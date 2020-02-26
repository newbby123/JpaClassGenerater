package mapping;

import java.util.concurrent.ConcurrentHashMap;

public class TypeMapping {
    public final static ConcurrentHashMap<String, String> typeMapping = new ConcurrentHashMap<String, String>() {{
        put("int", "Long");
        put("float", "Double");
        put("double", "Double");
        put("decimal", "Double");
        put("real", "Double");
        put("bool", "Boolean");
        put("boolean", "Boolean");
        put("datetime", "java.util.Date");
        put("timestamp", "java.sql.Date");
        put("date", "java.sql.Date");
        put("time", "java.sql.Time");
    }};

    public static String get(String key) {
        return typeMapping.get(key);
    }
}
