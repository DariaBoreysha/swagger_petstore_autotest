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

    private static HttpGet request;
    private String url;
    private String endpoint;
    private String pathParameterValue;
    private String queryParameterName;
    private String queryParameterValue;

    public Get setUrl(String url) {
        this.url = url;
        return this;
    }

    public Get setPathParameter(String pathParameter) {
        this.pathParameterValue = pathParameter;
        return this;
    }

    public Get setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public Get setQueryParameter(
            String queryParameterName,
            String queryParameterValue
    ) {
        this.queryParameterValue = queryParameterValue;
        this.queryParameterName = queryParameterName;
        return this;
    }

    private void composeRequest() {
        request = new HttpGet(url);
        addDefaultHeaders(request);
        if (endpoint != null && pathParameterValue != null) {
            addPathParameter();
        }
        if (queryParameterName != null && queryParameterValue != null) {
            addQueryParameter();
        }
    }

    private void addQueryParameter() {
        URI uri;
        try {
            uri = new URIBuilder(request.getURI())
                    .addParameter(queryParameterName, queryParameterValue).build();
        } catch (URISyntaxException e) {
            throw new AtGetMethodException(e);
        }
        request.setURI(uri);
    }

    private void addPathParameter() {
        URI uri;
        try {
            uri = new URIBuilder(request.getURI())
                    .setPath(endpoint + pathParameterValue).build();
        } catch (URISyntaxException e) {
            throw new AtGetMethodException(e);
        }
        request.setURI(uri);
    }

    private void addDefaultHeaders(HttpGet request) {
        request.addHeader("accept", "application/json");
    }

    public HttpResponse sendRequest() {
        composeRequest();
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

    private void logRequest(URI uri, Header[] headers) {
        Log.log("GET request: " + System.lineSeparator()
                + "URL: " + uri + System.lineSeparator()
                + "headers: " + Arrays.toString(headers)
        );
    }
}
