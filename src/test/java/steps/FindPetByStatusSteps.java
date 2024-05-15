package steps;

import client.HttpClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import stephelper.Memory;
import utils.FileUtil;

public class FindPetByStatusSteps {

    @When("формируем GET запрос с валидным параметром {string} со значением {string}, отправляем на {string} и сохраняем ответ в Memory как {string}")
    public void sendGetRequest(
            String parameterName,
            String parameterValue,
            String url,
            String memoryVariableName
    ) {
        HttpClient httpClient = new HttpClient();
        HttpEntity responseBody = httpClient.sendGetRequest(url, parameterName, parameterValue);
        Memory.put(memoryVariableName, responseBody);
    }

    @And("извлекаем ответ из Memory переменной : {string} и проверяем, что структура тела JSON соответствует JSON schema : {string}")
    public void checkResponseAgainstJsonSchema(
            String responseVariableName,
            String jsonSchemaFileName
    ) {
        HttpResponse response = (HttpResponse) Memory.get(responseVariableName);
        String jsonBody = FileUtil.convertJsonEntityToString(response);

    }

    @And("парсим JSON из Memory переменной : {string} на POJO классы и проверяем, что значение поля status соответствует значению {string} из запроса")
    public void checkStatusFieldValuesInResponseBody(
            String responseVariableName,
            String expectedStatusValue
    ) {

    }
}
