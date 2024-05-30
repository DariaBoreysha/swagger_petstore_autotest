package steps;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import stephelper.Memory;
import utils.JsonSchemaValidator;

public class FindPetByStatusSteps extends BaseSteps {

    @When("формируем GET запрос с параметром {string} со значением {string}, отправляем на {string} и сохраняем ответ в Memory как {string}")
    public void sendGetRequestWithQueryParam(
            String parameterName,
            String parameterValue,
            String url,
            String memoryVariableName
    ) {
        HttpResponse response = httpClient.methodGet().sendRequestWithQueryParam(url, parameterName, parameterValue);
        Memory.put(memoryVariableName, response);
    }

    @And("извлекаем тело JSON из Memory переменной : {string} и проверяем, что структура тела JSON соответствует JSON schema : {string}")
    public void checkResponseAgainstJsonSchema(
            String jsonNodeVariableName,
            String jsonSchemaFileName
    ) {
        JsonNode jsonBody = Memory.asJsonNode(jsonNodeVariableName);
        JsonSchemaValidator schemaValidator = new JsonSchemaValidator();
        schemaValidator.validate(jsonBody, jsonSchemaFileName);
    }
}
