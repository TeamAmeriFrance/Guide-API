package amerifrance.guideapi.objects.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import amerifrance.guideapi.util.GuiHelper;
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
    public void drawExtras(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        //TODO: Find a way to make the icons bigger.
        GuiHelper.drawItemStack(stack, guiLeft, guiTop + guiBase.ySize / 2);
        GuiHelper.drawItemStack(stack, guiLeft + 5 * guiBase.xSize / 6, guiTop + guiBase.ySize / 2);
    }
}
