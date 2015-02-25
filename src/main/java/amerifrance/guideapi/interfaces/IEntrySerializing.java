package amerifrance.guideapi.interfaces;

import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import com.google.gson.*;

import java.lang.reflect.Type;

public interface IEntrySerializing {

    public EntryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException;

    public JsonObject serialize(EntryAbstract src, Type typeofSrc, JsonSerializationContext context);
}
