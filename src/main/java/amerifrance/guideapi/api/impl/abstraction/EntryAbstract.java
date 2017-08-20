package amerifrance.guideapi.api.impl.abstraction;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class EntryAbstract {

    public final List<IPage> pageList;
    public final String name;
    public boolean unicode;

    public EntryAbstract(List<IPage> pageList, String name, boolean unicode) {
        this.pageList = pageList;
        this.name = name;
        this.unicode = unicode;
    }

    public EntryAbstract(List<IPage> pageList, String name) {
        this(pageList, name, false);
    }

    public EntryAbstract(String name, boolean unicode) {
        this(Lists.<IPage>newArrayList(), name, unicode);
    }

    public EntryAbstract(String name) {
        this(name, false);
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
        return TextHelper.localizeEffect(name);
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
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pageList != null ? pageList.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
