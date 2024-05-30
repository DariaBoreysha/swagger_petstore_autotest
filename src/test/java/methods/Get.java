package methods;

import exceptions.AtGetMethodException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import report.Log;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Get {

    public HttpResponse sendRequestWithQueryParam(
            String url,
            String parameterName,
            String parameterValue
    ) {
        HttpGet request = composeRequestWithQueryParam(
                url,
                parameterName,
                parameterValue
        );
        logRequest(request.getURI(), request.getAllHeaders());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new AtGetMethodException(e);
        }
        return response;
    }

    public HttpGet composeRequestWithQueryParam(
            String url,
            String parameterName,
            String parameterValue
    ) {
        HttpGet request = new HttpGet(url);
        addQueryParameter(request, parameterName, parameterValue);
        addDefaultHeaders(request);
        return request;
    }

    private HttpGet addDefaultHeaders(HttpGet request) {
        request.addHeader("accept", "application/json");
        return request;
    }

    private HttpGet addQueryParameter(
            HttpGet request,
            String parameterName,
            String parameterValue
    ) {
        URI uri;
        try {
            uri = new URIBuilder(request.getURI())
                    .addParameter(parameterName, parameterValue).build();
        } catch (URISyntaxException e) {
            throw new AtGetMethodException(e);
        }
        request.setURI(uri);
        return request;
    }

    private static void logRequest(URI uri, Header[] headers) {
        Log.log("GET request: " + System.lineSeparator()
                + "URL: " + uri + System.lineSeparator()
                + "headers: " + Arrays.toString(headers)
        );
    }

    public HttpResponse sendRequestWithPathParam(
            String url,
            String endpoint,
            String parameterValue
    ) {
        HttpGet request = composeRequestWithPathParam(
                url,
                endpoint,
                parameterValue
        );
        logRequest(request.getURI(), request.getAllHeaders());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new AtGetMethodException(e);
        }
        return response;
    }

    public HttpGet composeRequestWithPathParam(
            String url,
            String endpoint,
            String parameterValue
    ) {
        HttpGet request = new HttpGet(url);
        addPathParameter(request, endpoint, parameterValue);
        addDefaultHeaders(request);
        return request;
    }

    private HttpGet addPathParameter(
            HttpGet request,
            String endpoint,
            String parameterValue
    ) {
        URI uri;
        try {
            uri = new URIBuilder(request.getURI())
                    .setPath(endpoint + parameterValue).build();
        } catch (URISyntaxException e) {
            throw new AtGetMethodException(e);
        }
        request.setURI(uri);
        return request;
    }
}
