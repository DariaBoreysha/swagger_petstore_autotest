package stephelper;

import org.apache.commons.lang3.RandomStringUtils;
import utils.StringUtil;

import java.util.Arrays;

public class TestDataGenerator {

    public static String generate(String fieldName) {
        switch (fieldName) {
            case "id", "pet_entity_id":
                return StringUtil.generateStringOfDigits(9223372036854775807L);
            case "pet_name", "tag_name", "category_name":
                return RandomStringUtils.random(5, true, false);
            case "photourls":
                return generateStringArray(3);
            default:
                return StringUtil.generateStringOfDigits(10);
        }
    }

    private static String generateStringArray(int length) {
        String[] array = new String[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = RandomStringUtils.random(5, true, true);
        }
        return Arrays.toString(array);
    }
}
