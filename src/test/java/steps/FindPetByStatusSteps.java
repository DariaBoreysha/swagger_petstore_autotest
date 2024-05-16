package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import stephelper.Memory;
import utils.JsonSchemaValidator;

public class FindPetByStatusSteps extends BaseSteps {

    @When("формируем GET запрос с валидным параметром {string} со значением {string}, отправляем на {string} и сохраняем ответ в Memory как {string}")
    public void sendGetRequest(
            String parameterName,
            String parameterValue,
            String url,
            String memoryVariableName
    ) {
        HttpResponse response = httpClient.sendGetRequest(url, parameterName, parameterValue);
        Memory.put(memoryVariableName, response);
    }

    @And("извлекаем ответ из Memory переменной : {string} и проверяем, что структура тела JSON соответствует JSON schema : {string}")
    public void checkResponseAgainstJsonSchema(
            String responseVariableName,
            String jsonSchemaFileName
    ) {
        HttpResponse response = Memory.asHttpResponse(responseVariableName);
        String jsonBody = httpClient.convertHttpEntityToString(response);
        JsonSchemaValidator schemaValidator = new JsonSchemaValidator();
        schemaValidator.isJsonValid(jsonBody, jsonSchemaFileName);
    }
}
