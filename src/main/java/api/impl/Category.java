package api.impl;

import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.gui.GuiHome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class Category extends CategoryAbstract {

    public Category(Map<ResourceLocation, EntryAbstract> entryList, String name) {
        super(entryList, name);
    }

    public Category(String name) {
        super(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
    }

    @Override
    public boolean canSee(PlayerEntity player, ItemStack bookStack) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onLeftClicked(Book book, int mouseX, int mouseY, PlayerEntity player, ItemStack bookStack) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiCategory(book, this, player, bookStack, null));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRightClicked(Book book, int mouseX, int mouseY, PlayerEntity player, ItemStack bookStack) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onInit(Book book, GuiHome guiHome, PlayerEntity player, ItemStack bookStack) {
    }
}
