package client;

import exceptions.AtHttpClientException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
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

    public static InputStream extractHttpEntityContent(HttpResponse response) {
        HttpEntity entity = extractHttpEntityFromResponse(response);
        InputStream entityContentStream;
        try {
            entityContentStream = entity.getContent();
        } catch (IOException e) {
            throw new AtHttpClientException(e);
        }
        return entityContentStream;
    }

    private static HttpEntity extractHttpEntityFromResponse(HttpResponse response) {
        return response.getEntity();
    }

    public HttpResponse sendDeleteRequest(
            String url,
            String endpoint,
            long pathParameter
    ) {
        HttpDelete request = composeDeleteRequest(url, endpoint, pathParameter);
        logDeleteRequest(request.getURI(), request.getAllHeaders());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new AtHttpClientException(e);
        }
        return response;
    }

    private void logDeleteRequest(URI uri, Header[] allHeaders) {
        Log.log("DELETE request: " + System.lineSeparator()
                + "URL: " + uri + System.lineSeparator()
                + "headers: " + Arrays.toString(allHeaders)
        );
    }

    private HttpDelete composeDeleteRequest(
            String url,
            String endpoint,
            long pathParameter
    ) {
        HttpDelete request = new HttpDelete(url);
        addParameter(request, endpoint, pathParameter);
        addDefaultHeaders(request);
        return request;
    }

    private HttpDelete addParameter(
            HttpDelete request,
            String endpoint,
            long pathParameter
    ) {
        URI uri;
        try {
            uri = new URIBuilder(request.getURI())
                    .setPath(endpoint + pathParameter).build();
        } catch (URISyntaxException e) {
            throw new AtHttpClientException(e);
        }
        request.setURI(uri);
        return request;
    }

    private HttpDelete addDefaultHeaders(HttpDelete request) {
        request.addHeader("accept", "application/json");
        return request;
    }

    public HttpResponse sendPostRequest(
            String url,
            String body
    ) {
        HttpPost request = composePostRequest(url, body);
        logPostRequest(request.getURI(), request.getAllHeaders(), body);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new AtHttpClientException(e);
        }
        return response;
    }

    private HttpPost composePostRequest(
            String url,
            String body
    ) {
        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity(body, "UTF-8"));
        addDefaultHeaders(request);
        return request;
    }

    private HttpPost addDefaultHeaders(HttpPost request) {
        request.addHeader("accept", "application/json");
        request.addHeader("Content-type", "application/json");
        return request;
    }

    private void logPostRequest(URI uri, Header[] allHeaders, String body) {
        Log.log("POST request: " + System.lineSeparator()
                + "URL: " + uri + System.lineSeparator()
                + "headers: " + Arrays.toString(allHeaders) + System.lineSeparator()
                + "body: " + body

        );
    }
}
