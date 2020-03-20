package api.impl;

import api.IPage;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiEntry;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class Page implements IPage {

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
    public boolean canSee(Book book, CategoryAbstract category, EntryAbstract entry, PlayerEntity player, ItemStack bookStack, GuiEntry guiEntry) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onLeftClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, PlayerEntity player, GuiEntry guiEntry) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRightClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, PlayerEntity player, GuiEntry guiEntry) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onInit(Book book, CategoryAbstract category, EntryAbstract entry, PlayerEntity player, ItemStack bookStack, GuiEntry guiEntry) {
    }

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
