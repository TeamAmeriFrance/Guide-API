package amerifrance.guideapi.page.reciperenderer;

import amerifrance.guideapi.api.IRecipeRenderer.RecipeRendererBase;
import amerifrance.guideapi.api.SubTexture;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import com.google.common.base.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

public class BasicRecipeRenderer<T extends IRecipe> extends RecipeRendererBase<T> {

    private long lastCycle = -1;
    private int cycleIdx = 0;
    private Random rand = new Random();
    private String customDisplay;

    public BasicRecipeRenderer(T recipe) {
        super(recipe);
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        Minecraft mc = Minecraft.getMinecraft();

        long time = mc.world.getTotalWorldTime();
        if (lastCycle < 0 || lastCycle < time - 20) {
            if (lastCycle > 0) {
                cycleIdx++;
                cycleIdx = Math.max(0, cycleIdx);
            }
            lastCycle = mc.world.getTotalWorldTime();
        }

        SubTexture.CRAFTING_GRID.draw(guiLeft + 42, guiTop + 53);

        String recipeName = Strings.isNullOrEmpty(customDisplay) ? getRecipeName() : customDisplay;
        guiBase.drawCenteredString(fontRendererObj, recipeName, guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int outputX = (5 * 18) + (guiLeft + guiBase.xSize / 7) + 5;
        int outputY = (2 * 18) + (guiTop + guiBase.xSize / 5);

        ItemStack stack = recipe.getRecipeOutput();

        if (!stack.isEmpty() && stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
            stack = getNextItem(stack, 0);

        GuiHelper.drawItemStack(stack, outputX, outputY);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15))
            tooltips = GuiHelper.getTooltip(recipe.getRecipeOutput());
    }

    protected ItemStack getNextItem(ItemStack stack, int position) {
        NonNullList<ItemStack> subItems = NonNullList.create();
        Item item = stack.getItem();
        item.getSubItems(item, item.getCreativeTab(), subItems);
        return subItems.get(getRandomizedCycle(position, subItems.size()));
    }

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
