package amerifrance.guideapi.objects;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import amerifrance.guideapi.objects.abstraction.AbstractPage;
import amerifrance.guideapi.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class EntryBase extends AbstractEntry {

    public EntryBase(List<AbstractPage> pageList, String unlocEntryName) {
        super(pageList, unlocEntryName);
    }

    @Override
    public void draw(Book book, AbstractCategory category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, entryWidth, entryHeight)) {
            fontRenderer.drawString(getLocalizedName(), entryX, entryY - 2, 0x423EBC);
        } else {
            fontRenderer.drawString(getLocalizedName(), entryX, entryY, 0);
        }
    }

    @Override
    public void drawExtras(Book book, AbstractCategory category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
    }

    @Override
    public boolean canSee(EntityPlayer player) {
        return true;
    }

    @Override
    public void onLeftClicked(Book book, AbstractCategory category, int mouseX, int mouseY, EntityPlayer player, GuiCategory guiCategory) {
        System.out.println(getLocalizedName() + "Left Clicked");
        Minecraft.getMinecraft().displayGuiScreen(new GuiEntry(guiCategory, book, category, this, player));
    }

    @Override
    public void onRightClicked(Book book, AbstractCategory category, int mouseX, int mouseY, EntityPlayer player, GuiCategory guiCategory) {
        System.out.println(getLocalizedName() + "Right Clicked");
    }
}