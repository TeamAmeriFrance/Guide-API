package de.maxanier.guideapi.page.reciperenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.IRecipeRenderer;
import de.maxanier.guideapi.api.SubTexture;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.api.util.IngredientCycler;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.network.chat.Component;

public abstract class CraftingRecipeRenderer<T extends Recipe<?>> extends IRecipeRenderer.RecipeRendererBase<T> {


    private final Component title;
    private Component customDisplay;

    public CraftingRecipeRenderer(T recipe, Component title) {
        super(recipe);
        this.title = title;
    }

    @Override
    public void draw(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj, IngredientCycler cycler) {

        SubTexture.CRAFTING_GRID.draw(stack, guiLeft + 68, guiTop + 53);

        Component recipeName = customDisplay == null ? title : customDisplay;
        guiBase.drawCenteredStringWithoutShadow(stack, fontRendererObj, recipeName, guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int outputX = guiLeft + 148;
        int outputY = guiTop + 73;

        ItemStack itemStack = recipe.getResultItem();

        GuiHelper.drawItemStack(stack, itemStack, outputX, outputY);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15))
            tooltips = GuiHelper.getTooltip(recipe.getResultItem());
    }

//    protected ItemStack getNextItem(ItemStack stack, int position) {
//        NonNullList<ItemStack> subItems = NonNullList.create();
//        stack.getItem().fillItemGroup(ItemGroup.SEARCH, subItems);
//        return subItems.get(getRandomizedCycle(position, subItems.size()));
//    }

    public void setCustomTitle(Component customDisplay) {
        this.customDisplay = customDisplay;
    }


}
