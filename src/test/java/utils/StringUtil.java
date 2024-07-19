package utils;

import constants.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import stephelper.Memory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.stream.IntStream;

public class StringUtil {

    private static final String PREFIX = "#{placeholder_";
    private static final String POSTFIX = "}";

    public static String generateStringOfDigits(long upperBorder) {
        return String.valueOf((long) (Math.random() * (upperBorder - 1)) + 1);
    }

    public static String generateInvalidIdString() {
        return "922337203685477580" + ((int) (Math.random() * (100 - 8)) + 8);
    }

    public static String generateStringArray() {
        ArrayList<String> randomStrings = new ArrayList<>();
        IntStream.range(0, 3)
                .forEach(iteration -> randomStrings.add(RandomStringUtils.random(5, true, true)));
        String data = "[\"" + String.join("\",\"", randomStrings) + "\"]";
        return data;
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

    public static String generateCurrentDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        return dateFormatter.format(Calendar.getInstance().getTime());
    }
}
