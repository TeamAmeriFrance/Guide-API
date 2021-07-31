package de.maxanier.guideapi.page;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.IRecipeRenderer;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.Page;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.IngredientCycler;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.EntryScreen;
import de.maxanier.guideapi.page.reciperenderer.FurnaceRecipeRenderer;
import de.maxanier.guideapi.page.reciperenderer.ShapedRecipesRenderer;
import de.maxanier.guideapi.page.reciperenderer.ShapelessRecipesRenderer;
import de.maxanier.guideapi.util.LogHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Objects;


public class PageIRecipe extends Page {

    @Nullable
    public static IRecipeRenderer getRenderer(Recipe<?> recipe) {
        if (recipe == null) {
            LogHelper.error("Cannot get renderer for null recipe.");
            return null;
        } else if (recipe instanceof ShapedRecipe) {
            return new ShapedRecipesRenderer((ShapedRecipe) recipe);
        } else if (recipe instanceof ShapelessRecipe) {
            return new ShapelessRecipesRenderer((ShapelessRecipe) recipe);
        } else if (recipe instanceof SmeltingRecipe) {
            return new FurnaceRecipeRenderer((SmeltingRecipe) recipe);
        } else {
            return null;
        }
    }

    private final IngredientCycler ingredientCycler = new IngredientCycler();
    public Recipe<?> recipe;
    public IRecipeRenderer iRecipeRenderer;
    protected boolean isValid;

    /**
     * Use this if you are creating a page for a standard recipe, one of:
     * <p>
     * <ul>
     * <li>{@link ShapedRecipe}</li>
     * <li>{@link ShapelessRecipe}</li>
     * <li>{@link FurnaceRecipe}</li>
     * </ul>
     *
     * @param recipe - Recipe to draw
     */
    public PageIRecipe(Recipe<?> recipe) {
        this(recipe, getRenderer(recipe));
    }

    /**
     * @param recipe          - Recipe to draw
     * @param iRecipeRenderer - Your custom Recipe drawer
     */
    public PageIRecipe(Recipe<?> recipe, IRecipeRenderer iRecipeRenderer) {
        this.recipe = recipe;
        this.iRecipeRenderer = iRecipeRenderer;
        isValid = recipe != null && iRecipeRenderer != null;
    }

    @Override
    public boolean canSee(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack, EntryScreen guiEntry) {
        return isValid;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {
        if (isValid) {
            super.draw(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
            ingredientCycler.tick(guiBase.getMinecraft());
            iRecipeRenderer.draw(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj, ingredientCycler);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {
        if (isValid) {
            super.drawExtras(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
            iRecipeRenderer.drawExtras(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageIRecipe that)) return false;
        if (!super.equals(o)) return false;

        if (!Objects.equals(recipe, that.recipe)) return false;
        return Objects.equals(iRecipeRenderer, that.iRecipeRenderer);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (recipe != null ? recipe.hashCode() : 0);
        result = 31 * result + (iRecipeRenderer != null ? iRecipeRenderer.hashCode() : 0);
        return result;
    }
}
