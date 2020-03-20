package amerifrance.guideapi.entry;

import api.IPage;
import api.impl.Book;
import api.impl.Entry;
import api.impl.abstraction.CategoryAbstract;
import api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntryItemStack extends Entry {

    public ItemStack stack;

    public EntryItemStack(List<IPage> pageList, String name, ItemStack stack, boolean unicode) {
        super(pageList, name, unicode);
        this.stack = stack;
    }

    public EntryItemStack(List<IPage> pageList, String name, ItemStack stack) {
        this(pageList, name, stack, false);
    }

    public EntryItemStack(String name, boolean unicode, ItemStack stack) {
        super(name, unicode);
        this.stack = stack;
    }

    public EntryItemStack(String name, ItemStack stack) {
        super(name);
        this.stack = stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        if (stack != null)
            GuiHelper.drawScaledItemStack(stack, entryX + 2, entryY, 0.5F);

        super.drawExtras(book, category, entryX, entryY, entryWidth, entryHeight, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryItemStack)) return false;
        if (!super.equals(o)) return false;

        EntryItemStack that = (EntryItemStack) o;

        return stack != null ? stack.equals(that.stack) : that.stack == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (stack != null ? stack.hashCode() : 0);
        return result;
    }
}
