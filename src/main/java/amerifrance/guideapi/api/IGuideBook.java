package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
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
     * @return The resource location of your own model or null if you want handle rendering yourself somehow
     */
    @Nullable
    @OnlyIn(Dist.CLIENT)
    default ResourceLocation getModel(){
        return new ResourceLocation("guideapi","guidebook");
    }

    /**
     * Called during Post Initialization.
     */
    default void handlePost(@Nonnull ItemStack bookStack) {
        // No-op
    }
}
