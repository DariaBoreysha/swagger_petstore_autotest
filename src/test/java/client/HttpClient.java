package client;

import exceptions.AtHttpClientException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import report.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class HttpClient {

    public HttpResponse sendGetRequest(
            String url,
            String parameterName,
            String parameterValue
    ) {
        HttpGet request = composeGetRequest(
                url,
                parameterName,
                parameterValue
        );
        logGetRequest(request.getURI(), request.getAllHeaders());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new AtHttpClientException(e);
        }
        return response;
    }

    public HttpGet composeGetRequest(
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

    private static void logGetRequest(URI uri, Header[] headers) {
        Log.log("GET request: " + System.lineSeparator()
                + "URL: " + uri + System.lineSeparator()
                + "headers: " + Arrays.toString(headers)
        );
    }

    public static InputStream extractHttpEntityContent(HttpResponse response){
        HttpEntity entity = extractHttpEntityFromResponse(response);
        InputStream entityContentStream;
        try {
            entityContentStream = entity.getContent();
        } catch (IOException e) {
            throw new AtHttpClientException(e);
        }
        return entityContentStream;
    }

    private static HttpEntity extractHttpEntityFromResponse(HttpResponse response){
        return response.getEntity();
    }
}
