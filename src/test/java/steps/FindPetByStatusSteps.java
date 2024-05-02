package steps;

import client.HttpClient;
import io.cucumber.java.en.Given;
import java.util.HashMap;

public class FindPetByStatusSteps {

  @Given("формируем GET запрос и отправляем на {string} с одним параметром и получаем тело JSON")
  public void createGetRequest(String url) {
    HashMap<String, String> headers = new HashMap<>();
    HashMap<String, String> parameters = new HashMap<>();
    headers.put("accept", "application/json");
    parameters.put("status", "available");
    HttpClient httpClient = new HttpClient(url);
    String responseBody = httpClient.get(headers, parameters);
  }
}
