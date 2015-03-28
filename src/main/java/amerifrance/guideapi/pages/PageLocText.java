package amerifrance.guideapi.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.PageBase;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;

public class PageLocText extends PageBase {

    public String locText;

    /**
     *
     * @param locText - Pre-localized text to draw.
     */
    public PageLocText(String locText) {
        this.locText = locText;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        fontRenderer.setUnicodeFlag(true);
        fontRenderer.drawSplitString(locText, guiLeft + 39, guiTop + 12, 3 * guiBase.xSize / 5, 0);
        fontRenderer.setUnicodeFlag(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PageLocText that = (PageLocText) o;
        if (locText != null ? !locText.equals(that.locText) : that.locText != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return locText != null ? locText.hashCode() : 0;
    }
}
