package steps;

import io.cucumber.java.en.Then;
import org.apache.http.HttpResponse;
import stephelper.Memory;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseSteps {

    @Then("извлекаем ответ из Memory переменной : {string} и проверяем соответствие статус кода и поясняющей фразы значениям {int}, {string}")
    public void checkStatusCodeAndReasonPhraseAreCorrect(
            String responseVariableName,
            int expectedStatusCode,
            String expectedReasonPhrase
    ) {
        HttpResponse response = (HttpResponse) Memory.get(responseVariableName);
        int actualStatusCode = response.getStatusLine().getStatusCode();
        String actualReasonPhrase = response.getStatusLine().getReasonPhrase();
        assertThat(actualStatusCode).isEqualTo(expectedStatusCode);
        assertThat(actualReasonPhrase).isEqualTo(expectedReasonPhrase);
    }
}
