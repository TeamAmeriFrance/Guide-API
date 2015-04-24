package amerifrance.guideapi.api.util;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;

public class BookHelper {

    public static CategoryAbstract getCategoryFromName(Book book, String unlocName) {
        for (CategoryAbstract category : book.categoryList) {
            if (category.unlocCategoryName.equals(unlocName))
                return category;
        }
        return null;
    }

    public static EntryAbstract getEntryFromName(CategoryAbstract category, String unlocName) {
        for (EntryAbstract entry : category.entryList) {
            if (entry.unlocEntryName.equals(unlocName))
                return entry;
        }
        return null;
    }
}
