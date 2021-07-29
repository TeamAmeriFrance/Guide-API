package de.maxanier.guideapi.api;

import de.maxanier.guideapi.api.impl.Book;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

/**
 * Base class for all {@link Book} related events.
 * <p>
 * {@link #book} is the book being opened.
 * {@link #stack} is the ItemStack of the Book.
 * {@link #player} is the player opening the book.
 */
public class BookEvent extends Event {

    private final Book book;
    private final ItemStack stack;
    private final Player player;

    protected BookEvent(Book book, ItemStack stack, Player player) {
        this.book = book;
        this.stack = stack;
        this.player = player;
    }

    public Book getBook() {
        return book;
    }

    public ItemStack getStack() {
        return stack;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Called client side whenever a book is opened.
     * <p>
     * {@link #canceledText} is a status message sent to the player when the book fails to open.
     */
    @Cancelable
    public static class Open extends BookEvent {

        private static final Component DEFAULT_CANCEL = new TranslatableComponent("text.open.failed").withStyle(ChatFormatting.RED);

        private Component canceledText = DEFAULT_CANCEL;

        public Open(Book book, ItemStack stack, Player player) {
            super(book, stack, player);
        }

        @Nonnull
        public Component getCanceledText() {
            return canceledText;
        }

        public void setCanceledText(@Nonnull Component canceledText) {
            this.canceledText = canceledText;
        }
    }
}
