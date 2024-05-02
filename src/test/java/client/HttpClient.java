package client;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClient {

  private final String url;

  public HttpClient(String url) {
    this.url = url;
  }

  public String get(
      HashMap<String, String> headers,
      HashMap<String, String> values
  ) {
    HttpGet request = composeGetRequest(headers, values);
    HttpResponse response;
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    try {
      response = httpClient.execute(request);
    } catch (IOException e) {
      throw new HttpClientException(e);
    }
    assertAnswerStatusIsOk(response.getStatusLine().getStatusCode());
    HttpEntity responseEntity = response.getEntity();
    String responseBody;
    try {
      responseBody = EntityUtils.toString(responseEntity, "UTF-8");
    } catch (IOException e) {
      throw new HttpClientException(e);
    }
    return responseBody;
  }

  private HttpGet composeGetRequest(
      HashMap<String, String> headers,
      HashMap<String, String> values
  ) {
    HttpGet request = new HttpGet(url);
    addParameters(request, values);
    addHeaders(request, headers);
    return request;
  }

  private HttpGet addHeaders(
      HttpGet request,
      HashMap<String, String> headers
  ) {
    for (String key : headers.keySet()) {
      request.addHeader(key, headers.get(key));
    }
    return request;
  }

  private HttpGet addParameters(
      HttpGet request,
      HashMap<String, String> parameters
  ) {
    List<NameValuePair> nameValuePairs = new ArrayList<>();
    for (String key : parameters.keySet()) {
      NameValuePair param = new BasicNameValuePair(key, parameters.get(key));
      nameValuePairs.add(param);
    }
    URI uri = null;
    try {
      uri = new URIBuilder(request.getURI()).addParameters(nameValuePairs).build();
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
