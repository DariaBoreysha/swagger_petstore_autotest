package steps;

import assertions.PetstoreAssertion;
import io.cucumber.java.en.Then;
import org.apache.http.HttpResponse;
import org.assertj.core.api.SoftAssertions;
import stephelper.Memory;

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
}
