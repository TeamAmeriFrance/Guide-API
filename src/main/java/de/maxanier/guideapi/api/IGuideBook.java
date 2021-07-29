package de.maxanier.guideapi.api;

import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.impl.Book;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IGuideBook {

    /**
     * Build your guide book here. The returned book will be registered for you. The book created here can be modified
     * later, so make sure to keep a reference for yourself.
     * This is called during the Register<Item> event, so don't do anything here except binding your book.
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
    default ResourceLocation getModel() {
        return new ResourceLocation(GuideMod.ID, "guidebook");
    }

    /**
     * Called during Post Initialization.
     */
    default void handlePost(@Nonnull ItemStack bookStack) {
        // No-op
    }

    /**
     * If you want to register {@link IInfoRenderer} to {@link GuideAPI}, do it in here.
     */
    default void registerInfoRenderer(Book yourBook) {

    }
}
