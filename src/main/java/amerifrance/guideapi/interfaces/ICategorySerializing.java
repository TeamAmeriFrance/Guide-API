package amerifrance.guideapi.interfaces;

import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import com.google.gson.*;

import java.lang.reflect.Type;

public interface ICategorySerializing {

    public CategoryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException;

    public JsonObject serialize(CategoryAbstract src, Type typeofSrc, JsonSerializationContext context);
}
