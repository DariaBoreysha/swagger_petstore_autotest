package methods;

import exceptions.AtHttpClientException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import report.Log;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Delete {

    public HttpResponse sendRequest(
            String url,
            String endpoint,
            String pathParameter
    ) {
        HttpDelete request = composeRequest(url, endpoint, pathParameter);
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

    private void logRequest(URI uri, Header[] allHeaders) {
        Log.log("DELETE request: " + System.lineSeparator()
                + "URL: " + uri + System.lineSeparator()
                + "headers: " + Arrays.toString(allHeaders)
        );
    }

    private HttpDelete composeRequest(
            String url,
            String endpoint,
            String pathParameter
    ) {
        HttpDelete request = new HttpDelete(url);
        addParameter(request, endpoint, pathParameter);
        addDefaultHeaders(request);
        return request;
    }

    private HttpDelete addParameter(
            HttpDelete request,
            String endpoint,
            String pathParameter
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
}
