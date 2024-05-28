package stephelper;

import constants.Constants;
import utils.FileUtil;

import java.util.HashMap;

public class RequestComposer {

    private static final String PREFIX = "#{placeholder_";
    private static final String POSTFIX = "}";

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
