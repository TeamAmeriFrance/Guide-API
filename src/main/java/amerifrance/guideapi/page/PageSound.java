package amerifrance.guideapi.page;

import amerifrance.guideapi.GuideMod;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiEntry;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageSound extends Page {

    public IPage pageToEmulate;
    public SoundEvent sound;

    /**
     * @param pageToEmulate - Which page to use as a base
     * @param sound         - Sound to play
     */
    public PageSound(IPage pageToEmulate, SoundEvent sound) {
        this.pageToEmulate = pageToEmulate;
        this.sound = sound;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        pageToEmulate.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        pageToEmulate.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    public boolean canSee(Book book, CategoryAbstract category, EntryAbstract entry, EntityPlayer player, ItemStack bookStack, GuiEntry guiEntry) {
        return pageToEmulate.canSee(book, category, entry, player, bookStack, guiEntry);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onLeftClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, EntityPlayer player, GuiEntry guiEntry) {
        GuideMod.PROXY.playSound(sound);
        pageToEmulate.onLeftClicked(book, category, entry, mouseX, mouseY, player, guiEntry);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRightClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, EntityPlayer player, GuiEntry guiEntry) {
        pageToEmulate.onRightClicked(book, category, entry, mouseX, mouseY, player, guiEntry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageSound)) return false;
        if (!super.equals(o)) return false;

        PageSound pageSound = (PageSound) o;

        if (pageToEmulate != null ? !pageToEmulate.equals(pageSound.pageToEmulate) : pageSound.pageToEmulate != null)
            return false;
        return sound != null ? sound.equals(pageSound.sound) : pageSound.sound == null;
    }

    @Override
    public int hashCode() {
        int result = pageToEmulate != null ? pageToEmulate.hashCode() : 0;
        result = 31 * result + (sound != null ? sound.hashCode() : 0);
        return result;
    }
}
