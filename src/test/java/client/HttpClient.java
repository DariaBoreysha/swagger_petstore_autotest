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

  private final String url;

  public HttpClient(String url) {
    this.url = url;
  }

  public String get(
      Map<String, String> headers,
      String parameterName,
      String parameterValue
  ) {
    HttpGet request = composeGetRequest(
        headers,
        parameterName,
        parameterValue
    );
    System.out.println(request);
    HttpResponse response;
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
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
      responseBody = EntityUtils.toString(
          responseEntity,
          "UTF-8"
      );
    } catch (IOException e) {
      throw new HttpClientException(e);
    }
    return responseBody;
  }

  private HttpGet composeGetRequest(
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
    addHeaders(
        request,
        headers
    );
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
