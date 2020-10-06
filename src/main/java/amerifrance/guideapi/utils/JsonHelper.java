package amerifrance.guideapi.utils;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.deserialization.registry.DeserializerRegistry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JsonHelper {

    public static Stream<JsonObject> jsonObjectStream(JsonArray jsonArray) {
        return StreamSupport.stream(jsonArray.spliterator(), false).map(JsonElement::getAsJsonObject);
    }

    public static Object deserializeFromJson(JsonObject jsonObject, Object... initParameters) {
        Deserializer deserializer = DeserializerRegistry.get(jsonObject.get("deserializer").getAsString());
        return deserializer.deserialize(jsonObject.toString(), initParameters);
    }
}
