package steps;

import assertions.PetstoreAssertion;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.http.HttpResponse;
import org.assertj.core.api.SoftAssertions;
import stephelper.Memory;
import utils.DataTableConverter;
import utils.HttpUtil;

import java.util.HashMap;

public class CommonSteps {

    @Then("извлекаем ответ из Memory переменной : {string} и проверяем соответствие статус кода и поясняющей фразы значениям {int}, {string}")
    public void checkStatusCodeAndReasonPhraseAreCorrect(
            String responseVariableName,
            int expectedStatusCode,
            String expectedReasonPhrase
    ) {
        HttpResponse response = Memory.asHttpResponse(responseVariableName);
        int actualStatusCode = response.getStatusLine().getStatusCode();
        String actualReasonPhrase = response.getStatusLine().getReasonPhrase();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualStatusCode).isEqualTo(expectedStatusCode);
        softly.assertThat(actualReasonPhrase).isEqualTo(expectedReasonPhrase);
        softly.assertAll();
    }

    @And("конвертируем ответ из Memory: {string} в JsonNode и сохраняем как {string}")
    public void convertResponseToJsonNode(
            String responseMemoryKey,
            String jsonNodeMemoryKey
    ) {
        HttpResponse response = Memory.asHttpResponse(responseMemoryKey);
        JsonNode responseJsonBody = HttpUtil.convertHttpResponseToJsonNode(response);
        Memory.put(jsonNodeMemoryKey, responseJsonBody);
    }

    @And("извлекаем тело JSON из Memory переменной : {string} и проверяем соответствие фактических значений полей ожидаемым")
    public void checkActualFieldValueMatchesExpected(
            String memoryKeyName,
            DataTable table
    ) {
        JsonNode jsonResponseBody = Memory.asJsonNode(memoryKeyName);
        HashMap<String, String> map = DataTableConverter.toHashMap(table, "field");
        PetstoreAssertion assertion = new PetstoreAssertion();
        for (String key : map.keySet()) {
            assertion.assertBodyFieldValueIsCorrect(jsonResponseBody, key, map.get(key));
        }
        assertion.assertAll();
    }

    @And("извлекаем тело JSON из Memory переменной : {string} и проверяем, что значение поля {string} соответствует значению {string} запроса")
    public void checkStatusCorrectnessInServerResponse(
            String jsonNodeMemoryKey,
            String fieldName,
            String expectedStatusValue
    ) {
        JsonNode responseJsonBody = Memory.asJsonNode(jsonNodeMemoryKey);
        String[] fieldExpectedValues = expectedStatusValue.split(",");
        PetstoreAssertion assertion = new PetstoreAssertion();
        assertion.assertBodyArrayFieldValuesAreCorrect(responseJsonBody, fieldName, fieldExpectedValues);
    }
}
