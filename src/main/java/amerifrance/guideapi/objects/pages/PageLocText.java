package amerifrance.guideapi.objects.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import net.minecraft.client.gui.FontRenderer;

public class PageLocText extends PageBase {

    public String locText;

    public PageLocText(String locText) {
        this.locText = locText;
    }

    @Override
    public void draw(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        fontRenderer.setUnicodeFlag(true);
        fontRenderer.drawSplitString(locText, guiLeft + 37, guiTop + 12, 4 * guiBase.xSize / 6, 0);
        fontRenderer.setUnicodeFlag(false);
    }
}
