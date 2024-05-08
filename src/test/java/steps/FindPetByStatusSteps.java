package steps;

import client.HttpClient;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import stephelper.Memory;

import java.util.Map;

public class FindPetByStatusSteps {

    @When("формируем GET запрос с валидным параметром {string} со значением {string}, отправляем на {string} и сохраняем ответ как {string}")
    public void sendGetRequest(
            String parameterName,
            String parameterValue,
            String url,
            String memoryVariableName,
            DataTable table
    ) {
        HttpClient httpClient = new HttpClient();
        Map<String, String> headers = table.transpose().asMap();
        String responseBody = httpClient.sendGetRequest(
                url,
                headers,
                parameterName,
                parameterValue
        );
        Memory.put(
                memoryVariableName,
                responseBody
        );
    }
}
