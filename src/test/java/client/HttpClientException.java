package client;

public class HttpClientException extends RuntimeException {

  public HttpClientException(String message) {
    super(message);
  }

  public HttpClientException(Throwable cause) {
    super(cause);
  }

  public HttpClientException(String message, Throwable cause) {
    super(message, cause);
  }

}
