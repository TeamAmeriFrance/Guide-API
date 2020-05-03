package de.maxanier.guideapi.util;

import com.google.common.base.Throwables;
import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.impl.Book;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModLoadingContext;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * This class is for internal use <b>only</b>. Do not use this from outside.
 */
@SuppressWarnings("unchecked")
public class APISetter {

    public static void registerBook(Book book) {
        try {
            sanityCheck();
        } catch (IllegalAccessException e) {
            Throwables.propagate(e);
            return;
        }

        try {
            Field books = GuideAPI.class.getDeclaredField("BOOKS");
            books.setAccessible(true);
            Map<ResourceLocation, Book> BOOKS = (Map<ResourceLocation, Book>) books.get(null);
            BOOKS.put(book.getRegistryName(), book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setBookForStack(Book book, ItemStack stack) {
        try {
            sanityCheck();
        } catch (IllegalAccessException e) {
            Throwables.propagate(e);
            return;
        }

        try {
            Field stacks = GuideAPI.class.getDeclaredField("BOOK_TO_STACK");
            stacks.setAccessible(true);
            Map<Book, ItemStack> BOOK_TO_STACK = (Map<Book, ItemStack>) stacks.get(null);
            BOOK_TO_STACK.put(book, stack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setIndexedBooks(List<Book> books) {
        try {
            sanityCheck();
        } catch (IllegalAccessException e) {
            Throwables.propagate(e);
            return;
        }

        try {
            Field indexedBooks = GuideAPI.class.getDeclaredField("indexedBooks");
            indexedBooks.setAccessible(true);
            indexedBooks.set(null, books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sanityCheck() throws IllegalAccessException {
        String activeMod = ModLoadingContext.get().getActiveNamespace();
        if (!GuideMod.ID.equals(activeMod))
            throw new IllegalAccessException("Mod " + activeMod + " tried to access an internal-only method in GuideAPI. Please report this.");
    }
}
