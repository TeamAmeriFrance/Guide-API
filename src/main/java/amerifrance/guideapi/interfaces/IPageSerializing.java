package amerifrance.guideapi.interfaces;

import amerifrance.guideapi.objects.abstraction.PageAbstract;
import com.google.gson.*;

import java.lang.reflect.Type;

public interface IPageSerializing {

    public PageAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException;

    public JsonObject serialize(PageAbstract src, Type typeofSrc, JsonSerializationContext context);
}
