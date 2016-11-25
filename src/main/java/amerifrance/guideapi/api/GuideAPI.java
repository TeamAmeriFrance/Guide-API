package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

public class GuideAPI {

    public static final List<Book> BOOKS = Lists.newArrayList();
    public static final Map<Book, ItemStack> BOOK_TO_STACK = Maps.newHashMap();
    public static final Map<Book, Multimap<Class<? extends Block>, IInfoRenderer>> INFO_RENDERERS = Maps.newHashMap();

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
     * Registers an IInfoRenderer
     *
     * @param infoRenderer - The renderer to register
     * @param blockClasses - The block classes that this should draw for
     */
    public static void registerInfoRenderer(Book book, IInfoRenderer infoRenderer, Class<? extends Block>... blockClasses) {
        if (!INFO_RENDERERS.containsKey(book))
            INFO_RENDERERS.put(book, ArrayListMultimap.<Class<? extends Block>, IInfoRenderer>create());

        for (Class<? extends Block> blockClass : blockClasses)
            INFO_RENDERERS.get(book).put(blockClass, infoRenderer);
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
