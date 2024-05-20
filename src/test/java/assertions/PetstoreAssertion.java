package assertions;

import com.fasterxml.jackson.databind.JsonNode;

import static org.assertj.core.api.Assertions.assertThat;

public class PetstoreAssertion {

    public static void assertBodyFieldValueIsCorrect(
            JsonNode jsonBody,
            String fieldName,
            String[] expectedValues
    ) {
        for (int i = 0; i < jsonBody.size(); i++) {
            assertThat(jsonBody.get(i).get(fieldName).asText())
                    .containsAnyOf(expectedValues);
        }
    }

    public static <T> void assertBodyFieldValueIsCorrect(
            JsonNode jsonBody,
            String fieldName,
            T expectedValue
    ) {
        assertThat(jsonBody.get(fieldName).asText())
                .isEqualTo(expectedValue);
    }
}
