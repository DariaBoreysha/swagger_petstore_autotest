package methods;

import exceptions.AtPostMethodException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import report.Log;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

public class Post {

    public HttpResponse sendRequest(
            String url,
            String body
    ) {
        HttpPost request = composeRequest(url, body);
        logRequest(request.getURI(), request.getAllHeaders(), body);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new AtPostMethodException(e);
        }
        return response;
    }

    private HttpPost composeRequest(
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

    private void logRequest(URI uri, Header[] allHeaders, String body) {
        Log.log("POST request: " + System.lineSeparator()
                + "URL: " + uri + System.lineSeparator()
                + "headers: " + Arrays.toString(allHeaders) + System.lineSeparator()
                + "body: " + body

        );
    }
}
