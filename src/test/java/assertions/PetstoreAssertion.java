package assertions;

import com.fasterxml.jackson.databind.JsonNode;

import static org.assertj.core.api.Assertions.assertThat;

public class PetstoreAssertion {

    public static void assertBodyStatusFieldValueIsCorrect(
            JsonNode jsonBody,
            String[] expectedValues
    ) {
        for (int i = 0; i < jsonBody.size(); i++) {
            assertThat(jsonBody.get(i).get("status").asText())
                    .containsAnyOf(expectedValues);
        }
    }
}
