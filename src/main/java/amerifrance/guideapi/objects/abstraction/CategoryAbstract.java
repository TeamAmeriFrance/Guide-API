package amerifrance.guideapi.objects.abstraction;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CategoryAbstract {

    public List<EntryAbstract> entryList = new ArrayList<EntryAbstract>();
    public String localizedCategoryName;

    public CategoryAbstract(List<EntryAbstract> entryList, String localizedCategoryName) {
        this.entryList = entryList;
        this.localizedCategoryName = localizedCategoryName;
    }

    public void addEntry(EntryAbstract entry) {
        this.entryList.add(entry);
    }

    public void removeEntry(EntryAbstract entry) {
        this.entryList.remove(entry);
    }

    public void addEntryList(List<EntryAbstract> entries) {
        this.entryList.addAll(entries);
    }

    public void removeEntryList(List<EntryAbstract> entries) {
        this.entryList.removeAll(entries);
    }

    public List<String> getTooltip() {
        List<String> list = new ArrayList<String>();
        list.add(localizedCategoryName);
        return list;
    }

    @SideOnly(Side.CLIENT)
    public abstract void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem);

    @SideOnly(Side.CLIENT)
    public abstract void drawExtras(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem);

    public abstract boolean canSee(EntityPlayer player, ItemStack bookStack);

    public abstract void onLeftClicked(Book book, int mouseX, int mouseY, EntityPlayer player, ItemStack bookStack);

    public abstract void onRightClicked(Book book, int mouseX, int mouseY, EntityPlayer player, ItemStack bookStack);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryAbstract that = (CategoryAbstract) o;
        if (entryList != null ? !entryList.equals(that.entryList) : that.entryList != null) return false;
        if (localizedCategoryName != null ? !localizedCategoryName.equals(that.localizedCategoryName) : that.localizedCategoryName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = entryList != null ? entryList.hashCode() : 0;
        result = 31 * result + (localizedCategoryName != null ? localizedCategoryName.hashCode() : 0);
        return result;
    }
}
