package amerifrance.guideapi.objects;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import amerifrance.guideapi.objects.abstraction.AbstractPage;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PageBase extends AbstractPage {

    @Override
    public void draw(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
    }

    @Override
    public void drawExtras(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
    }

    @Override
    public boolean canSee(EntityPlayer player, ItemStack bookStack) {
        return true;
    }

    @Override
    public void onLeftClicked(Book book, AbstractCategory category, AbstractEntry entry, int mouseX, int mouseY, EntityPlayer player, GuiEntry guiEntry) {
    }

    @Override
    public void onRightClicked(Book book, AbstractCategory category, AbstractEntry entry, int mouseX, int mouseY, EntityPlayer player, GuiEntry guiEntry) {
    }
}
