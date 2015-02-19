package amerifrance.guideapi.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.PageBase;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import amerifrance.guideapi.util.GuiHelper;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class PageFurnaceRecipe extends PageBase {

    public ItemStack input;
    public ItemStack output;

    /**
     *
     * @param input - Input ItemStack to draw smelting result of
     */
    public PageFurnaceRecipe(ItemStack input) {
        this.input = input;
        this.output = FurnaceRecipes.smelting().getSmeltingResult(input);
    }

    /**
     *
     * @param input - Input Item to draw smelting result of
     */
    public PageFurnaceRecipe(Item input) {
        this.input = new ItemStack(input);
        this.output = FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(input));
    }

    /**
     *
     * @param input - Input Block to draw smelting result of
     */
    public PageFurnaceRecipe(Block input) {
        this.input = new ItemStack(input);
        this.output = FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(input));
    }

    /**
     *
     * @param input - Input OreDict entry to draw smelting result of
     */
    public PageFurnaceRecipe(String input) {

        this.input = new ItemStack(Blocks.fire);

        if (!OreDictionary.getOres(input).isEmpty())
            for (int i = 0; i < OreDictionary.getOres(input).size(); i++) {
                ItemStack stack = OreDictionary.getOres(input).get(i);

                this.input = stack;
                this.output = FurnaceRecipes.smelting().getSmeltingResult(stack);
            }
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        guiBase.drawCenteredString(fontRenderer, StatCollector.translateToLocal("text.furnace.smelting"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int inputX = (1 + 1) * 20 + (guiLeft + guiBase.xSize / 7);
        int inputY = (1 + 1) * 20 + (guiTop + guiBase.ySize / 5);
        GuiHelper.drawItemStack(input, inputX, inputY);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, inputX, inputY, 15, 15)) {
            guiBase.renderToolTip(input, mouseX, mouseY);
        }

        if (output == null) {
            output = new ItemStack(Blocks.fire);
        }
        int outputX = (5 * 20) + (guiLeft + guiBase.xSize / 7);
        int outputY = (2 * 20) + (guiTop + guiBase.xSize / 5);
        GuiHelper.drawItemStack(output, outputX, outputY);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15)) {
            guiBase.renderToolTip(output, outputX, outputY);
        }
        if (output.getItem() == Item.getItemFromBlock(Blocks.fire)) {
            guiBase.drawCenteredString(fontRenderer, StatCollector.translateToLocal("text.furnace.error"), guiLeft + guiBase.xSize / 2, guiTop + 4 * guiBase.ySize / 6, 0xED073D);
        }
    }
}
