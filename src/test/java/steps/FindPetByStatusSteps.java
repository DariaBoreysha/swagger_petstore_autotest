package steps;

import client.HttpClient;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import org.apache.http.HttpEntity;
import stephelper.Memory;

import java.util.Map;

public class FindPetByStatusSteps {

    @When("формируем GET запрос с валидным параметром {string} со значением {string}, отправляем на {string} и сохраняем ответ в Memory как {string}")
    public void sendGetRequest(
            String parameterName,
            String parameterValue,
            String url,
            String memoryVariableName
    ) {
        HttpClient httpClient = new HttpClient();
        HttpEntity responseBody = httpClient.sendGetRequest(
                url,
                parameterName,
                parameterValue
        );
        Memory.put(memoryVariableName, responseBody);
    }
}
