package de.maxanier.guideapi.page.reciperenderer;

import com.google.common.base.Strings;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.IRecipeRenderer;
import de.maxanier.guideapi.api.SubTexture;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.api.util.IngredientCycler;
import de.maxanier.guideapi.api.util.TextHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public abstract class CraftingRecipeRenderer<T extends IRecipe<?>> extends IRecipeRenderer.RecipeRendererBase<T> {


    private String customDisplay;

    public CraftingRecipeRenderer(T recipe) {
        super(recipe);
    }

    @Override
    public void draw(MatrixStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj, IngredientCycler cycler) {

        SubTexture.CRAFTING_GRID.draw(stack, guiLeft + 68, guiTop + 53);

        String recipeName = Strings.isNullOrEmpty(customDisplay) ? getRecipeName() : customDisplay;
        guiBase.func_238471_a_(stack, fontRendererObj, recipeName, guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int outputX = guiLeft + 148;
        int outputY = guiTop + 73;

        ItemStack itemStack = recipe.getRecipeOutput();

        GuiHelper.drawItemStack(stack, itemStack, outputX, outputY);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15))
            tooltips = GuiHelper.getTooltip(recipe.getRecipeOutput());
    }

//    protected ItemStack getNextItem(ItemStack stack, int position) {
//        NonNullList<ItemStack> subItems = NonNullList.create();
//        stack.getItem().fillItemGroup(ItemGroup.SEARCH, subItems);
//        return subItems.get(getRandomizedCycle(position, subItems.size()));
//    }


    protected String getRecipeName() {
        return TextHelper.localizeEffect("guideapi.text.crafting.shaped");
    }

    public void setCustomTitle(String customDisplay) {
        this.customDisplay = customDisplay;
    }


}
