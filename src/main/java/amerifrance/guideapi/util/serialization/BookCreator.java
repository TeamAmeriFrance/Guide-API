package amerifrance.guideapi.util.serialization;

import amerifrance.guideapi.GuideMod;
import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.iface.ITypeReader;
import com.google.common.collect.Maps;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class BookCreator {

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .registerTypeAdapter(ItemStack.class, new CustomItemStackJson())
            .registerTypeAdapter(Color.class, new CustomColorJson())
            .registerTypeAdapter(IPage.class, new CustomPageJson())
            .registerTypeHierarchyAdapter(EntryAbstract.class, new CustomEntryJson())
            .registerTypeHierarchyAdapter(CategoryAbstract.class, new CustomCategoryJson())
            .registerTypeHierarchyAdapter(Book.class, new CustomBookJson())
            .create();

    private static Map<Class, ITypeReader> serializers = Maps.newHashMap();
    private static Map<String, Class<?>> idents = Maps.newHashMap();

    public static void registerJsonBooks() {
        File folder = new File(GuideMod.getConfigDir(), "books");
        folder.mkdir();
        File[] files = folder.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        for (File file : files)
            GuideAPI.BOOKS.register(createBookFromJson(file));
    }

    public static Book createBookFromJson(File file) {
        try {
            return GSON.fromJson(new FileReader(file), Book.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createJsonFromBook(Book book) {
        try {
            String reverse = GSON.toJson(book, Book.class);
            FileWriter fw = new FileWriter(new File(GuideMod.getConfigDir().getPath(), book.getLocalizedDisplayName() + ".json"));
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

    public static class CustomItemStackJson implements JsonDeserializer<ItemStack>, JsonSerializer<ItemStack> {

        @Override
        public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("name").getAsString();
            int meta = json.getAsJsonObject().get("metadata").getAsInt();

            return new ItemStack(GameData.getItemRegistry().getObject(new ResourceLocation(name)), 1, meta);
        }

        @Override
        public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", GameData.getItemRegistry().getNameForObject(src.getItem()).toString());
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
            String displayName = json.getAsJsonObject().get("displayName").getAsString();
            String welcome = json.getAsJsonObject().get("welcomeMessage").getAsString();
            String title = json.getAsJsonObject().get("title").getAsString();
            String author = json.getAsJsonObject().get("author").getAsString();
            Color color = context.deserialize(json.getAsJsonObject().get("color"), Color.class);
            boolean spawnWithBook = json.getAsJsonObject().get("spawnWithBook").getAsBoolean();
            List<CategoryAbstract> list = context.deserialize(json.getAsJsonObject().get("categoryList"), new TypeToken<List<CategoryAbstract>>() {
            }.getType());

            Book book = new Book();
            book.setCategoryList(list);
            book.setTitle(title);
            book.setWelcomeMessage(welcome);
            book.setDisplayName(displayName);
            book.setAuthor(author);
            book.setColor(color);
            book.setSpawnWithBook(spawnWithBook);

            return book;
        }

        @Override
        public JsonElement serialize(Book src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("displayName", context.serialize(src.getDisplayName()));
            jsonObject.add("welcomeMessage", context.serialize(src.getWelcomeMessage()));
            jsonObject.add("title", context.serialize(src.getTitle()));
            jsonObject.add("author", context.serialize(src.getAuthor()));
            jsonObject.add("color", context.serialize(src.getColor()));
            jsonObject.add("spawnWithBook", context.serialize(src.isSpawnWithBook()));
            jsonObject.add("categoryList", context.serialize(src.getCategoryList()));
            return jsonObject;
        }
    }
}
