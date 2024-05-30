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
import utils.StringUtil;

import java.util.HashMap;

import static steps.BaseSteps.httpClient;

public class AddNewPetSteps {

    @And("формируем JSON на основе шаблона {string} и сохраняем в Memory как {string}")
    public void formRequestBody(
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
    public void responseToString(String memoryVariableBefore, String memoryVariableAfter) {
        HttpResponse response = Memory.asHttpResponse(memoryVariableBefore);
        JsonNode jsonNode = HttpUtil.convertHttpResponseToJsonNode(response);
        Memory.put(memoryVariableAfter, jsonNode);
    }

    @And("извлекаем тело запроса из Memory: {string}, конвертируем в jsonNode и сохраняем в Memory как {string}")
    public void requestToJsonNode(String memoryVariableBefore, String memoryVariableAfter) {
        String body = Memory.asString(memoryVariableBefore);
        JsonNode jsonNode = StringUtil.convertStringToJsonNode(body);
        Memory.put(memoryVariableAfter, jsonNode);
    }

    @And("извлекаем тело ответа и тело запроса из Memory: {string}, {string} и проверяем, что ответ и запрос совпадают")
    public void toJsonNode(String request, String response) {
        JsonNode requestNode = Memory.asJsonNode(request);
        JsonNode responseNode = Memory.asJsonNode(response);
        AssertionsForClassTypes.assertThat(requestNode).isEqualTo(responseNode);
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