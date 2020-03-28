package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IGuideBook {

    /**
     * Build your guide book here. The returned book will be registered for you. The book created here can be modified
     * later, so make sure to keep a reference for yourself.
     *
     * @return a built book to be registered.
     */
    @Nullable
    Book buildBook();

    /**
     * Use this to handle setting the model of your book. Only exists on the client.
     *
     * @param bookStack - The ItemStack assigned to your book.
     */
    @OnlyIn(Dist.CLIENT)
    default void handleModel(@Nonnull ItemStack bookStack) {
        GuideAPI.setModel(((IGuideItem) bookStack.getItem()).getBook(bookStack));
    }

    /**
     * Called during Post Initialization.
     */
    default void handlePost(@Nonnull ItemStack bookStack) {
        // No-op
    }
}
