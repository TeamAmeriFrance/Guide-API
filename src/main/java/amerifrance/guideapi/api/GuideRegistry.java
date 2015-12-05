package amerifrance.guideapi.api;

import amerifrance.guideapi.api.base.Book;
import com.google.gson.GsonBuilder;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class GuideRegistry {

    /**
     * The GsonBuilder used by Guide-API to create Books from Jsons. Access after Pre-Init
     */
    public static GsonBuilder bookBuilder;

    private static List<Book> bookList = new ArrayList<Book>();

    /**
     * @param book - The book to register
     */
    public static void registerBook(Book book) {
        bookList.add(book);
    }

    /**
     * @param index - The index of the book
     *
     * @return the book corresponding to the index given
     */
    public static Book getBook(int index) {
        return bookList.get(index);
    }

    /**
     * If you want to use a custom model for your book (IE: Different texture),
     * register it here. Make sure to call this on the Client only in the
     * postInit phase.
     *
     * @param book             - Book to register a custom model for.
     * @param resourceLocation - Location for the custom model. Generally modid:MODEL
     */
    @SideOnly(Side.CLIENT)
    public static void registerBookModel(Book book, String resourceLocation) {
        ModelBakery.addVariantName(GuideAPIItems.guideBook, resourceLocation);
        ModelLoader.setCustomModelResourceLocation(GuideAPIItems.guideBook, getIndexOf(book), new ModelResourceLocation(resourceLocation, "inventory"));
    }

    /**
     * @param book - The book of which to get the index
     *
     * @return the index of the book given
     */
    public static int getIndexOf(Book book) {
        return bookList.indexOf(book);
    }

    /**
     * @return the number of registered books
     */
    public static int getSize() {
        return bookList.size();
    }

    /**
     * @return whether or not there are books registered
     */
    public static boolean isEmpty() {
        return bookList.isEmpty();
    }

    /**
     * @param book - The book of which to get the itemstack
     *
     * @return an itemstack corresponding to the ingame book
     */
    public static ItemStack getItemStackForBook(Book book) {
        return new ItemStack(GuideAPIItems.guideBook, 1, getIndexOf(book)).copy();
    }

    /**
     * @return a copy of the booklist
     */
    public static List<Book> getBookList() {
        return new ArrayList<Book>(bookList);
    }
}
