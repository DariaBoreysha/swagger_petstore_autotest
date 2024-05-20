package steps;

import assertions.PetstoreAssertion;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.http.HttpResponse;
import org.assertj.core.api.SoftAssertions;
import stephelper.Memory;
import utils.HttpUtil;

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
            String responseVariableName,
            String jsonNodeVariableName
    ) {
        HttpResponse response = Memory.asHttpResponse(responseVariableName);
        JsonNode responseJsonBody = HttpUtil.convertHttpResponseToJsonNode(response);
        Memory.put(jsonNodeVariableName, responseJsonBody);
    }

    @And("извлекаем тело JSON из Memory переменной : {string} и проверяем соответствие полей code, type, message значениям {int}, {string}, {string}")
    public void checkResponseMessage(
            String jsonNodeVariableName,
            int expectedCodeValue,
            String expectedTypeValue,
            String expectedMessageValue
    ) {
        JsonNode jsonResponseBody = Memory.asJsonNode(jsonNodeVariableName);
        PetstoreAssertion.assertBodyFieldValueIsCorrect(jsonResponseBody, "code", expectedCodeValue);
        PetstoreAssertion.assertBodyFieldValueIsCorrect(jsonResponseBody, "type", expectedTypeValue);
        PetstoreAssertion.assertBodyFieldValueIsCorrect(jsonResponseBody, "message", expectedMessageValue);
    }
}
