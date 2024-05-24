package utils;

import exceptions.AtFileUtilException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static String readFileToString(String filePath) {
        String body;
        try {
            body = FileUtils.readFileToString(new File(filePath), "UTF-8");
        } catch (IOException e) {
            throw new AtFileUtilException(e);
        }
        return body;
    }
}
