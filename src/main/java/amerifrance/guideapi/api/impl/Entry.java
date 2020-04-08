package amerifrance.guideapi.api.impl;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.BaseScreen;
import amerifrance.guideapi.gui.CategoryScreen;
import amerifrance.guideapi.gui.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
    public void draw(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {


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


    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {


        // Cutting code ripped from GuiButtonExt#drawButton(...)
        int strWidth = fontRendererObj.getStringWidth(getLocalizedName());
        boolean cutString = false;

        if (strWidth > guiBase.xSize - 80 && strWidth > fontRendererObj.getStringWidth("..."))
            cutString = true;

        if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, entryWidth, entryHeight) && cutString) {
            guiBase.renderTooltip(getLocalizedName(), entryX, entryY + 12);
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