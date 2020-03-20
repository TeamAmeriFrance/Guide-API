package amerifrance.guideapi.page;

import api.impl.Book;
import api.impl.Page;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import api.util.PageHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PageText extends Page {

    public String draw;
    private int yOffset;

    /**
     * @param draw    - Text to draw. Checks for localization.
     * @param yOffset - How many pixels to offset the text on the Y value
     */
    public PageText(String draw, int yOffset) {
        this.draw = draw;
        this.yOffset = yOffset;
    }

    public PageText(String draw) {
        this(draw, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        boolean startFlag = fontRendererObj.getUnicodeFlag();

        if (unicode)
            fontRendererObj.setUnicodeFlag(true);

        PageHelper.drawFormattedText(guiLeft + 39, guiTop + 12 + yOffset, guiBase, I18n.format(draw));

        if (unicode && !startFlag)
            fontRendererObj.setUnicodeFlag(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageText)) return false;
        if (!super.equals(o)) return false;

        PageText pageText = (PageText) o;

        if (yOffset != pageText.yOffset) return false;
        return draw != null ? draw.equals(pageText.draw) : pageText.draw == null;
    }

    @Override
    public int hashCode() {
        int result = draw != null ? draw.hashCode() : 0;
        result = 31 * result + yOffset;
        return result;
    }
}
