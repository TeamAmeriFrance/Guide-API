package amerifrance.guideapi.util.serialization;

import java.awt.Color;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import amerifrance.guideapi.api.util.BookBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import org.apache.commons.io.filefilter.FileFilterUtils;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.interfaces.ITypeReader;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
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

    public static void registerJsonBooks(GsonBuilder gsonBuilder) {
        File folder = new File(GuideAPI.getConfigDir().getPath() + "/books");
        folder.mkdir();
        File[] files = folder.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        for (File file : files) GuideRegistry.registerBook(BookCreator.createBookFromJson(gsonBuilder, file));
    }

    public static Book createBookFromJson(GsonBuilder gsonBuilder, File file) {
        try {
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            Book book = gson.fromJson(new FileReader(file), Book.class);
              // Uncomment for test serialization
//            String reverse = gson.toJson(book, Book.class);
//            FileWriter fw = new FileWriter(new File(GuideAPI.getConfigDir().getPath() + "/test.json"));
//            fw.write(reverse);
//            fw.close();
            return book;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createJsonFromBook(GsonBuilder gsonBuilder, Book book) {
        try {
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            String reverse = gson.toJson(book, Book.class);
            FileWriter fw = new FileWriter(new File(GuideAPI.getConfigDir().getPath(), book.getLocalizedDisplayName() + ".json"));
            fw.write(reverse);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        gsonBuilder.registerTypeHierarchyAdapter(EntryAbstract.class, new CustomEntryJson());
        gsonBuilder.registerTypeHierarchyAdapter(CategoryAbstract.class, new CustomCategoryJson());
        gsonBuilder.registerTypeHierarchyAdapter(Book.class, new CustomBookJson());
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
            boolean spawnWithBook = json.getAsJsonObject().get("spawnWithBook").getAsBoolean();
            boolean isLostBook = json.getAsJsonObject().get("isLostBook").getAsBoolean();
            List<CategoryAbstract> list = context.deserialize(json.getAsJsonObject().get("categoryList"), new TypeToken<List<CategoryAbstract>>() {
            }.getType());

            BookBuilder builder = new BookBuilder();
            builder.setCategories(list);
            builder.setUnlocBookTitle(title);
            builder.setUnlocWelcomeMessage(welcome);
            builder.setUnlocDisplayName(displayName);
            builder.setBookColor(color);
            builder.setSpawnWithBook(spawnWithBook);
            builder.setIsLostBook(isLostBook);

            return builder.build();
        }

        @Override
        public JsonElement serialize(Book src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("unlocDisplayName", context.serialize(src.unlocDisplayName));
            jsonObject.add("unlocWelcomeMessage", context.serialize(src.unlocWelcomeMessage));
            jsonObject.add("unlocBookTitle", context.serialize(src.unlocBookTitle));
            jsonObject.add("color", context.serialize(src.bookColor));
            jsonObject.add("spawnWithBook", context.serialize(src.spawnWithBook));
            jsonObject.add("isLostBook", context.serialize(src.isLostBook));
            jsonObject.add("categoryList", context.serialize(src.categoryList));
            return jsonObject;
        }
    }
}
