package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

/**
 * Base class for all {@link Book} related events.
 *
 * {@link #book} is the book being opened.
 * {@link #stack} is the ItemStack of the Book.
 * {@link #player} is the player opening the book.
 */
public class BookEvent extends Event {

    private final Book book;
    private final ItemStack stack;
    private final EntityPlayer player;

    protected BookEvent(Book book, ItemStack stack, EntityPlayer player) {
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

    public EntityPlayer getPlayer() {
        return player;
    }

    /**
     * Called whenever a book is opened.
     *
     * {@link #canceledText} is a status message sent to the player when the book fails to open.
     */
    @Cancelable
    public static class Open extends BookEvent {

        private static final ITextComponent DEFAULT_CANCEL = new TextComponentTranslation("text.open.failed").setStyle(new Style().setColor(TextFormatting.RED));

        private ITextComponent canceledText = DEFAULT_CANCEL;

        public Open(Book book, ItemStack stack, EntityPlayer player) {
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
