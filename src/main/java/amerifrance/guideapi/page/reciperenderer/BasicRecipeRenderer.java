package amerifrance.guideapi.page.reciperenderer;

import api.IRecipeRenderer.RecipeRendererBase;
import api.SubTexture;
import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import api.util.GuiHelper;
import api.util.TextHelper;
import amerifrance.guideapi.gui.BaseScreen;
import com.google.common.base.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;

import java.util.Random;

public class BasicRecipeRenderer<T extends IRecipe<?>> extends RecipeRendererBase<T> {

    private long lastCycle = -1;
    private int cycleIdx = 0;
    private Random rand = new Random();
    private String customDisplay;

    public BasicRecipeRenderer(T recipe) {
        super(recipe);
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        Minecraft mc = Minecraft.getInstance();

        long time = mc.world.getGameTime();
        if (lastCycle < 0 || lastCycle < time - 20) {
            if (lastCycle > 0) {
                cycleIdx++;
                cycleIdx = Math.max(0, cycleIdx);
            }
            lastCycle = mc.world.getGameTime();
        }

        SubTexture.CRAFTING_GRID.draw(guiLeft + 42, guiTop + 53);

        String recipeName = Strings.isNullOrEmpty(customDisplay) ? getRecipeName() : customDisplay;
        guiBase.drawCenteredString(fontRendererObj, recipeName, guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int outputX = (5 * 18) + (guiLeft + guiBase.xSize / 7) + 5;
        int outputY = (2 * 18) + (guiTop + guiBase.xSize / 5);

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

    protected int getRandomizedCycle(int index, int max) {
        rand.setSeed(index);
        return (index + rand.nextInt(max) + cycleIdx) % max;
    }

    protected String getRecipeName() {
        return TextHelper.localizeEffect("text.shaped.crafting");
    }

    public void setCustomTitle(String customDisplay) {
        this.customDisplay = customDisplay;
    }
}
