package amerifrance.guideapi.util.json.serialization;

import amerifrance.guideapi.util.json.JsonHelper;
import com.google.gson.*;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

public class SerializerResourceLocation extends SerializerBase<ResourceLocation> {

    public static final String IDENTIFIER = "identifier";

    @Override
    public ResourceLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new ResourceLocation(JsonHelper.getString(json, IDENTIFIER, "guideapi:bad_id"));
    }

    @Override
    public JsonElement serialize(ResourceLocation src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(IDENTIFIER, src.toString());
        return jsonObject;
    }
}
