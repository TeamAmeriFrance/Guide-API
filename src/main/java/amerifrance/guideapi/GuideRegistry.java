package amerifrance.guideapi;

import amerifrance.guideapi.items.ItemsRegistry;
import amerifrance.guideapi.objects.Book;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuideRegistry {

    private static List<Book> bookList = new ArrayList<Book>();

    public static void registerBook(Book book) {
        bookList.add(book);
    }

    public static Book getBook(int index) {
        return bookList.get(index);
    }

    public static int getIndexOf(Book book) {
        return bookList.indexOf(book);
    }

    public static int getSize() {
        return bookList.size();
    }

    public static boolean isEmpty() {
        return bookList.isEmpty();
    }

    public static ItemStack getItemStackForBook(Book book) {
        return new ItemStack(ItemsRegistry.guideBook, 1, getIndexOf(book));
    }
}
