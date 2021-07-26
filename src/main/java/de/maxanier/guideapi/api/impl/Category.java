package de.maxanier.guideapi.api.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.CategoryScreen;
import de.maxanier.guideapi.gui.HomeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

public class Category extends CategoryAbstract {

    public Category(Map<ResourceLocation, EntryAbstract> entryList, ITextComponent name) {
        super(entryList, name);
    }

    public Category(ITextComponent name) {
        super(name);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(MatrixStack stack, Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, BaseScreen guiBase, boolean drawOnLeft, ItemRenderer renderItem) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(MatrixStack stack, Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, BaseScreen guiBase, boolean drawOnLeft, ItemRenderer renderItem) {
    }

    @Override
    public boolean canSee(PlayerEntity player, ItemStack bookStack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onLeftClicked(Book book, double mouseX, double mouseY, PlayerEntity player, ItemStack bookStack) {
        Minecraft.getInstance().setScreen(new CategoryScreen(book, this, player, bookStack, null));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onRightClicked(Book book, double mouseX, double mouseY, PlayerEntity player, ItemStack bookStack) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onInit(Book book, HomeScreen guiHome, PlayerEntity player, ItemStack bookStack) {
    }
}
