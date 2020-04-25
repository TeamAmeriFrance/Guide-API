package amerifrance.guideapi.page.reciperenderer;

import amerifrance.guideapi.api.IRecipeRenderer.RecipeRendererBase;
import amerifrance.guideapi.api.SubTexture;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.IngredientCycler;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.BaseScreen;
import com.google.common.base.Strings;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public abstract class CraftingRecipeRenderer<T extends IRecipe<?>> extends RecipeRendererBase<T> {


    private String customDisplay;

    public CraftingRecipeRenderer(T recipe) {
        super(recipe);
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj, IngredientCycler cycler) {

        SubTexture.CRAFTING_GRID.draw(guiLeft + 68, guiTop + 53);

        String recipeName = Strings.isNullOrEmpty(customDisplay) ? getRecipeName() : customDisplay;
        guiBase.drawCenteredString(fontRendererObj, recipeName, guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int outputX = guiLeft + 148;
        int outputY = guiTop + 73;

        ItemStack stack = recipe.getRecipeOutput();

        GuiHelper.drawItemStack(stack, outputX, outputY);
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
