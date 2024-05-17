package steps;

import assertions.PetstoreAssertion;
import com.fasterxml.jackson.databind.JsonNode;
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

    @And("извлекаем тело JSON из Memory переменной : {string} и проверяем, что структура тела JSON соответствует JSON schema : {string}")
    public void checkResponseAgainstJsonSchema(
            String jsonNodeVariableName,
            String jsonSchemaFileName
    ) {
        JsonNode jsonBody = Memory.asJsonNode(jsonNodeVariableName);
        JsonSchemaValidator schemaValidator = new JsonSchemaValidator();
        schemaValidator.isJsonValid(jsonBody, jsonSchemaFileName);
    }

    @And("извлекаем ответ из Memory переменной : {string} и проверяем, что значение поля status соответствует значению {string} запроса, сохраняем JsonNode в Memory как {string}")
    public void checkingStatusCorrectnessInServerResponse(
            String responseVariableName,
            String expectedStatusValue,
            String jsonNodeVariableName
    ) {
        HttpResponse response = Memory.asHttpResponse(responseVariableName);
        JsonNode responseJsonBody = JsonSchemaValidator.convertHttpResponseToJsonNode(response);
        String[] fieldExpectedValues = expectedStatusValue.split(",");
        PetstoreAssertion.assertBodyStatusFieldValueIsCorrect(responseJsonBody, fieldExpectedValues);
        Memory.put(jsonNodeVariableName, responseJsonBody);
    }
}
