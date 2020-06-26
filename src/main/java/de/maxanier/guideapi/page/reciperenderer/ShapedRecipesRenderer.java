package de.maxanier.guideapi.page.reciperenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.api.util.IngredientCycler;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;

public class ShapedRecipesRenderer extends CraftingRecipeRenderer<ShapedRecipe> {

    public ShapedRecipesRenderer(ShapedRecipe recipe) {
        super(recipe);
    }

    @Override
    public void draw(MatrixStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj, IngredientCycler cycler) {
        super.draw(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj, cycler);
        for (int y = 0; y < recipe.getRecipeHeight(); y++) {
            for (int x = 0; x < recipe.getRecipeWidth(); x++) {
                int i = y * recipe.getRecipeWidth() + x;
                int stackX = (x + 1) * 17 + (guiLeft + 53) + x;
                int stackY = (y + 1) * 17 + (guiTop + 38) + y;

                Ingredient ingredient = recipe.getIngredients().get(i);
                cycler.getCycledIngredientStack(ingredient, i).ifPresent(s -> {
                    GuiHelper.drawItemStack(stack, s, stackX, stackY);
                    if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15))
                        tooltips = GuiHelper.getTooltip(s);
                });
            }
        }
    }
}
