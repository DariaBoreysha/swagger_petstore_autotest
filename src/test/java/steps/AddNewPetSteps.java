package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import stephelper.Memory;
import utils.DataTableConverter;

import java.util.HashMap;

import static steps.BaseSteps.httpClient;

public class AddNewPetSteps {

    @Given("отправляем DELETE запрос на {string} эндпойнт {string} со значением path параметра {}")
    public void sendDeleteRequest(
            String uri,
            String endpoint,
            long id
    ) {
        httpClient.sendDeleteRequest(uri, endpoint, id);
    }

    @And("формируем JSON на основе шаблона {string} и сохраняем в Memory как {string}")
    public void formRequestBody(
            String fileSampleName,
            String memoryVariableName,
            DataTable table
    ) {
        HashMap<String, String> map = DataTableConverter.toHashMap(table, "field");
        String requestBody = RequestComposer.composeRequest(fileSampleName, map);
        Memory.put(memoryVariableName, requestBody);
    }

    @When("извлекаем JSON из Memory: {string}, отправляем POST запрос на {string} и сохраняем ответ в Memory как {string}")
    public void sendPostRequest(
            String memoryRequestVariableName,
            String url,
            String memoryResponseVariableName
    ) {
        String requestBody = Memory.asString(memoryRequestVariableName);
        HttpResponse response = httpClient.sendPostRequest(url, requestBody);
        Memory.put(memoryResponseVariableName, response);
    }
}
