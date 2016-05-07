package amerifrance.guideapi.util.json.serialization;

import com.google.gson.*;

import java.lang.reflect.Type;

public abstract class SerializerBase<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    public abstract T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException;

    @Override
    public abstract JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context);
}
