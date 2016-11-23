package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public interface IGuideBook {

    /**
     * Build your guide book here. The returned book will be registered for you. The book created here can be modified
     * later, so make sure to keep a reference for yourself.
     * @return a built book to be registered.
     */
    @Nullable
    Book buildBook();

    /**
     * Use this to handle setting the model of your book. Only exists on the client.
     *
     * @param bookStack - The ItemStack assigned to your book.
     */
    @SideOnly(Side.CLIENT)
    void handleModel(ItemStack bookStack);

    /**
     * Called during Post Initialization. Use this to register recipes and the like.
     */
    void handlePost(ItemStack bookStack);
}
