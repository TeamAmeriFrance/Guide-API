package amerifrance.guideapi.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import amerifrance.guideapi.util.GuiHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PageLocItemStack extends PageLocText {

    public ItemStack stack;

    public PageLocItemStack(String locText, ItemStack stack) {
        super(locText);
        this.stack = stack;
    }

    public PageLocItemStack(String locText, Item item) {
        super(locText);
        this.stack = new ItemStack(item);
    }

    public PageLocItemStack(String locText, Block block) {
        super(locText);
        this.stack = new ItemStack(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        GuiHelper.drawScaledItemStack(stack, guiLeft - 20, guiTop + guiBase.ySize / 3, 3);
        GuiHelper.drawScaledItemStack(stack, guiLeft + 5 * guiBase.xSize / 6, guiTop + guiBase.ySize / 3, 3);
    }
}
