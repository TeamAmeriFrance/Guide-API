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

public class PageUnlockItemStack extends PageUnlocText {

    public ItemStack stack;

    public PageUnlockItemStack(String unlocText, ItemStack stack) {
        super(unlocText);
        this.stack = stack;
    }

    public PageUnlockItemStack(String unlocText, Item item) {
        super(unlocText);
        this.stack = new ItemStack(item);
    }

    public PageUnlockItemStack(String unlocText, Block block) {
        super(unlocText);
        this.stack = new ItemStack(block);
    }

    @Override
    public void drawExtras(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        GuiHelper.drawScaledItemStack(stack, guiLeft - 20, guiTop + guiBase.ySize / 3, 3);
        GuiHelper.drawScaledItemStack(stack, guiLeft + 5 * guiBase.xSize / 6, guiTop + guiBase.ySize / 3, 3);
    }
}
