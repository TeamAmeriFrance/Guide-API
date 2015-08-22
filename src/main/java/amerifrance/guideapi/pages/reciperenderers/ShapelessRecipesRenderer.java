package amerifrance.guideapi.pages.reciperenderers;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;

public class ShapelessRecipesRenderer extends BasicRecipeRenderer<ShapelessRecipes> {

    public ShapelessRecipesRenderer(ShapelessRecipes recipe) {
        super(recipe);
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int i = 3 * y + x;
                if (i < recipe.getRecipeSize()) {
                    int stackX = (x + 1) * 17 + (guiLeft + 29);
                    int stackY = (y + 1) * 17 + (guiTop + 40);
                    ItemStack stack = (ItemStack) recipe.recipeItems.get(i);
                    if (stack != null) {
                        GuiHelper.drawItemStack(stack, stackX, stackY);
                        if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                            tooltips = GuiHelper.getTooltip(stack);
                        }
                    }
                }
            }
        }
    }
}
