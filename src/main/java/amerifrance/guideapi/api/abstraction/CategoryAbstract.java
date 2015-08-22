package amerifrance.guideapi.api.abstraction;

import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiHome;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public abstract class CategoryAbstract {

    public List<EntryAbstract> entryList = new ArrayList<EntryAbstract>();
    public String unlocCategoryName;

    public CategoryAbstract(List<EntryAbstract> entryList, String unlocCategoryName) {
        this.entryList = entryList;
        this.unlocCategoryName = unlocCategoryName;
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

    public String getLocalizedName() {
        return StatCollector.translateToLocal(unlocCategoryName);
    }

    public List<String> getTooltip() {
        List<String> list = new ArrayList<String>();
        list.add(getLocalizedName());
        return list;
    }

    @SideOnly(Side.CLIENT)
    public abstract void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem);

    @SideOnly(Side.CLIENT)
    public abstract void drawExtras(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem);

    public abstract boolean canSee(EntityPlayer player, ItemStack bookStack);

    @SideOnly(Side.CLIENT)
    public abstract void onLeftClicked(Book book, int mouseX, int mouseY, EntityPlayer player, ItemStack bookStack);

    @SideOnly(Side.CLIENT)
    public abstract void onRightClicked(Book book, int mouseX, int mouseY, EntityPlayer player, ItemStack bookStack);

    @SideOnly(Side.CLIENT)
    public abstract void onInit(Book book, GuiHome guiHome, EntityPlayer player, ItemStack bookStack);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryAbstract that = (CategoryAbstract) o;
        if (entryList != null ? !entryList.equals(that.entryList) : that.entryList != null) return false;
        if (unlocCategoryName != null ? !unlocCategoryName.equals(that.unlocCategoryName) : that.unlocCategoryName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = entryList != null ? entryList.hashCode() : 0;
        result = 31 * result + (unlocCategoryName != null ? unlocCategoryName.hashCode() : 0);
        return result;
    }
}
