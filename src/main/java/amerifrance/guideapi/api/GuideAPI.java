package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuideAPI {

    public static final List<Book> BOOKS = new ArrayList<Book>();
    public static final Map<Book, ItemStack> BOOK_TO_STACK = Maps.newHashMap();

    /**
     * Obtains a new ItemStack associated with the provided book.
     *
     * @param book - The book to get an ItemStack for.
     * @return - The ItemStack associated with the provided book.
     */
    public static ItemStack getStackFromBook(Book book) {
        return BOOK_TO_STACK.get(book);
    }

    /**
     * Helper method for setting a model for your book.
     * <p>
     * Use if you wish to use a custom model.
     * <p>
     * Only call <b>AFTER</b> you have registered your book.
     *
     * @param book        - Book to set model for
     * @param modelLoc    - Location of the model file
     * @param variantName - Variant to use
     */
    @SideOnly(Side.CLIENT)
    public static void setModel(Book book, ResourceLocation modelLoc, String variantName) {
        ModelLoader.setCustomModelResourceLocation(
                getStackFromBook(book).getItem(),
                0,
                new ModelResourceLocation(modelLoc, variantName)
        );
    }

    /**
     * Helper method for setting a model for your book.
     * <p>
     * Use if you wish to use the default model with color.
     * <p>
     * Only call <b>AFTER</b> you have registered your book.
     *
     * @param book - Book to set model for
     */
    @SideOnly(Side.CLIENT)
    public static void setModel(Book book) {
        setModel(book, new ResourceLocation("guideapi", "ItemGuideBook"), "inventory");
    }

    public static void initialize() {
        // No-op. Just here to initialize fields.
    }
}
