package amerifrance.guideapi.pages;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.PageBase;
import amerifrance.guideapi.gui.GuiBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

/**
 * Use {@link PageText}
 */
@Deprecated
public class PageUnlocText extends PageBase {

    public String unlocText;
    private int yOffset;
    public boolean unicode;

    /**
     * @param unlocText - Pre-localized text to draw.
     * @param yOffset   - How many pixels to offset the text on the Y value
     * @param unicode   - Whether to enable the unicode flag or not
     */
    public PageUnlocText(String unlocText, int yOffset, boolean unicode) {
        this.unlocText = unlocText;
        this.yOffset = yOffset;
        this.unicode = unicode;
    }

    public PageUnlocText(String unlocText, int yOffset) {
        this(unlocText, yOffset, false);
    }

    public PageUnlocText(String unlocText, boolean unicode) {
        this(unlocText, 60, unicode);
    }

    public PageUnlocText(String unlocText) {
        this(unlocText, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        fontRenderer.setUnicodeFlag(true);
        fontRenderer.drawSplitString(StatCollector.translateToLocal(unlocText), guiLeft + 39, guiTop + 12 + yOffset, 3 * guiBase.xSize / 5, 0);
        fontRenderer.setUnicodeFlag(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PageUnlocText that = (PageUnlocText) o;
        if (unlocText != null ? !unlocText.equals(that.unlocText) : that.unlocText != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return unlocText != null ? unlocText.hashCode() : 0;
    }
}
