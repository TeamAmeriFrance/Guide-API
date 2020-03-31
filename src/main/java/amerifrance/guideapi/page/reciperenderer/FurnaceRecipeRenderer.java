package amerifrance.guideapi.page.reciperenderer;

import amerifrance.guideapi.api.IRecipeRenderer;
import amerifrance.guideapi.api.SubTexture;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.IngredientCycler;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;


public class FurnaceRecipeRenderer extends IRecipeRenderer.RecipeRendererBase<FurnaceRecipe> {
    public FurnaceRecipeRenderer(FurnaceRecipe recipe) {
        super(recipe);
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj, IngredientCycler cycler) {
        SubTexture.FURNACE_GRID.draw(guiLeft + 64, guiTop + 71);

        guiBase.drawCenteredString(fontRendererObj, TextHelper.localizeEffect("guideapi.text.furnace.smelting"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int x = guiLeft + 66;
        int y = guiTop + 77;

        Ingredient input = recipe.getIngredients().get(0);
        cycler.getCycledIngredientStack(input,0).ifPresent(stack -> {
            GuiHelper.drawItemStack(stack, x, y);

            List<ITextComponent> tooltip = null;
            if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
                tooltips = GuiHelper.getTooltip(stack);
        });



        ItemStack output = recipe.getRecipeOutput();

        int x2 = guiLeft + 109;
        GuiHelper.drawItemStack(output, x2, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x2, y, 15, 15))
            tooltips = GuiHelper.getTooltip(output);

    }
}
