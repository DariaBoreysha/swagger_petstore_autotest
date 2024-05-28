package utils;

import client.HttpClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.AtHttpUtilException;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;

public class HttpUtil {

    public static JsonNode convertHttpResponseToJsonNode(HttpResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream entityContent = HttpClient.extractHttpEntityContent(response);
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readValue(entityContent, JsonNode.class);
        } catch (IOException e) {
            throw new AtHttpUtilException(e);
        }
        return jsonNode;
    }
}
