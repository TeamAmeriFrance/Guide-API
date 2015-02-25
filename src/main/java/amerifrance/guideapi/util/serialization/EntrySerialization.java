package amerifrance.guideapi.util.serialization;

import amerifrance.guideapi.entries.EntryText;
import amerifrance.guideapi.interfaces.IEntrySerializing;
import amerifrance.guideapi.objects.EntryBase;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import amerifrance.guideapi.objects.abstraction.PageAbstract;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class EntrySerialization {

    public static IEntrySerializing entryBaseSerialization = new IEntrySerializing() {
        @Override
        public EntryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("unlocEntryName").getAsString();
            List<PageAbstract> list = context.deserialize(json.getAsJsonObject().get("pageList"), new TypeToken<List<PageAbstract>>() {
            }.getType());
            return new EntryBase(list, name);
        }

        @Override
        public JsonObject serialize(EntryAbstract src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("entryType", context.serialize(src.getClass().getSimpleName()));
            jsonObject.add("unlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("pageList", context.serialize(src.pageList));
            return jsonObject;
        }
    };

    public static IEntrySerializing entryTextSerialization = new IEntrySerializing() {
        @Override
        public EntryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("unlocEntryName").getAsString();
            List<PageAbstract> list = context.deserialize(json.getAsJsonObject().get("pageList"), new TypeToken<List<PageAbstract>>() {
            }.getType());
            return new EntryText(list, name);
        }

        @Override
        public JsonObject serialize(EntryAbstract src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("entryType", context.serialize(src.getClass().getSimpleName()));
            jsonObject.add("unlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("pageList", context.serialize(src.pageList));
            return jsonObject;
        }
    };

    public static void registerEntrySerializers() {
        BookCreator.addEntrySerializingToMap(EntryBase.class, entryBaseSerialization);
        BookCreator.addEntrySerializingToMap(EntryText.class, entryTextSerialization);
    }
}
