package stephelper;

import java.util.HashMap;
import java.util.Map;

public class Memory {

    private static Memory INSTANCE;
    private static final Map<String, String> map = new HashMap<>();

    public static void getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Memory();
        }
    }

    public static void put(
            String key,
            String value
    ) {
        map.put(
                key,
                value
        );
    }

    public static String get(
            String key
    ) {
        return map.get(key);
    }

    public static void clear() {

        map.clear();
    }
}
