package amerifrance.guideapi.api.abstraction;

import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

public abstract class EntryAbstract {

    public List<IPage> pageList = new ArrayList<IPage>();
    public String unlocEntryName;

    public EntryAbstract(List pageList, String unlocEntryName) {
        this.pageList = pageList;
        this.unlocEntryName = unlocEntryName;
    }

    public void addPage(IPage page) {
        this.pageList.add(page);
    }

    public void removePage(IPage page) {
        this.pageList.remove(page);
    }

    public void addPageList(List<IPage> pages) {
        this.pageList.addAll(pages);
    }

    public void removePageList(List<IPage> pages) {
        this.pageList.removeAll(pages);
    }

    public String getLocalizedName() {
        return StatCollector.translateToLocal(unlocEntryName);
    }

    @SideOnly(Side.CLIENT)
    public abstract void draw(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer renderer);

    @SideOnly(Side.CLIENT)
    public abstract void drawExtras(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer renderer);

    public abstract boolean canSee(EntityPlayer player, ItemStack bookStack);

    @SideOnly(Side.CLIENT)
    public abstract void onLeftClicked(Book book, CategoryAbstract category, int mouseX, int mouseY, EntityPlayer player, GuiCategory guiCategory);

    @SideOnly(Side.CLIENT)
    public abstract void onRightClicked(Book book, CategoryAbstract category, int mouseX, int mouseY, EntityPlayer player, GuiCategory guiCategory);

    @SideOnly(Side.CLIENT)
    public abstract void onInit(Book book, CategoryAbstract category, GuiCategory guiCategory, EntityPlayer player, ItemStack bookStack);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntryAbstract that = (EntryAbstract) o;
        if (pageList != null ? !pageList.equals(that.pageList) : that.pageList != null) return false;
        if (unlocEntryName != null ? !unlocEntryName.equals(that.unlocEntryName) : that.unlocEntryName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pageList != null ? pageList.hashCode() : 0;
        result = 31 * result + (unlocEntryName != null ? unlocEntryName.hashCode() : 0);
        return result;
    }
}
