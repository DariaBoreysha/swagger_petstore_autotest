package steps;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import org.assertj.core.api.AssertionsForClassTypes;
import stephelper.Memory;
import utils.DataTableConverter;
import utils.HttpUtil;
import utils.JsonNodeUtil;
import utils.StringUtil;

import java.util.HashMap;

public class AddNewPetSteps extends BaseSteps {

    @And("формируем JSON на основе шаблона {string} и сохраняем в Memory как {string}")
    public void composeRequestBody(
            String fileSampleName,
            String memoryVariableName,
            DataTable table
    ) {
        HashMap<String, String> map = DataTableConverter.toHashMap(table, "field");
        String requestBody = StringUtil.composeRequest(fileSampleName, map);
        Memory.put(memoryVariableName, requestBody);
    }

    @When("отправляем POST запрос c телом из Memory: {string} на {string} и сохраняем ответ в Memory как {string}")
    public void sendPostRequest(
            String memoryRequestVariableName,
            String url,
            String memoryResponseVariableName
    ) {
        String requestBody = Memory.asString(memoryRequestVariableName);
        HttpResponse response = httpClient.methodPost().sendRequest(url, requestBody);
        Memory.put(memoryResponseVariableName, response);
    }

    @And("извлекаем тело ответа из Memory: {string}, конвертируем в jsonNode и сохраняем в Memory как {string}")
    public void responseToString(
            String memoryVariableAsHttpResponse,
            String memoryVariableAsJsonNode
    ) {
        HttpResponse response = Memory.asHttpResponse(memoryVariableAsHttpResponse);
        JsonNode jsonNode = HttpUtil.convertHttpResponseToJsonNode(response);
        Memory.put(memoryVariableAsJsonNode, jsonNode);
    }

    @And("извлекаем тело запроса из Memory: {string}, конвертируем в jsonNode и сохраняем в Memory как {string}")
    public void requestToJsonNode(
            String memoryVariableAsString,
            String memoryVariableAsJsonNode
    ) {
        String body = Memory.asString(memoryVariableAsString);
        JsonNode jsonNode = JsonNodeUtil.convertStringToJsonNode(body);
        Memory.put(memoryVariableAsJsonNode, jsonNode);
    }

    @And("проверяем, что JsonNode из Memory: {string} соответствует  JsonNode из Memory: {string}")
    public void verifyResponseIsEqualToRequest(String actualMemoryKey, String expectedMemoryKey) {
        JsonNode expectedNode = Memory.asJsonNode(expectedMemoryKey);
        JsonNode actualNode = Memory.asJsonNode(actualMemoryKey);
        AssertionsForClassTypes.assertThat(actualNode).isEqualTo(expectedNode);
    }


    @And("отправляем GET запрос на {string} эндпойнт {string} с path параметром равным {string} и сохраняем тело ответа в Memory как {string}")
    public void sendGetRequestWithPathParam(
            String url,
            String endpoint,
            String pathParameterMemoryVariable,
            String memoryVariableName
    ) {
        String pathParameterValue = Memory.asString(pathParameterMemoryVariable);
        HttpResponse response = httpClient.methodGet()
                .setUrl(url).setEndpoint(endpoint)
                .setPathParameter(pathParameterValue).sendRequest();
        Memory.put(memoryVariableName, response);
    }
}