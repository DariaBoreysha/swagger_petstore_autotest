package stephelper;

import org.apache.commons.lang3.RandomStringUtils;
import utils.StringUtil;

public class TestDataGenerator {

    public static String generate(String fieldName) {
        switch (fieldName) {
            case "id", "pet_entity_id":
                return StringUtil.generateStringOfDigits(9223372036854775807L);
            case "pet_name", "tag_name", "category_name":
                return RandomStringUtils.random(5, true, false);
            case "photourls":
                return generateStringArray();
            default:
                return StringUtil.generateStringOfDigits(10);
        }
    }

    private static String generateStringArray() {
        StringBuilder data = new StringBuilder("[");
        for (int i = 0; i < 3; i++) {
            data.append("\"")
                    .append(RandomStringUtils.random(5, true, true))
                    .append("\"" + ",");
        }
        return data.deleteCharAt(data.length() - 1).append("]").toString();
    }
}
