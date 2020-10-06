package amerifrance.guideapi.deserialization;

import amerifrance.guideapi.api.Deserializer;
import com.google.common.collect.Maps;

import java.util.Map;

public class DeserializerRegistry {
    private static final Map<String, Deserializer> registry = Maps.newLinkedHashMap();

    public static void register(String key, Deserializer deserializer) {
        registry.put(key, deserializer);
    }

    public static Deserializer get(String key) {
        return registry.get(key);
    }
}
