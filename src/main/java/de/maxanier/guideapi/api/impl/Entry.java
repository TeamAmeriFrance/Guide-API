package de.maxanier.guideapi.api.impl;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.CategoryScreen;
import de.maxanier.guideapi.gui.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class Entry extends EntryAbstract {


    public Entry(List<IPage> pageList, Component name) {
        super(pageList, name);
    }

    public Entry(Component name) {
        super(name);
    }

    @Override
    public boolean canSee(Player player, ItemStack bookStack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(PoseStack stack, Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {

        // Cutting code ripped from GuiButtonExt#drawButton(...)
        FormattedText entryName = getName();
        int strWidth = fontRendererObj.width(entryName);
        int ellipsisWidth = fontRendererObj.width("...");


        //Trim string if to long
        if (strWidth > guiBase.xSize - 80 && strWidth > ellipsisWidth) {
            entryName = fontRendererObj.substrByWidth(entryName, guiBase.xSize - 80 - ellipsisWidth);
            //Append dots
            entryName = FormattedText.composite(entryName, FormattedText.of("..."));
        }

        FormattedCharSequence entryNameRe = Language.getInstance().getVisualOrder(entryName);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, entryWidth, entryHeight)) {
            fontRendererObj.draw(stack, entryNameRe, entryX + 12, entryY + 1, new Color(206, 206, 206).getRGB());
            fontRendererObj.draw(stack, entryNameRe, entryX + 12, entryY, 0x423EBC);
        } else {
            fontRendererObj.draw(stack, entryNameRe, entryX + 12, entryY, 0);
        }


    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(PoseStack stack, Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {


        // Cutting code ripped from GuiButtonExt#drawButton(...)
        int strWidth = fontRendererObj.width(getName());
        boolean cutString = strWidth > guiBase.xSize - 80 && strWidth > fontRendererObj.width("...");

        if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, entryWidth, entryHeight) && cutString) {
            guiBase.renderComponentTooltip(stack, Lists.newArrayList(getName()), entryX, entryY + 12);
        }


    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onInit(Book book, CategoryAbstract category, CategoryScreen guiCategory, Player player, ItemStack bookStack) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onLeftClicked(Book book, CategoryAbstract category, double mouseX, double mouseY, Player player, CategoryScreen guiCategory) {
        Minecraft.getInstance().setScreen(new EntryScreen(book, category, this, player, guiCategory.bookStack));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onRightClicked(Book book, CategoryAbstract category, double mouseX, double mouseY, Player player, CategoryScreen guiCategory) {
    }
}