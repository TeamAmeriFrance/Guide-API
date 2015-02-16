package amerifrance.guideapi.objects.abstraction;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.objects.Book;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntry {

    public List<AbstractPage> pageList = new ArrayList<AbstractPage>();
    public String unlocEntryName;

    public AbstractEntry(List pageList, String unlocEntryName) {
        this.pageList = pageList;
        this.unlocEntryName = unlocEntryName;
    }

    public void addPage(AbstractPage page) {
        this.pageList.add(page);
    }

    public void removePage(AbstractPage page) {
        this.pageList.remove(page);
    }

    public void addPageList(List<AbstractPage> pages) {
        this.pageList.addAll(pages);
    }

    public void removePageList(List<AbstractPage> pages) {
        this.pageList.removeAll(pages);
    }

    public String getLocalizedName() {
        return StatCollector.translateToLocal(unlocEntryName);
    }

    @SideOnly(Side.CLIENT)
    public abstract void draw(Book book, AbstractCategory category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer renderer);

    @SideOnly(Side.CLIENT)
    public abstract void drawExtras(Book book, AbstractCategory category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer renderer);

    public abstract boolean canSee(EntityPlayer player, ItemStack bookStack);

    public abstract void onLeftClicked(Book book, AbstractCategory category, int mouseX, int mouseY, EntityPlayer player, GuiCategory guiCategory);

    public abstract void onRightClicked(Book book, AbstractCategory category, int mouseX, int mouseY, EntityPlayer player, GuiCategory guiCategory);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntry)) return false;

        AbstractEntry that = (AbstractEntry) o;

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

    @Override
    public String toString() {
        return "AbstractEntry{pageList=" + pageList + ", unlocEntryName='" + unlocEntryName + '\'' + '}';
    }
}
