package de.maxanier.guideapi.page.reciperenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.IRecipeRenderer;
import de.maxanier.guideapi.api.SubTexture;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.api.util.IngredientCycler;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;


public class FurnaceRecipeRenderer extends IRecipeRenderer.RecipeRendererBase<FurnaceRecipe> {
    private final ITextComponent title = new TranslationTextComponent("guideapi.text.furnace.smelting");
    public FurnaceRecipeRenderer(FurnaceRecipe recipe) {
        super(recipe);
    }

    @Override
    public void draw(MatrixStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj, IngredientCycler cycler) {
        SubTexture.FURNACE_GRID.draw(stack, guiLeft + 90, guiTop + 71);

        guiBase.drawCenteredStringWithoutShadow(stack, fontRendererObj, title, guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int x = guiLeft + 92;
        int y = guiTop + 77;

        Ingredient input = recipe.getIngredients().get(0);
        cycler.getCycledIngredientStack(input, 0).ifPresent(s -> {
            GuiHelper.drawItemStack(stack, s, x, y);

            List<ITextComponent> tooltip = null;
            if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
                tooltips = GuiHelper.getTooltip(s);
        });


        ItemStack output = recipe.getResultItem();

        int x2 = guiLeft + 135;
        GuiHelper.drawItemStack(stack, output, x2, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x2, y, 15, 15))
            tooltips = GuiHelper.getTooltip(output);

    }
}
