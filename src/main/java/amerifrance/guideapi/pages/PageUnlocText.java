package amerifrance.guideapi.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.PageBase;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

public class PageUnlocText extends PageBase {

    public String unlocText;

    /**
     * @param unlocText - Unlocalized text to draw
     */
    public PageUnlocText(String unlocText) {
        this.unlocText = unlocText;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        fontRenderer.setUnicodeFlag(true);
        fontRenderer.drawSplitString(StatCollector.translateToLocal(unlocText), guiLeft + 37, guiTop + 12, 4 * guiBase.xSize / 6, 0);
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
