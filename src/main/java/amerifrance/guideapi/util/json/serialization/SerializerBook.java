package amerifrance.guideapi.util.json.serialization;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.util.json.JsonHelper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.List;

public class SerializerBook extends SerializerBase<Book> {

    public static final String TITLE = "title";
    public static final String WELCOME = "welcomeMessage";
    public static final String DISPLAY = "displayName";
    public static final String AUTHOR = "author";
    public static final String COLOR = "color";
    public static final String SPAWNWITH = "spawnWith";
    public static final String CATEGORIES = "categories";

    @Override
    public Book deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Book book = new Book();
        book.setTitle(JsonHelper.getString(json, TITLE, "JsonCreated"));
        book.setWelcomeMessage(JsonHelper.getString(json, WELCOME, "JsonCreated"));
        book.setDisplayName(JsonHelper.getString(json, DISPLAY, "JsonBook"));
        book.setAuthor(JsonHelper.getString(json, AUTHOR, "JsonCreated"));
        book.setColor(JsonHelper.getColor(json, COLOR, Color.GREEN));
        book.setSpawnWithBook(JsonHelper.getBoolean(json, SPAWNWITH, false));
        book.setCategoryList((List<CategoryAbstract>) context.deserialize(json.getAsJsonObject().get(CATEGORIES), new TypeToken<List<CategoryAbstract>>() {
        }.getType()));
        book.setRegistryName(book.getTitle().replaceAll(" ", ""));
        return book;
    }

    @Override
    public JsonElement serialize(Book src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TITLE, src.getTitle());
        jsonObject.addProperty(WELCOME, src.getWelcomeMessage());
        jsonObject.addProperty(DISPLAY, src.getDisplayName());
        jsonObject.addProperty(AUTHOR, src.getAuthor());
        jsonObject.addProperty(COLOR, "#" + Integer.toHexString(src.getColor().getRGB()).substring(2).toUpperCase());
        jsonObject.addProperty(SPAWNWITH, src.isSpawnWithBook());
        jsonObject.add(CATEGORIES, context.serialize(src.getCategoryList(), new TypeToken<List<CategoryAbstract>>() {
        }.getType()));
        return jsonObject;
    }
}
