package de.maxanier.guideapi.page;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.Page;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PageTextImage extends Page {

    public PageText pageText;
    public ResourceLocation image;
    public boolean drawAtTop;

    /**
     * @param draw      - Localized text to draw
     * @param image     - Image to draw
     * @param drawAtTop - Draw Image at top and text at bottom. False reverses this.
     */
    public PageTextImage(String draw, ResourceLocation image, boolean drawAtTop) {
        this.pageText = new PageText(draw, drawAtTop ? 0 : 100);
        this.image = image;
        this.drawAtTop = drawAtTop;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(MatrixStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        Minecraft.getInstance().getTextureManager().bindTexture(image);
        GuiHelper.drawSizedIconWithoutColor(guiLeft + 60, guiTop + (drawAtTop ? 60 : 12), guiBase.xSize, guiBase.ySize, 0);

        pageText.draw(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageTextImage)) return false;
        if (!super.equals(o)) return false;

        PageTextImage that = (PageTextImage) o;

        if (drawAtTop != that.drawAtTop) return false;
        if (pageText != null ? !pageText.equals(that.pageText) : that.pageText != null) return false;
        return image != null ? image.equals(that.image) : that.image == null;
    }

    @Override
    public int hashCode() {
        int result = pageText != null ? pageText.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (drawAtTop ? 1 : 0);
        return result;
    }
}
