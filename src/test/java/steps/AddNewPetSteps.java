package steps;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
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
            String memoryKeyName,
            DataTable table
    ) {
        HashMap<String, String> map = DataTableConverter.toHashMap(table, "field");
        String requestBody = StringUtil.composeRequest(fileSampleName, map);
        Memory.put(memoryKeyName, requestBody);
    }

    @When("отправляем POST запрос c телом из Memory: {string} на {string} и сохраняем ответ в Memory как {string}")
    public void sendPostRequest(
            String requestMemoryKey,
            String url,
            String responseMemoryKey
    ) {
        String requestBody = Memory.asString(requestMemoryKey);
        HttpResponse response = httpClient.methodPost().sendRequest(url, requestBody);
        Memory.put(responseMemoryKey, response);
    }

    @And("извлекаем тело ответа из Memory: {string}, конвертируем в jsonNode и сохраняем в Memory как {string}")
    public void convertResponseToJsonNode(String httpResponseMemoryKey, String jsonNodeMemoryKey) {
        HttpResponse response = Memory.asHttpResponse(httpResponseMemoryKey);
        JsonNode jsonNode = HttpUtil.convertHttpResponseToJsonNode(response);
        Memory.put(jsonNodeMemoryKey, jsonNode);
    }

    @And("извлекаем тело запроса из Memory: {string}, конвертируем в jsonNode и сохраняем в Memory как {string}")
    public void convertRequestToJsonNode(String stringMemoryKey, String jsonNodeMemoryKey) {
        String body = Memory.asString(stringMemoryKey);
        JsonNode jsonNode = JsonNodeUtil.convertStringToJsonNode(body);
        Memory.put(jsonNodeMemoryKey, jsonNode);
    }

    @And("проверяем, что JsonNode из Memory: {string} соответствует  JsonNode из Memory: {string}")
    public void verifyResponseIsEqualToRequest(String actualMemoryKey, String expectedMemoryKey) {
        JsonNode expectedNode = Memory.asJsonNode(expectedMemoryKey);
        JsonNode actualNode = Memory.asJsonNode(actualMemoryKey);
        AssertionsForClassTypes.assertThat(actualNode).isEqualTo(expectedNode);
    }


    @And("отправляем GET запрос на {string} эндпойнт {string} с path параметром {string} и сохраняем тело ответа в Memory как {string}")
    public void sendGetRequestWithPathParam(
            String url,
            String endpoint,
            String pathParameterMemoryKey,
            String memoryKeyName
    ) {
        String pathParameterValue = Memory.asString(pathParameterMemoryKey);
        HttpResponse response = httpClient.methodGet()
                .setUrl(url).setEndpoint(endpoint)
                .setPathParameter(pathParameterValue).sendRequest();
        Memory.put(memoryKeyName, response);
    }

    @Given("отправляем DELETE запрос на {string} эндпойнт {string} с path параметром {string} и сохраняем ответ в Memory как {string}")
    public void sendDeleteRequest(
            String url,
            String endpoint,
            String pathParameterName,
            String memoryKeyName,
            DataTable table
    ) {
        HashMap<String, String> map = DataTableConverter.toHashMap(table, "variable");
        String pathParameterValue = Memory.review(map.get(pathParameterName));
        HttpResponse response = httpClient.methodDelete().sendRequest(url, endpoint, pathParameterValue);
        Memory.put(memoryKeyName, response);
    }
}