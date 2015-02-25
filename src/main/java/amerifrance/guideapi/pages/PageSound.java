package amerifrance.guideapi.pages;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.PageBase;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import amerifrance.guideapi.objects.abstraction.IPage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class PageSound extends PageBase {

    public IPage pageToEmulate;
    public String sound;

    /**
     * @param pageToEmulate - Which page to use as a base
     * @param sound         - Sound to play
     */
    public PageSound(IPage pageToEmulate, String sound) {
        this.pageToEmulate = pageToEmulate;
        this.sound = sound;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        pageToEmulate.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        pageToEmulate.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
    }

    @Override
    public boolean canSee(EntityPlayer player, ItemStack bookStack) {
        return pageToEmulate.canSee(player, bookStack);
    }

    @Override
    public void onLeftClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, EntityPlayer player, GuiEntry guiEntry) {
        GuideAPI.proxy.playSound(new ResourceLocation(sound));
        pageToEmulate.onLeftClicked(book, category, entry, mouseX, mouseY, player, guiEntry);
    }

    @Override
    public void onRightClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, EntityPlayer player, GuiEntry guiEntry) {
        pageToEmulate.onRightClicked(book, category, entry, mouseX, mouseY, player, guiEntry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PageSound pageSound = (PageSound) o;
        if (pageToEmulate != null ? !pageToEmulate.equals(pageSound.pageToEmulate) : pageSound.pageToEmulate != null)
            return false;
        if (sound != null ? !sound.equals(pageSound.sound) : pageSound.sound != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = pageToEmulate != null ? pageToEmulate.hashCode() : 0;
        result = 31 * result + (sound != null ? sound.hashCode() : 0);
        return result;
    }
}
