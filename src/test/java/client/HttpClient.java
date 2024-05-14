package client;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClient {

    public String sendGetRequest(
            String url,
            Map<String, String> headers,
            String parameterName,
            String parameterValue
    ) {
        HttpGet request = composeGetRequest(
                url,
                headers,
                parameterName,
                parameterValue
        );
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new HttpClientException(e);
        }
        assertAnswerStatusIsOk(response
                .getStatusLine()
                .getStatusCode()
        );
        HttpEntity responseEntity = response.getEntity();
        String responseBody;
        try {
            responseBody = EntityUtils.toString(responseEntity, "UTF-8");
        } catch (IOException e) {
            throw new HttpClientException(e);
        }
        return responseBody;
    }

    public HttpGet composeGetRequest(
            String url,
            Map<String, String> headers,
            String parameterName,
            String parameterValue
    ) {
        HttpGet request = new HttpGet(url);
        addParameter(
                request,
                parameterName,
                parameterValue
        );
        addHeaders(request, headers);
        System.out.println(request);
        return request;
    }

    private HttpGet addHeaders(
            HttpGet request,
            Map<String, String> headers
    ) {
        for (String key : headers.keySet()) {
            request.addHeader(key, headers.get(key));
        }
        return request;
    }

    private HttpGet addParameter(
            HttpGet request,
            String parameterName,
            String parameterValue
    ) {
        URI uri = null;
        try {
            uri = new URIBuilder(request.getURI())
                    .addParameter(parameterName, parameterValue)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        request.setURI(uri);
        return request;
    }

    private void assertAnswerStatusIsOk(int statusCode) {
        assertThat(statusCode).isEqualTo(200);
    }
}
