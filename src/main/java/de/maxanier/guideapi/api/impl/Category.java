package de.maxanier.guideapi.api.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.CategoryScreen;
import de.maxanier.guideapi.gui.HomeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

public class Category extends CategoryAbstract {

    public Category(Map<ResourceLocation, EntryAbstract> entryList, Component name) {
        super(entryList, name);
    }

    public Category(Component name) {
        super(name);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(PoseStack stack, Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, BaseScreen guiBase, boolean drawOnLeft, ItemRenderer renderItem) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(PoseStack stack, Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, BaseScreen guiBase, boolean drawOnLeft, ItemRenderer renderItem) {
    }

    @Override
    public boolean canSee(Player player, ItemStack bookStack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onLeftClicked(Book book, double mouseX, double mouseY, Player player, ItemStack bookStack) {
        Minecraft.getInstance().setScreen(new CategoryScreen(book, this, player, bookStack, null));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onRightClicked(Book book, double mouseX, double mouseY, Player player, ItemStack bookStack) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onInit(Book book, HomeScreen guiHome, Player player, ItemStack bookStack) {
    }
}
