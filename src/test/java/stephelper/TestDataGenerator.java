package stephelper;

import utils.StringUtil;

public class TestDataGenerator {

    public static String generate(String fieldName) {
        switch (fieldName) {
            case "id", "pet_entity_id":
                return StringUtil.generateStringOfDigits(9223372036854775807L);
            default:
                return StringUtil.generateStringOfDigits(10);
        }
    }
}
