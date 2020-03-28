package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import com.google.common.collect.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


import java.util.List;
import java.util.Map;

public class GuideAPI {

    private static final Map<ResourceLocation, Book> BOOKS = Maps.newHashMap();
    private static final Map<Book, ItemStack> BOOK_TO_STACK = Maps.newHashMap();
    private static final Map<Book, Multimap<Class<? extends Block>, IInfoRenderer>> INFO_RENDERERS = Maps.newHashMap();
    private static List<Book> indexedBooks = Lists.newArrayList();

    /**
     * Obtains a new ItemStack associated with the provided book.
     *
     * @param book - The book to get an ItemStack for.
     * @return - The ItemStack associated with the provided book.
     */
    public static ItemStack getStackFromBook(Book book) {
        return BOOK_TO_STACK.get(book) == null ? ItemStack.EMPTY : BOOK_TO_STACK.get(book);
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
    @OnlyIn(Dist.CLIENT)
    public static void setModel(Book book, ResourceLocation modelLoc, String variantName) {
        ModelResourceLocation mrl = new ModelResourceLocation(modelLoc, variantName);
//        ModelLoader.setCustomModelResourceLocation(
//                getStackFromBook(book).getItem(),
//                0,
//                mrl
//        ); TODO
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
    @OnlyIn(Dist.CLIENT)
    public static void setModel(Book book) {
        setModel(book, new ResourceLocation("guideapi", "ItemGuideBook"), "inventory");
    }

    public static void initialize() {
        // No-op. Just here to initialize fields.
    }

    public static Map<ResourceLocation, Book> getBooks() {
        return ImmutableMap.copyOf(BOOKS);
    }

    public static Map<Book, ItemStack> getBookToStack() {
        return ImmutableMap.copyOf(BOOK_TO_STACK);
    }

    public static Map<Book, Multimap<Class<? extends Block>, IInfoRenderer>> getInfoRenderers() {
        return ImmutableMap.copyOf(INFO_RENDERERS);
    }

    public static List<Book> getIndexedBooks() {
        return ImmutableList.copyOf(indexedBooks);
    }
}
