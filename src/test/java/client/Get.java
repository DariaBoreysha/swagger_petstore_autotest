package client;

import exceptions.AtHttpClientException;
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

    public HttpResponse get(
            String url,
            String parameterName,
            String parameterValue
    ) {
        HttpGet request = composeRequest(
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
            throw new AtHttpClientException(e);
        }
        return response;
    }

    public HttpGet composeRequest(
            String url,
            String parameterName,
            String parameterValue
    ) {
        HttpGet request = new HttpGet(url);
        addParameter(request, parameterName, parameterValue);
        addDefaultHeaders(request);
        return request;
    }

    private HttpGet addDefaultHeaders(HttpGet request) {
        request.addHeader("accept", "application/json");
        return request;
    }

    private HttpGet addParameter(
            HttpGet request,
            String parameterName,
            String parameterValue
    ) {
        URI uri;
        try {
            uri = new URIBuilder(request.getURI())
                    .addParameter(parameterName, parameterValue).build();
        } catch (URISyntaxException e) {
            throw new AtHttpClientException(e);
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
}
