package stephelper;

import org.apache.commons.lang3.RandomStringUtils;
import utils.StringUtil;

public class TestDataGenerator {

    public static String generate(String valueType) {
        switch (valueType) {
            case "id", "pet_entity_id", "order_id":
                return StringUtil.generateStringOfDigits(9223372036854775807L);
            case "pet_name", "tag_name", "category_name", "invalid_pet_entity_id":
                return RandomStringUtils.random(5, true, false);
            case "photourls":
                return StringUtil.generateStringArray();
            case "invalid_id":
                return StringUtil.generateInvalidIdString();
            case "date":
                return StringUtil.generateCurrentDate();
            default:
                return StringUtil.generateStringOfDigits(2);
        }
    }
}
