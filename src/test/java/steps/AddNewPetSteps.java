package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
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
        HttpResponse response = httpClient.postMethod().post(url, requestBody);
        Memory.put(memoryResponseVariableName, response);
    }
}
