package amerifrance.guideapi.page.reciperenderer;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.IngredientCycler;
import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;

public class ShapedRecipesRenderer extends CraftingRecipeRenderer<ShapedRecipe> {

    public ShapedRecipesRenderer(ShapedRecipe recipe) {
        super(recipe);
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj, IngredientCycler cycler) {
        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj, cycler);
        for (int y = 0; y < recipe.getRecipeHeight(); y++) {
            for (int x = 0; x < recipe.getRecipeWidth(); x++) {
                int i = y * recipe.getRecipeWidth() + x;
                int stackX = (x + 1) * 17 + (guiLeft + 53) + x;
                int stackY = (y + 1) * 17 + (guiTop + 38) + y;

                Ingredient ingredient = recipe.getIngredients().get(i);
                cycler.getCycledIngredientStack(ingredient, i).ifPresent(stack -> {
                    GuiHelper.drawItemStack(stack, stackX, stackY);
                    if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15))
                        tooltips = GuiHelper.getTooltip(stack);
                });
            }
        }
    }
}
