package stephelper;

import client.HttpClient;
import utils.StringUtil;

public class TestDataGenerator {

    public static String generate(String fieldName) {
        switch (fieldName) {
            case "id":
                return StringUtil.generateStringOfDigits(9223372036854775807L);
            case "pet_entity_id":
                HttpClient httpClient = new HttpClient();
                String pathParameter = StringUtil.generateStringOfDigits(9223372036854775807L);
                httpClient.methodDelete().sendRequest(
                        "https://petstore.swagger.io", "/v2/pet/", pathParameter
                );
                return pathParameter;
            default:
                return StringUtil.generateStringOfDigits(10);
        }
    }
}
