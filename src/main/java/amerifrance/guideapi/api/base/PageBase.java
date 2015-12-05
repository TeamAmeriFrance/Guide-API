package amerifrance.guideapi.api.base;

import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.items.ItemLostPage;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageBase implements IPage {

    protected boolean unicode;

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
    }

    @Override
    public boolean canSee(Book book, CategoryAbstract category, EntryAbstract entry, EntityPlayer player, ItemStack bookStack, GuiEntry guiEntry) {
        return ((bookStack.hasTagCompound() && bookStack.getTagCompound().getBoolean("CreativeBook"))) || ItemLostPage.bookHasPage(bookStack, GuideRegistry.getIndexOf(book), book.getCategoryList().indexOf(category), category.entryList.indexOf(entry), guiEntry.pageNumber) || !book.isLostBook();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onLeftClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, EntityPlayer player, GuiEntry guiEntry) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRightClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, EntityPlayer player, GuiEntry guiEntry) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onInit(Book book, CategoryAbstract category, EntryAbstract entry, EntityPlayer player, ItemStack bookStack, GuiEntry guiEntry) {
    }

    @SideOnly(Side.CLIENT)
    public void setUnicodeFlag(boolean flag) {
        this.unicode = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        return true;
    }
}
