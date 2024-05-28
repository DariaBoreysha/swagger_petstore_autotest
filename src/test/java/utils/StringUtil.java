package utils;

import constants.Constants;
import stephelper.Memory;

import java.util.HashMap;

public class StringUtil {

    private static final String PREFIX = "#{placeholder_";
    private static final String POSTFIX = "}";

    public static String generateStringOfDigits(long upperBorder) {
        return String.valueOf((long) (Math.random() * (upperBorder - 1)) + 1);
    }

    public static long generateNumber(long upperBorder) {
        return (long) (Math.random() * (upperBorder - 1)) + 1;
    }

    public static String composeRequest(String fileSampleName, HashMap<String, String> map) {
        String request = FileUtil.read(Constants.SAMPLES_FOLDER + fileSampleName);
        for (String key : map.keySet()) {
            String placeholder = PREFIX + key + POSTFIX;
            if (request.contains(placeholder)) {
                request = request.replace(placeholder, Memory.review(map.get(key)));
            }
        }
        return request;
    }
}