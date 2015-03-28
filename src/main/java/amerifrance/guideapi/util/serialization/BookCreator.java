package amerifrance.guideapi.util.serialization;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import amerifrance.guideapi.interfaces.ITypeReader;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;

import com.google.common.collect.Maps;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import cpw.mods.fml.common.registry.GameData;

public class BookCreator {

    @SuppressWarnings("rawtypes")
    private static Map<Class, ITypeReader> serializers = Maps.newHashMap();
    private static Map<String, Class<?>> idents = Maps.newHashMap();

    public static Book createBookFromJson(GsonBuilder gsonBuilder, File file) {
        try {
            return gsonBuilder.setPrettyPrinting().create().fromJson(new FileReader(file), Book.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void registerSerializer(ITypeReader<?> serializer) {
        serializers.put(serializer.getType(), serializer);
        idents.put(serializer.getType().getSimpleName(), serializer.getType());
    }

    public static void registerCustomSerializers(GsonBuilder gsonBuilder) {
        TypeReaders.init();
        gsonBuilder.registerTypeAdapter(ItemStack.class, new CustomItemStackJson());
        gsonBuilder.registerTypeAdapter(Color.class, new CustomColorJson());
        gsonBuilder.registerTypeAdapter(IPage.class, new CustomPageJson());
        gsonBuilder.registerTypeAdapter(EntryAbstract.class, new CustomEntryJson());
        gsonBuilder.registerTypeAdapter(CategoryAbstract.class, new CustomCategoryJson());
        gsonBuilder.registerTypeAdapter(Book.class, new CustomBookJson());
    }

    public static class CustomItemStackJson implements JsonDeserializer<ItemStack>, JsonSerializer<ItemStack> {

        @Override
        public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            boolean isBlock = json.getAsJsonObject().get("isBlock").getAsBoolean();
            String name = json.getAsJsonObject().get("name").getAsString();
            int meta = json.getAsJsonObject().get("metadata").getAsInt();
            if (isBlock) {
                return new ItemStack(GameData.getBlockRegistry().getObject(name), 1, meta);
            } else {
                return new ItemStack(GameData.getItemRegistry().getObject(name), 1, meta);
            }
        }

        @Override
        public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("isBlock", src.getItem() instanceof ItemBlock);
            if (src.getItem() instanceof ItemBlock) {
                jsonObject.addProperty("name", GameData.getBlockRegistry().getNameForObject(Block.getBlockFromItem(src.getItem())));
            } else {
                jsonObject.addProperty("name", GameData.getItemRegistry().getNameForObject(src.getItem()));
            }
            jsonObject.addProperty("metadata", src.getItemDamage());
            return jsonObject;
        }
    }

    public static class CustomColorJson implements JsonDeserializer<Color>, JsonSerializer<Color> {

        @Override
        public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            int red = json.getAsJsonObject().get("red").getAsInt();
            int green = json.getAsJsonObject().get("green").getAsInt();
            int blue = json.getAsJsonObject().get("blue").getAsInt();
            int alpha = json.getAsJsonObject().get("alpha").getAsInt();
            return new Color(red, green, blue, alpha);
        }

        @Override
        public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("red", src.getRed());
            jsonObject.addProperty("green", src.getGreen());
            jsonObject.addProperty("blue", src.getBlue());
            jsonObject.addProperty("alpha", src.getAlpha());
            return jsonObject;
        }
    }

    public static class CustomPageJson implements JsonDeserializer<IPage>, JsonSerializer<IPage> {

        @Override
        public IPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = context.deserialize(json.getAsJsonObject().get("type"), String.class);
            return (IPage) serializers.get(idents.get(name)).deserialize(json, typeOfT, context);
        }

        @SuppressWarnings("unchecked")
        @Override
        public JsonElement serialize(IPage src, Type typeOfSrc, JsonSerializationContext context) {
            return serializers.get(src.getClass()).serialize(src, typeOfSrc, context);
        }
    }

    public static class CustomEntryJson implements JsonDeserializer<EntryAbstract>, JsonSerializer<EntryAbstract> {

        @Override
        public EntryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = context.deserialize(json.getAsJsonObject().get("type"), String.class);
            return (EntryAbstract) serializers.get(idents.get(name)).deserialize(json, typeOfT, context);
        }

        @SuppressWarnings("unchecked")
        @Override
        public JsonElement serialize(EntryAbstract src, Type typeOfSrc, JsonSerializationContext context) {
            return serializers.get(src.getClass()).serialize(src, typeOfSrc, context);
        }
    }

    public static class CustomCategoryJson implements JsonDeserializer<CategoryAbstract>, JsonSerializer<CategoryAbstract> {

        @Override
        public CategoryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = context.deserialize(json.getAsJsonObject().get("type"), String.class);
            return (CategoryAbstract) serializers.get(idents.get(name)).deserialize(json, typeOfT, context);
        }

        @SuppressWarnings("unchecked")
        @Override
        public JsonElement serialize(CategoryAbstract src, Type typeOfSrc, JsonSerializationContext context) {
            return serializers.get(src.getClass()).serialize(src, typeOfSrc, context);
        }
    }

    public static class CustomBookJson implements JsonDeserializer<Book>, JsonSerializer<Book> {

        @Override
        public Book deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String displayName = json.getAsJsonObject().get("unlocDisplayName").getAsString();
            String welcome = json.getAsJsonObject().get("unlocWelcomeMessage").getAsString();
            String title = json.getAsJsonObject().get("unlocBookTitle").getAsString();
            Color color = context.deserialize(json.getAsJsonObject().get("color"), Color.class);
            List<CategoryAbstract> list = context.deserialize(json.getAsJsonObject().get("categoryList"), new TypeToken<List<CategoryAbstract>>() {
            }.getType());
            return new Book(list, title, welcome, displayName, color);
        }

        @Override
        public JsonElement serialize(Book src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("unlocDisplayName", context.serialize(src.unlocDisplayName));
            jsonObject.add("unlocWelcomeMessage", context.serialize(src.unlocWelcomeMessage));
            jsonObject.add("unlocBookTitle", context.serialize(src.unlocBookTitle));
            jsonObject.add("color", context.serialize(src.bookColor));
            jsonObject.add("categoryList", context.serialize(src.categoryList));
            return jsonObject;
        }
    }
}
