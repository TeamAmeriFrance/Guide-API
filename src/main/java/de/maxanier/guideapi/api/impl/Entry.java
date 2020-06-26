package de.maxanier.guideapi.api.impl;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.CategoryScreen;
import de.maxanier.guideapi.gui.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class Entry extends EntryAbstract {


    public Entry(List<IPage> pageList, String name) {
        super(pageList, name);
    }

    public Entry(String name) {
        super(name);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(MatrixStack stack, Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {


        // Cutting code ripped from GuiButtonExt#drawButton(...)
        ITextProperties entryName = getName();
        int strWidth = fontRendererObj.func_238414_a_(entryName);
        int ellipsisWidth = fontRendererObj.getStringWidth("...");


        //Trim string if to long
        if (strWidth > guiBase.xSize - 80 && strWidth > ellipsisWidth)
            entryName = fontRendererObj.func_238417_a_(entryName, guiBase.xSize - 80 - ellipsisWidth);


        //Append dots
        entryName = ITextProperties.func_240655_a_(entryName, ITextProperties.func_240652_a_("..."));

        if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, entryWidth, entryHeight)) {
            fontRendererObj.func_238422_b_(stack, entryName, entryX + 12, entryY + 1, new Color(206, 206, 206).getRGB());
            fontRendererObj.func_238422_b_(stack, entryName, entryX + 12, entryY, 0x423EBC);
        } else {
            fontRendererObj.func_238422_b_(stack, entryName, entryX + 12, entryY, 0);
        }


    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(MatrixStack stack, Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {


        // Cutting code ripped from GuiButtonExt#drawButton(...)
        int strWidth = fontRendererObj.func_238414_a_(getName());
        boolean cutString = false;

        if (strWidth > guiBase.xSize - 80 && strWidth > fontRendererObj.getStringWidth("..."))
            cutString = true;

        if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, entryWidth, entryHeight) && cutString) {
            guiBase.func_238654_b_(stack, Lists.newArrayList(getName()), entryX, entryY + 12);
        }


    }

    @Override
    public boolean canSee(PlayerEntity player, ItemStack bookStack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onLeftClicked(Book book, CategoryAbstract category, double mouseX, double mouseY, PlayerEntity player, CategoryScreen guiCategory) {
        Minecraft.getInstance().displayGuiScreen(new EntryScreen(book, category, this, player, guiCategory.bookStack));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onRightClicked(Book book, CategoryAbstract category, double mouseX, double mouseY, PlayerEntity player, CategoryScreen guiCategory) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onInit(Book book, CategoryAbstract category, CategoryScreen guiCategory, PlayerEntity player, ItemStack bookStack) {
    }
}