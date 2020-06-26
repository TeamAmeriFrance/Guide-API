package de.maxanier.guideapi.page;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.Page;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.PageHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ForgeI18n;


public class PageText extends Page {

    public String draw;
    private final int yOffset;

    /**
     * @param draw    - Text to draw. Checks for localization.
     * @param yOffset - How many pixels to offset the text on the Y value
     */
    public PageText(String draw, int yOffset) {
        this.draw = draw;
        this.yOffset = yOffset;
    }

    public PageText(String draw) {
        this(draw, 5);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(MatrixStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {

        PageHelper.drawFormattedText(guiLeft + 44, guiTop + 12 + yOffset, guiBase, ForgeI18n.getPattern(draw));

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
