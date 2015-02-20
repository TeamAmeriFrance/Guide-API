package amerifrance.guideapi.objects.abstraction;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.objects.Book;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class EntryAbstract {

    public List<PageAbstract> pageList = new ArrayList<PageAbstract>();
    public String localizedEntryName;

    public EntryAbstract(List pageList, String localizedEntryName) {
        this.pageList = pageList;
        this.localizedEntryName = localizedEntryName;
    }

    public void addPage(PageAbstract page) {
        this.pageList.add(page);
    }

    public void removePage(PageAbstract page) {
        this.pageList.remove(page);
    }

    public void addPageList(List<PageAbstract> pages) {
        this.pageList.addAll(pages);
    }

    public void removePageList(List<PageAbstract> pages) {
        this.pageList.removeAll(pages);
    }

    @SideOnly(Side.CLIENT)
    public abstract void draw(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer renderer);

    @SideOnly(Side.CLIENT)
    public abstract void drawExtras(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer renderer);

    public abstract boolean canSee(EntityPlayer player, ItemStack bookStack);

    public abstract void onLeftClicked(Book book, CategoryAbstract category, int mouseX, int mouseY, EntityPlayer player, GuiCategory guiCategory);

    public abstract void onRightClicked(Book book, CategoryAbstract category, int mouseX, int mouseY, EntityPlayer player, GuiCategory guiCategory);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntryAbstract that = (EntryAbstract) o;
        if (localizedEntryName != null ? !localizedEntryName.equals(that.localizedEntryName) : that.localizedEntryName != null)
            return false;
        if (pageList != null ? !pageList.equals(that.pageList) : that.pageList != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pageList != null ? pageList.hashCode() : 0;
        result = 31 * result + (localizedEntryName != null ? localizedEntryName.hashCode() : 0);
        return result;
    }
}
