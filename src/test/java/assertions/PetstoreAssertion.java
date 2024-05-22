package assertions;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.SoftAssertions;

public class PetstoreAssertion {

    private SoftAssertions softly = new SoftAssertions();

    public PetstoreAssertion() {
    }

    public void assertBodyArrayFieldValuesAreCorrect(
            JsonNode jsonBody,
            String fieldName,
            String[] expectedValues
    ) {
        for (int i = 0; i < jsonBody.size(); i++) {
            softly.assertThat(jsonBody.get(i).get(fieldName).asText())
                    .containsAnyOf(expectedValues);
        }
        softly.assertAll();
    }

    public void assertBodyFieldValueIsCorrect(
            JsonNode jsonBody,
            String fieldName,
            String expectedValue
    ) {
        softly.assertThat(jsonBody.get(fieldName).asText())
                .isEqualTo(expectedValue);
    }

    public void assertAll(){
        softly.assertAll();
    }
}
