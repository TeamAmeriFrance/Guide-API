package amerifrance.guideapi.proxies;

import amerifrance.guideapi.GuideRegistry;
import amerifrance.guideapi.test.TestBooks;
import amerifrance.guideapi.util.serialization.BookCreator;
import com.google.gson.GsonBuilder;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerBooks() {
        TestBooks.setTestBooks();
    }

    @Override
    public void registerJsonBooks(GsonBuilder gsonBuilder) {
        GuideRegistry.registerBook(BookCreator.createBookFromJson(gsonBuilder, "TestBook.json"));
    }
}
