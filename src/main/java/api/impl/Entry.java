package api.impl;

import api.IPage;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import api.util.GuiHelper;
import amerifrance.guideapi.gui.BaseScreen;
import amerifrance.guideapi.gui.CategoryScreen;
import amerifrance.guideapi.gui.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class Entry extends EntryAbstract {

    public Entry(List<IPage> pageList, String name, boolean unicode) {
        super(pageList, name, unicode);
    }

    public Entry(List<IPage> pageList, String name) {
        super(pageList, name, false);
    }

    public Entry(String name, boolean unicode) {
        super(name, unicode);
    }

    public Entry(String name) {
        super(name);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {

        boolean startFlag = fontRendererObj.getUnicodeFlag();

        if (unicode)
            fontRendererObj.setUnicodeFlag(true);

        // Cutting code ripped from GuiButtonExt#drawButton(...)
        String entryName = getLocalizedName();
        int strWidth = fontRendererObj.getStringWidth(entryName);
        int ellipsisWidth = fontRendererObj.getStringWidth("...");

        if (strWidth > guiBase.xSize - 80 && strWidth > ellipsisWidth)
            entryName = fontRendererObj.trimStringToWidth(entryName, guiBase.xSize - 80 - ellipsisWidth).trim() + "...";

        if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, entryWidth, entryHeight)) {
            fontRendererObj.drawString(entryName, entryX + 12, entryY + 1, new Color(206, 206, 206).getRGB());
            fontRendererObj.drawString(entryName, entryX + 12, entryY, 0x423EBC);
        } else {
            fontRendererObj.drawString(entryName, entryX + 12, entryY, 0);
        }

        if (unicode && !startFlag)
            fontRendererObj.setUnicodeFlag(false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        boolean startFlag = fontRendererObj.getUnicodeFlag();
        fontRendererObj.setUnicodeFlag(false);

        // Cutting code ripped from GuiButtonExt#drawButton(...)
        int strWidth = fontRendererObj.getStringWidth(getLocalizedName());
        boolean cutString = false;

        if (strWidth > guiBase.xSize - 80 && strWidth > fontRendererObj.getStringWidth("..."))
            cutString = true;

        if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, entryWidth, entryHeight) && cutString) {

            guiBase.drawHoveringText(Collections.singletonList(getLocalizedName()), entryX, entryY + 12);
            fontRendererObj.setUnicodeFlag(unicode);
        }

        fontRendererObj.setUnicodeFlag(startFlag);
    }

    @Override
    public boolean canSee(PlayerEntity player, ItemStack bookStack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onLeftClicked(Book book, CategoryAbstract category, int mouseX, int mouseY, PlayerEntity player, CategoryScreen guiCategory) {
        Minecraft.getInstance().displayGuiScreen(new EntryScreen(book, category, this, player, guiCategory.bookStack));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onRightClicked(Book book, CategoryAbstract category, int mouseX, int mouseY, PlayerEntity player, CategoryScreen guiCategory) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onInit(Book book, CategoryAbstract category, CategoryScreen guiCategory, PlayerEntity player, ItemStack bookStack) {
    }
}