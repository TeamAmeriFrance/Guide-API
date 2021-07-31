package de.maxanier.guideapi.page;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.Page;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.EntryScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

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
    public boolean canSee(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack, EntryScreen guiEntry) {
        return pageToEmulate.canSee(book, category, entry, player, bookStack, guiEntry);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {
        pageToEmulate.draw(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {
        pageToEmulate.drawExtras(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageSound pageSound)) return false;
        if (!super.equals(o)) return false;

        if (!Objects.equals(pageToEmulate, pageSound.pageToEmulate))
            return false;
        return Objects.equals(sound, pageSound.sound);
    }

    @Override
    public int hashCode() {
        int result = pageToEmulate != null ? pageToEmulate.hashCode() : 0;
        result = 31 * result + (sound != null ? sound.hashCode() : 0);
        return result;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onLeftClicked(Book book, CategoryAbstract category, EntryAbstract entry, double mouseX, double mouseY, Player player, EntryScreen guiEntry) {
        GuideMod.PROXY.playSound(sound);
        pageToEmulate.onLeftClicked(book, category, entry, mouseX, mouseY, player, guiEntry);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onRightClicked(Book book, CategoryAbstract category, EntryAbstract entry, double mouseX, double mouseY, Player player, EntryScreen guiEntry) {
        pageToEmulate.onRightClicked(book, category, entry, mouseX, mouseY, player, guiEntry);
    }
}
