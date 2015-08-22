package amerifrance.guideapi.api.registry;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.api.GuideAPIItems;
import amerifrance.guideapi.api.base.Book;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
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

    @SideOnly(Side.CLIENT)
    public static void registerBookModel(Book book, String resourceLocation) {
        int meta = getIndexOf(book);

        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

        ModelBakery.addVariantName(GuideAPIItems.guideBook, resourceLocation);
        renderItem.getItemModelMesher().register(GuideAPIItems.guideBook, meta, new ModelResourceLocation(resourceLocation, "inventory"));
    }

    /**
     * @param index - The index of the book
     * @return the book corresponding to the index given
     */
    public static Book getBook(int index) {
        return bookList.get(index);
    }


    /**
     * @param book - The book of which to get the index
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
