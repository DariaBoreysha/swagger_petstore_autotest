package stephelper;

import org.apache.commons.lang3.RandomStringUtils;
import utils.StringUtil;

import java.math.BigDecimal;

public class TestDataGenerator {

    public static String generate(String fieldName) {
        switch (fieldName) {
            case "id", "pet_entity_id":
                return StringUtil.generateStringOfDigits(9223372036854775807L);
            case "pet_name", "tag_name", "category_name":
                return RandomStringUtils.random(5, true, false);
            case "photourls":
                return StringUtil.generateStringArray();
            case "invalid_id":
                return String.valueOf(new BigDecimal("9223372036854775808"));
            default:
                return StringUtil.generateStringOfDigits(10);
        }
    }
}
