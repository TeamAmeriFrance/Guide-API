package amerifrance.guideapi.pages;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.PageBase;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageFurnaceRecipe extends PageBase {

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

        this.input = new ItemStack(Blocks.fire);

        if (!OreDictionary.getOres(input).isEmpty())
            for (int i = 0; i < OreDictionary.getOres(input).size(); i++) {
                ItemStack stack = OreDictionary.getOres(input).get(i);

                this.input = stack;
                this.output = FurnaceRecipes.instance().getSmeltingResult(stack);
            }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "recipe_elements.png"));
        guiBase.drawTexturedModalRect(guiLeft + 42, guiTop + 53, 0, 65, 105, 65);

        guiBase.drawCenteredString(fontRenderer, StatCollector.translateToLocal("text.furnace.smelting"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int x = guiLeft + 66;
        int y = guiTop + 77;
        GuiHelper.drawItemStack(input, x, y);
        
        List<String> tooltip = null;
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15)) {
            tooltip = GuiHelper.getTooltip(input);
        }

        if (output == null) {
            output = new ItemStack(Blocks.fire);
        }
        x = guiLeft + 123;
        GuiHelper.drawItemStack(output, x, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15)) {
            tooltip = GuiHelper.getTooltip(output);
        }
        if (output.getItem() == Item.getItemFromBlock(Blocks.fire)) {
            guiBase.drawCenteredString(fontRenderer, StatCollector.translateToLocal("text.furnace.error"), guiLeft + guiBase.xSize / 2, guiTop + 4 * guiBase.ySize / 6, 0xED073D);
        }
        
        if (tooltip != null) {
        	guiBase.drawHoveringText(tooltip, mouseX, mouseY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PageFurnaceRecipe that = (PageFurnaceRecipe) o;
        if (input != null ? !input.isItemEqual(that.input) : that.input != null) return false;
        if (output != null ? !output.isItemEqual(that.output) : that.output != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = input != null ? input.hashCode() : 0;
        result = 31 * result + (output != null ? output.hashCode() : 0);
        return result;
    }
}
