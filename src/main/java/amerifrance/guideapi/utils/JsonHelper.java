package amerifrance.guideapi.utils;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.deserialization.DeserializerRegistry;
import amerifrance.guideapi.deserialization.JsonDeserializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JsonHelper {

    public static Stream<JsonObject> jsonObjectStream(JsonArray jsonArray) {
        return StreamSupport.stream(jsonArray.spliterator(), false).map(JsonElement::getAsJsonObject);
    }

    public static Stream<JsonPrimitive> jsonPrimitiveStream(JsonArray jsonArray) {
        return StreamSupport.stream(jsonArray.spliterator(), false).map(JsonElement::getAsJsonPrimitive);
    }

    public static Object deserializeFromJson(JsonObject jsonObject, Object... initParameters) {
        Deserializer deserializer = DeserializerRegistry.get(jsonObject.get("deserializer").getAsString());
        return deserializer.deserialize(jsonObject.toString(), initParameters);
    }

    public static Object deserializeFromJsonString(String json) {
        return deserializeFromJson(JsonDeserializer.GSON.fromJson(json, JsonObject.class));
    }
}
