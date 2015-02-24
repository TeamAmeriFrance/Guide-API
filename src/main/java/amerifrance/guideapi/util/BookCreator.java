package amerifrance.guideapi.util;

import amerifrance.guideapi.entries.EntryText;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.CategoryBase;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import amerifrance.guideapi.objects.abstraction.PageAbstract;
import com.google.gson.*;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class BookCreator {


    public static Book createBookFromJson(GsonBuilder gsonBuilder, String fileName) {
        Gson gson = gsonBuilder.create();
        try {
            return gson.fromJson(new FileReader(fileName), Book.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void registerCustomSerializers(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(ItemStack.class, new CustomItemStackJson());
        gsonBuilder.registerTypeAdapter(Color.class, new CustomColorJson());
        gsonBuilder.registerTypeAdapter(EntryAbstract.class, new CustomEntryJson());
        gsonBuilder.registerTypeAdapter(CategoryAbstract.class, new CustomCategoryJson());
        gsonBuilder.registerTypeAdapter(Book.class, new CustomBookJson());
    }

    public static class CustomItemStackJson implements JsonDeserializer<ItemStack>, JsonSerializer<ItemStack> {

        @Override
        public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            boolean isBlock = json.getAsJsonObject().get("IsBlock").getAsBoolean();
            String name = json.getAsJsonObject().get("Name").getAsString();
            int meta = json.getAsJsonObject().get("Metadata").getAsInt();
            if (isBlock) {
                return new ItemStack(GameData.getBlockRegistry().getObject(name), 1, meta);
            } else {
                return new ItemStack(GameData.getItemRegistry().getObject(name), 1, meta);
            }
        }

        @Override
        public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("IsBlock", src.getItem() instanceof ItemBlock);
            if (src.getItem() instanceof ItemBlock) {
                jsonObject.addProperty("Name", GameData.getBlockRegistry().getNameForObject(Block.getBlockFromItem(src.getItem())));
            } else {
                jsonObject.addProperty("Name", GameData.getItemRegistry().getNameForObject(src.getItem()));
            }
            jsonObject.addProperty("Metadata", src.getItemDamage());
            return jsonObject;
        }
    }

    public static class CustomColorJson implements JsonDeserializer<Color>, JsonSerializer<Color> {

        @Override
        public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            int red = json.getAsJsonObject().get("Red").getAsInt();
            int green = json.getAsJsonObject().get("Green").getAsInt();
            int blue = json.getAsJsonObject().get("Blue").getAsInt();
            int alpha = json.getAsJsonObject().get("Alpha").getAsInt();
            return new Color(red, green, blue, alpha);
        }

        @Override
        public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Red", src.getRed());
            jsonObject.addProperty("Green", src.getGreen());
            jsonObject.addProperty("Blue", src.getBlue());
            jsonObject.addProperty("Alpha", src.getAlpha());
            return jsonObject;
        }
    }

    public static class CustomEntryJson implements JsonDeserializer<EntryAbstract>, JsonSerializer<EntryAbstract> {

        @Override
        public EntryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("UnlocEntryName").getAsString();
            List<PageAbstract> list = context.deserialize(json.getAsJsonObject().get("PageList"), List.class);
            return new EntryText(list, name);
        }

        @Override
        public JsonElement serialize(EntryAbstract src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("UnlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("PageList", context.serialize(src.pageList));
            return jsonObject;
        }
    }

    public static class CustomCategoryJson implements JsonDeserializer<CategoryAbstract>, JsonSerializer<CategoryAbstract> {

        @Override
        public CategoryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("UnlocCategoryName").getAsString();
            List<EntryAbstract> list = context.deserialize(json.getAsJsonObject().get("EntryList"), List.class);
            return new CategoryBase(list, name);
        }

        @Override
        public JsonElement serialize(CategoryAbstract src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("UnlocCategoryName", context.serialize(src.unlocCategoryName));
            jsonObject.add("EntryList", context.serialize(src.entryList));
            jsonObject.addProperty("Type", src.getClass().getName());
            return jsonObject;
        }
    }

    public static class CustomBookJson implements JsonDeserializer<Book>, JsonSerializer<Book> {

        @Override
        public Book deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String displayName = json.getAsJsonObject().get("UnlocDisplayName").getAsString();
            String welcome = json.getAsJsonObject().get("UnlocWelcomeMessage").getAsString();
            String title = json.getAsJsonObject().get("UnlocBookTitle").getAsString();
            Color color = context.deserialize(json.getAsJsonObject().get("Color"), Color.class);
            List<CategoryAbstract> list = context.deserialize(json.getAsJsonObject().get("CategoryList"), List.class);
            return new Book(list, title, welcome, displayName, color);
        }

        @Override
        public JsonElement serialize(Book src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("UnlocDisplayName", context.serialize(src.unlocDisplayName));
            jsonObject.add("UnlocWelcomeMessage", context.serialize(src.unlocWelcomeMessage));
            jsonObject.add("UnlocBookTitle", context.serialize(src.unlocBookTitle));
            jsonObject.add("Color", context.serialize(src.bookColor));
            jsonObject.add("CategoryList", context.serialize(src.categoryList));
            return jsonObject;
        }
    }
}
