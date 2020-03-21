package amerifrance.guideapi.page.reciperenderer;

import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import api.util.GuiHelper;
import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;

import java.util.Arrays;
import java.util.List;

public class ShapedRecipesRenderer extends BasicRecipeRenderer<ShapedRecipe> {

    public ShapedRecipesRenderer(ShapedRecipe recipe) {
        super(recipe);
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
        for (int y = 0; y < recipe.getRecipeHeight(); y++) {
            for (int x = 0; x < recipe.getRecipeHeight(); x++) {
                int stackX = (x + 1) * 17 + (guiLeft + 27) + x;
                int stackY = (y + 1) * 17 + (guiTop + 38) + y;

                Ingredient ingredient = recipe.getIngredients().get(y * recipe.getRecipeWidth() + x);
                List<ItemStack> list = Arrays.asList(ingredient.getMatchingStacks());
                if (!list.isEmpty()) {
                    ItemStack stack = list.get(getRandomizedCycle(x + (y * 3), list.size()));
                    GuiHelper.drawItemStack(stack, stackX, stackY);
                    if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15))
                        tooltips = GuiHelper.getTooltip(stack);
                }
            }
        }
    }
}
