package de.maxanier.guideapi.api;

import com.google.common.collect.*;
import de.maxanier.guideapi.api.impl.Book;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

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
            INFO_RENDERERS.put(book, ArrayListMultimap.create());

        for (Class<? extends Block> blockClass : blockClasses)
            INFO_RENDERERS.get(book).put(blockClass, infoRenderer);
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
