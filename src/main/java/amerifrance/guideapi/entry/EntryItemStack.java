package amerifrance.guideapi.entry;

import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import lombok.EqualsAndHashCode;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class EntryItemStack extends Entry {

    public ItemStack stack;

    public EntryItemStack(List<IPage> pageList, String unlocEntryName, ItemStack stack, boolean unicode) {
        super(pageList, unlocEntryName, unicode);

        this.stack = stack;
    }

    public EntryItemStack(List<IPage> pageList, String unlocEntryName, ItemStack stack) {
        this(pageList, unlocEntryName, stack, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        if (stack != null)
            GuiHelper.drawScaledItemStack(stack, entryX + 2, entryY, 0.5F);
    }
}
