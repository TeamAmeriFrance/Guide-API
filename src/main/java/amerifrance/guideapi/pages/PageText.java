package amerifrance.guideapi.pages;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.PageBase;
import amerifrance.guideapi.gui.GuiBase;
import lombok.EqualsAndHashCode;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EqualsAndHashCode(callSuper = true)
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
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        boolean startFlag = fontRendererObj.getUnicodeFlag();

        if (unicode)
            fontRendererObj.setUnicodeFlag(true);

        fontRendererObj.drawSplitString(draw, guiLeft + 39, guiTop + 12 + yOffset, 3 * guiBase.xSize / 5, 0);

        if (unicode && !startFlag)
            fontRendererObj.setUnicodeFlag(false);
    }
}
