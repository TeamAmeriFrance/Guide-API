package amerifrance.guideapi.proxies;

import amerifrance.guideapi.GuideRegistry;
import amerifrance.guideapi.test.TestBooks;
import amerifrance.guideapi.util.serialization.BookCreator;
import com.google.gson.GsonBuilder;

import java.io.File;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerBooks() {
        TestBooks.setTestBooks();
    }

    @Override
    public void registerJsonBooks(GsonBuilder gsonBuilder) {
        File folder = new File("guide-api");
        if (!folder.exists()) folder.mkdir();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                GuideRegistry.registerBook(BookCreator.createBookFromJson(gsonBuilder, file));
            }
        }
    }
}
