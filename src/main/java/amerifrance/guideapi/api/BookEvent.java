package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
    private final PlayerEntity player;

    protected BookEvent(Book book, ItemStack stack, PlayerEntity player) {
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

    public PlayerEntity getPlayer() {
        return player;
    }

    /**
     * Called client side whenever a book is opened.
     * <p>
     * {@link #canceledText} is a status message sent to the player when the book fails to open.
     */
    @Cancelable
    public static class Open extends BookEvent {

        private static final ITextComponent DEFAULT_CANCEL = new TranslationTextComponent("text.open.failed").setStyle(new Style().setColor(TextFormatting.RED));

        private ITextComponent canceledText = DEFAULT_CANCEL;

        public Open(Book book, ItemStack stack, PlayerEntity player) {
            super(book, stack, player);
        }

        @Nonnull
        public ITextComponent getCanceledText() {
            return canceledText;
        }

        public void setCanceledText(@Nonnull ITextComponent canceledText) {
            this.canceledText = canceledText;
        }
    }
}
