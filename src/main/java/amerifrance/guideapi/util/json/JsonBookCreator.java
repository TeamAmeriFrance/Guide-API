package amerifrance.guideapi.util.json;

import amerifrance.guideapi.GuideMod;
import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.ITypeReader;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.util.LogHelper;
import amerifrance.guideapi.util.json.serialization.SerializerBook;
import amerifrance.guideapi.util.json.serialization.SerializerItemStack;
import amerifrance.guideapi.util.json.serialization.SerializerResourceLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.*;

public class JsonBookCreator {

    public static Gson gson;

    public static void buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.serializeNulls();
        gsonBuilder.disableHtmlEscaping();
        gsonBuilder.registerTypeAdapter(Book.class, new SerializerBook());
        gsonBuilder.registerTypeAdapter(ItemStack.class, new SerializerItemStack());
        gsonBuilder.registerTypeAdapter(ResourceLocation.class, new SerializerResourceLocation());
        for (ITypeReader typeReader : GuideAPI.getTypeReaders())
            gsonBuilder.registerTypeAdapter(typeReader.getType(), typeReader);

        gson = gsonBuilder.create();
    }

    public static void buildBooks() {
        File folder = new File(GuideMod.getConfigDir(), "books");
        if (folder.exists() || (!folder.exists() && folder.mkdirs())) {
            File[] files = folder.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
            for (File file : files)
                GuideAPI.BOOKS.register(jsonToBook(file));
        }
    }

    public static Book jsonToBook(File file) {
        try {
            return gson.fromJson(new FileReader(file), Book.class);
        } catch (IOException e) {
            LogHelper.error(e.getLocalizedMessage());
        }

        return null;
    }

    public static void bookToJson(Book book) {
        try {
            String json = gson.toJson(book, Book.class);
            FileWriter fileWriter = new FileWriter(new File(GuideMod.getConfigDir() + "/books", book.getLocalizedBookTitle().replaceAll(" ", "") + ".json"));
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            LogHelper.error(e.getLocalizedMessage());
        }
    }
}
