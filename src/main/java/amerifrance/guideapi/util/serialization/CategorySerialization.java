package amerifrance.guideapi.util.serialization;

import amerifrance.guideapi.categories.CategoryItemStack;
import amerifrance.guideapi.interfaces.ICategorySerializing;
import amerifrance.guideapi.objects.CategoryBase;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Type;
import java.util.List;

public class CategorySerialization {

    public static ICategorySerializing categoryBaseSerialization = new ICategorySerializing() {
        @Override
        public CategoryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            List<EntryAbstract> list = context.deserialize(json.getAsJsonObject().get("entryList"), new TypeToken<List<EntryAbstract>>() {
            }.getType());
            String name = json.getAsJsonObject().get("unlocCategoryName").getAsString();
            return new CategoryBase(list, name);
        }

        @Override
        public JsonObject serialize(CategoryAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("categoryType", context.serialize(src.getClass().getSimpleName()));
            jsonObject.add("unlocCategoryName", context.serialize(src.unlocCategoryName));
            jsonObject.add("entryList", context.serialize(src.entryList));
            return jsonObject;
        }
    };

    public static ICategorySerializing categoryItemStackSerialization = new ICategorySerializing() {
        @Override
        public CategoryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            List<EntryAbstract> list = context.deserialize(json.getAsJsonObject().get("entryList"), new TypeToken<List<EntryAbstract>>() {
            }.getType());
            String name = json.getAsJsonObject().get("unlocCategoryName").getAsString();
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("itemStack"), ItemStack.class);
            return new CategoryItemStack(list, name, stack);
        }

        @Override
        public JsonObject serialize(CategoryAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("categoryType", context.serialize(src.getClass().getSimpleName()));
            jsonObject.add("unlocCategoryName", context.serialize(src.unlocCategoryName));
            jsonObject.add("entryList", context.serialize(src.entryList));
            if (src instanceof CategoryItemStack) {
                CategoryItemStack category = (CategoryItemStack) src;
                jsonObject.add("itemStack", context.serialize(category.stack));
            }
            return jsonObject;
        }
    };

    public static void registerCategorySerializers() {
        BookCreator.addCategorySerializingToMap(CategoryBase.class, categoryBaseSerialization);
        BookCreator.addCategorySerializingToMap(CategoryItemStack.class, categoryItemStackSerialization);
    }
}
