package assertions;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.SoftAssertions;

public class PetstoreAssertion {

    public static SoftAssertions softly = new SoftAssertions();

    public static void assertBodyFieldValueIsCorrect(
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

    public static <T> void assertBodyFieldValueIsCorrect(
            JsonNode jsonBody,
            String fieldName,
            T expectedValue
    ) {
        softly.assertThat(jsonBody.get(fieldName).asText())
                .isEqualTo(expectedValue);
    }
}
