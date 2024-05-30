package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.Constants;
import exceptions.AtStringUtilException;
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
                request = request.replace(placeholder, Memory.review(map.get(key)));
            }
        }
        return request;
    }
}
