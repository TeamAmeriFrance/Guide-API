package amerifrance.guideapi.api.impl;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.gui.GuiEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {

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
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
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
    @SideOnly(Side.CLIENT)
    public void onLeftClicked(Book book, CategoryAbstract category, int mouseX, int mouseY, PlayerEntity player, GuiCategory guiCategory) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiEntry(book, category, this, player, guiCategory.bookStack));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRightClicked(Book book, CategoryAbstract category, int mouseX, int mouseY, PlayerEntity player, GuiCategory guiCategory) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onInit(Book book, CategoryAbstract category, GuiCategory guiCategory, PlayerEntity player, ItemStack bookStack) {
    }
}