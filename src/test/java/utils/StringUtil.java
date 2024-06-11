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

    public static String composeRequest(String fileSampleName, HashMap<String, String> map) {
        String request = FileUtil.read(Constants.SAMPLES_FOLDER + fileSampleName);
        for (String key : map.keySet()) {
            String placeholder = PREFIX + key + POSTFIX;
            if (request.contains(placeholder)) {
                if (Memory.review(map.get(key)).equals("null")) {
                    request = setNullValueForField(request, placeholder);
                } else {
                    request = request.replace(placeholder, Memory.review(map.get(key)));
                }
            }
        }
        return request;
    }

    private static String setNullValueForField(String request, String placeholder) {
        String searchString = "\"" + placeholder + "\"";
        int indexOfPlaceholderStart;
        if (request.contains(searchString)) {
            indexOfPlaceholderStart = request.indexOf(searchString);
        } else {
            indexOfPlaceholderStart = request.indexOf(placeholder);
            searchString = placeholder;
        }
        String stringBeforePlaceholder = request.substring(0, indexOfPlaceholderStart);
        int indexOfPlaceholderEnd = indexOfPlaceholderStart + searchString.length();
        String stringAfterPlaceholder = request.substring(indexOfPlaceholderEnd);
        return (stringBeforePlaceholder + null + stringAfterPlaceholder);
    }
}
