package assertions;

import com.fasterxml.jackson.databind.JsonNode;

import static org.assertj.core.api.Assertions.assertThat;

public class PetstoreAssertion {

    //TODO Отрефакторить в случае, если необходимо будет проверять не только поле status
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
