package stephelper;

import utils.FileUtil;

import java.util.HashMap;

public class RequestComposer {

    private static final String PREFIX = "#{placeholder_";
    private static final String POSTFIX = "}";

    public static String composeRequest(String fileSampleName, HashMap<String, String> map) {
        String output = FileUtil.readFileToString(Constants.SAMPLES_FOLDER + fileSampleName);
        for (String key : map.keySet()) {
            String placeholder = PREFIX + key + POSTFIX;
            if (output.contains(placeholder)) {
                output = output.replace(placeholder, map.get(key));
            }
        }
        return output;
    }
}
