package amerifrance.guideapi.api.util;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;

public class BookHelper {

    public static CategoryAbstract getCategoryFromName(Book book, String unlocName) {
        for (CategoryAbstract category : book.getCategoryList()) {
            if (category.unlocCategoryName.equals(unlocName))
                return category;
        }
        return null;
    }

    public static EntryAbstract getEntryFromName(CategoryAbstract category, String unlocName) {
        for (EntryAbstract entry : category.entries.values()) {
            if (entry.unlocEntryName.equals(unlocName))
                return entry;
        }
        return null;
    }
}
