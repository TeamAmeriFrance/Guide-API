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

public class PageText extends PageBase {

    public String draw;
    private int yOffset;

    /**
     * @param draw    - Text to draw. Checks for localization.
     * @param yOffset - How many pixels to offset the text on the Y value
     */
    public PageText(String draw, int yOffset) {
        this.draw = StatCollector.canTranslate(draw) ? StatCollector.translateToLocal(draw) : draw;
        this.yOffset = yOffset;
    }

    public PageText(String draw) {
        this(draw, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        boolean startFlag = fontRenderer.getUnicodeFlag();

        if (unicode)
            fontRenderer.setUnicodeFlag(true);

        fontRenderer.drawSplitString(draw, guiLeft + 39, guiTop + 12 + yOffset, 3 * guiBase.xSize / 5, 0);

        if (unicode && !startFlag)
            fontRenderer.setUnicodeFlag(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PageText that = (PageText) o;
        if (draw != null ? !draw.equals(that.draw) : that.draw != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return draw != null ? draw.hashCode() : 0;
    }
}
