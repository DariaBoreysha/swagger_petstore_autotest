package assertions;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.SoftAssertions;
import steps.BaseSteps;

public class PetstoreAssertion {

    static SoftAssertions softly = new SoftAssertions();

    public static void assertBodyFieldValueIsCorrect(
            JsonNode jsonBody,
            String fieldName,
            String[] expectedValues
    ) {
        for (int i = 0; i < jsonBody.size(); i++) {
            BaseSteps.softly.assertThat(jsonBody.get(i).get(fieldName).asText())
                    .containsAnyOf(expectedValues);
        }
        softly.assertAll();
    }

    public static <T> void assertBodyFieldValueIsCorrect(
            JsonNode jsonBody,
            String fieldName,
            T expectedValue
    ) {
        BaseSteps.softly.assertThat(jsonBody.get(fieldName).asText())
                .isEqualTo(expectedValue);
    }
}
