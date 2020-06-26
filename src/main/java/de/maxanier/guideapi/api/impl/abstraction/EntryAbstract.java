package de.maxanier.guideapi.api.impl.abstraction;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.CategoryScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class EntryAbstract {

    public final List<IPage> pageList;
    public final String name;

    public EntryAbstract(List<IPage> pageList, String name) {
        this.pageList = pageList;
        this.name = name;
    }

    public EntryAbstract(String name) {
        this(Lists.newArrayList(), name);
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

    @OnlyIn(Dist.CLIENT)
    public abstract void draw(MatrixStack stack, Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer renderer);

    @OnlyIn(Dist.CLIENT)
    public abstract void drawExtras(MatrixStack stack, Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer renderer);

    public TextComponent getName() {
        return new TranslationTextComponent(name);
    }

    public abstract boolean canSee(PlayerEntity player, ItemStack bookStack);

    @OnlyIn(Dist.CLIENT)
    public abstract void onLeftClicked(Book book, CategoryAbstract category, double mouseX, double mouseY, PlayerEntity player, CategoryScreen guiCategory);

    @OnlyIn(Dist.CLIENT)
    public abstract void onRightClicked(Book book, CategoryAbstract category, double mouseX, double mouseY, PlayerEntity player, CategoryScreen guiCategory);

    @OnlyIn(Dist.CLIENT)
    public abstract void onInit(Book book, CategoryAbstract category, CategoryScreen guiCategory, PlayerEntity player, ItemStack bookStack);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntryAbstract that = (EntryAbstract) o;
        if (pageList != null ? !pageList.equals(that.pageList) : that.pageList != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = pageList != null ? pageList.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
