package amerifrance.guideapi.page;

import api.SubTexture;
import api.impl.Book;
import api.impl.Page;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import api.util.GuiHelper;
import api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class PageFurnaceRecipe extends Page {

    public ItemStack input;
    public ItemStack output;

    /**
     * @param input - Input ItemStack to draw smelting result of
     */
    public PageFurnaceRecipe(ItemStack input) {
        this.input = input;
        this.output = FurnaceRecipes.instance().getSmeltingResult(input);
    }

    /**
     * @param input - Input Item to draw smelting result of
     */
    public PageFurnaceRecipe(Item input) {
        this.input = new ItemStack(input);
        this.output = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(input));
    }

    /**
     * @param input - Input Block to draw smelting result of
     */
    public PageFurnaceRecipe(Block input) {
        this.input = new ItemStack(input);
        this.output = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(input));
    }

    /**
     * @param input - Input OreDict entry to draw smelting result of
     */
    public PageFurnaceRecipe(String input) {

        this.input = new ItemStack(Blocks.FIRE);

        if (!OreDictionary.getOres(input).isEmpty())
            for (int i = 0; i < OreDictionary.getOres(input).size(); i++) {
                ItemStack stack = OreDictionary.getOres(input).get(i);

                this.input = stack;
                this.output = FurnaceRecipes.instance().getSmeltingResult(stack);
            }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        SubTexture.FURNACE_GRID.draw(guiLeft + 64, guiTop + 71);

        List badTip = new ArrayList();
        badTip.add(TextHelper.localizeEffect("text.furnace.error"));

        guiBase.drawCenteredString(fontRendererObj, TextHelper.localizeEffect("text.furnace.smelting"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int x = guiLeft + 66;
        int y = guiTop + 77;
        GuiHelper.drawItemStack(input, x, y);

        List<String> tooltip = null;
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
            tooltip = GuiHelper.getTooltip(input);

        if (output.isEmpty())
            output = new ItemStack(Blocks.BARRIER);

        x = guiLeft + 109;
        GuiHelper.drawItemStack(output, x, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
            tooltip = output.getItem() == Item.getItemFromBlock(Blocks.BARRIER) ? badTip : GuiHelper.getTooltip(output);

        if (output.getItem() == Item.getItemFromBlock(Blocks.BARRIER))
            guiBase.drawCenteredString(fontRendererObj, TextHelper.localizeEffect("text.furnace.error"), guiLeft + guiBase.xSize / 2, guiTop + 4 * guiBase.ySize / 6, 0xED073D);

        if (tooltip != null)
            guiBase.drawHoveringText(tooltip, mouseX, mouseY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageFurnaceRecipe)) return false;
        if (!super.equals(o)) return false;

        PageFurnaceRecipe that = (PageFurnaceRecipe) o;

        if (input != null ? !input.equals(that.input) : that.input != null) return false;
        return output != null ? output.equals(that.output) : that.output == null;
    }

    @Override
    public int hashCode() {
        int result = input != null ? input.hashCode() : 0;
        result = 31 * result + (output != null ? output.hashCode() : 0);
        return result;
    }
}
