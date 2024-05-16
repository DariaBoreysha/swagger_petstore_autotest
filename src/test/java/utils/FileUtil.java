package utils;

import exceptions.FileUtilException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class FileUtil {

    public static String convertJsonEntityToString(HttpResponse response) {
        HttpEntity jsonEntity = response.getEntity();
        String jsonBody;
        try {
            jsonBody = EntityUtils.toString(jsonEntity, "UTF-8");
        } catch (IOException e) {
            throw new FileUtilException(e);
        }
        return jsonBody;
    }
}
