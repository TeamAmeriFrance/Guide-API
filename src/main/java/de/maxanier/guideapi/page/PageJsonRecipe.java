package de.maxanier.guideapi.page;

import de.maxanier.guideapi.api.IRecipeRenderer;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.gui.EntryScreen;
import de.maxanier.guideapi.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class PageJsonRecipe extends PageIRecipe {

    @Nonnull
    private final ResourceLocation recipeId;
    @Nonnull
    private final Function<Recipe<?>, IRecipeRenderer> recipeRendererSupplier;

    public PageJsonRecipe(ResourceLocation recipeId) {
        this(recipeId, PageIRecipe::getRenderer);
    }

    public PageJsonRecipe(ResourceLocation recipeId, Function<Recipe<?>, IRecipeRenderer> rendererSupplier) {
        super(null, null);
        this.recipeId = recipeId;
        this.recipeRendererSupplier = rendererSupplier;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onInit(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack, EntryScreen guiEntry) {
        super.onInit(book, category, entry, player, bookStack, guiEntry);
        if (recipe == null) {
            this.recipe = Minecraft.getInstance().getConnection() == null ? null : Minecraft.getInstance().getConnection().getRecipeManager().byKey(recipeId).orElse(null);
            if (recipe == null) {
                LogHelper.error("Cannot find recipe " + recipeId);
            } else {
                if (iRecipeRenderer == null) {
                    iRecipeRenderer = recipeRendererSupplier.apply(recipe);
                    if (iRecipeRenderer == null) {
                        LogHelper.error("Did not get renderer for recipe type " + recipe.getClass().toString() + " for recipe " + recipeId);
                    }
                }
            }
        }
        this.isValid = recipe != null && iRecipeRenderer != null;
    }
}