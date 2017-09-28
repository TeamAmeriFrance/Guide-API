package amerifrance.guideapi.api.util;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;

public class BookHelper {

    public static CategoryAbstract getCategoryFromName(Book book, String unlocName) {
        for (CategoryAbstract category : book.getCategoryList()) {
            if (category.name.equals(unlocName))
                return category;
        }
        return null;
    }

    public static EntryAbstract getEntryFromName(CategoryAbstract category, String unlocName) {
        for (EntryAbstract entry : category.getEntries().values()) {
            if (entry.name.equals(unlocName))
                return entry;
        }
        return null;
    }
}
