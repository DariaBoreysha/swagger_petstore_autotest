package stephelper;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class Memory {

    private static Memory INSTANCE;
    private static final Map<String, Object> map = new HashMap<>();

    public static void getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Memory();
        }
    }

    public static void put(String key, Object value) {
        map.put(key, value);
    }

    public static void clear() {
        map.clear();
    }

    public static HttpResponse asHttpResponse(String key) {
        return (HttpResponse) map.get(key);
    }

    public static JsonNode asJsonNode(String key) {
        return (JsonNode) map.get(key);
    }
}
