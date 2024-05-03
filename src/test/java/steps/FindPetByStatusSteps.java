package steps;

import client.HttpClient;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import java.util.Map;

public class FindPetByStatusSteps {

  @When("формируем GET запрос и отправляем на {string} с одним параметром {string} и получаем тело JSON")
  public void createGetRequest(
      String url,
      String statusValue,
      DataTable table
  ) {
    HttpClient httpClient = new HttpClient(url);
    Map<String, String> headers = table.transpose().asMap();
    String responseBody = httpClient.get(
        headers,
        "status",
        statusValue
    );
  }
}
