package stephelper;

import com.fasterxml.jackson.databind.JsonNode;
import exceptions.AtMemoryException;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class Memory {

    private static Memory INSTANCE;
    private static final int GENERATE_SPACE_2_INDEX = 11;
    private static final String GENERATE_SPACE_2 = "GENERATE : ";
    private static final String MEMORY_SPACE_2 = "MEMORY : ";
    private static final int MEMORY_SPACE_2_INDEX = 9;
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

    public static String asString(String key) {
        return (String) map.get(key);
    }

    private static String defineGenerateVariable(String memoryVariable) {
        String value = TestDataGenerator.generate(memoryVariable);
        map.put(memoryVariable, value);
        return value;
    }

    public static String review(String value) {
        return reviewVariable(value);
    }

    private static String reviewVariable(String value) {
        if (value == null) {
            return "";
        }
        if (value.startsWith(GENERATE_SPACE_2)) {
            return defineGenerateVariable(value.substring(GENERATE_SPACE_2_INDEX));
        }
        if (value.startsWith(MEMORY_SPACE_2)) {
            return defineMemoryVariable(value.substring(MEMORY_SPACE_2_INDEX));
        }
        return value;
    }

    private static String defineMemoryVariable(String memoryVariable) {
        if (map.containsKey(memoryVariable)) {
            return (String) map.get(memoryVariable);
        } else {
            throw new AtMemoryException("'" + memoryVariable + "'" + " is ABSENT in MEMORY");
        }
    }
}
