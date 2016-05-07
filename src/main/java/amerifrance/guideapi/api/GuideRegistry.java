package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Deprecated: Use {@link GuideAPI} (specifically {@link GuideAPI#BOOKS} for what used to be done here.
 */
@Deprecated
public class GuideRegistry {

    /**
     * Registers your book. Can also create a basic model for your book item.
     *
     * @param book        - Book to register
     * @param createModel - If a model should be created or not. Still adheres to {@link Book#isCustomModel()}
     */
    public static void registerBook(Book book, boolean createModel) {
        GuideAPI.BOOKS.register(book);
    }

    public static void registerBook(Book book) {
        registerBook(book, false);
    }

    /**
     * If the book does not have a custom model, this will also register a render for the book.
     *
     * @param book - The book to register
     */
    public static void registerBook(Book book, FMLPreInitializationEvent event) {
        registerBook(book);
    }

    /**
     * @param index - The index of the book
     * @return the book corresponding to the index given
     */
    public static Book getBook(int index) {
        return GuideAPI.BOOKS.getValues().get(index);
    }

    /**
     * @param book - The book of which to get the index
     * @return the index of the book given
     */
    public static int getIndexOf(Book book) {
        return GuideAPI.BOOKS.getValues().indexOf(book);
    }

    /**
     * @return the number of registered books
     */
    public static int getSize() {
        return GuideAPI.BOOKS.getValues().size();
    }

    /**
     * @return whether or not there are books registered
     */
    public static boolean isEmpty() {
        return GuideAPI.BOOKS.getValues().isEmpty();
    }

    /**
     * @param book - The book of which to get the itemstack
     * @return an itemstack corresponding to the ingame book
     */
    public static ItemStack getItemStackForBook(Book book) {
        return new ItemStack(GuideAPI.guideBook, 1, getIndexOf(book)).copy();
    }

    /**
     * @return a copy of the booklist
     */
    public static List<Book> getBookList() {
        return new ArrayList<Book>(GuideAPI.BOOKS.getValues());
    }
}
