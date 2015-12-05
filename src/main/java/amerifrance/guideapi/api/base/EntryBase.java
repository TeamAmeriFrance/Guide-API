package amerifrance.guideapi.api.base;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.gui.GuiEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.List;

public class EntryBase extends EntryAbstract {

    public EntryBase(List<IPage> pageList, String unlocEntryName, boolean unicode) {
        super(pageList, unlocEntryName, unicode);
    }

    public EntryBase(List<IPage> pageList, String unlocEntryName) {
        super(pageList, unlocEntryName, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {

        boolean startFlag = fontRendererObj.getUnicodeFlag();

        if (unicode)
            fontRendererObj.setUnicodeFlag(true);

        if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, entryWidth, entryHeight)) {
            fontRendererObj.drawString(getLocalizedName(), entryX + 12, entryY + 1, new Color(206, 206, 206).getRGB());
            fontRendererObj.drawString(getLocalizedName(), entryX + 12, entryY, 0x423EBC);
        } else {
            fontRendererObj.drawString(getLocalizedName(), entryX + 12, entryY, 0);
        }

        if (unicode && !startFlag)
            fontRendererObj.setUnicodeFlag(false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
    }

    @Override
    public boolean canSee(EntityPlayer player, ItemStack bookStack) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onLeftClicked(Book book, CategoryAbstract category, int mouseX, int mouseY, EntityPlayer player, GuiCategory guiCategory) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiEntry(book, category, this, player, guiCategory.bookStack));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRightClicked(Book book, CategoryAbstract category, int mouseX, int mouseY, EntityPlayer player, GuiCategory guiCategory) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onInit(Book book, CategoryAbstract category, GuiCategory guiCategory, EntityPlayer player, ItemStack bookStack) {
    }
}