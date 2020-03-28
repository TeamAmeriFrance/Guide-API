package amerifrance.guideapi.page;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.BaseScreen;
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
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        Minecraft.getInstance().getTextureManager().bindTexture(image);
        GuiHelper.drawSizedIconWithoutColor(guiLeft + 50, guiTop + (drawAtTop ? 60 : 12), guiBase.xSize, guiBase.ySize, 0);

        pageText.setUnicodeFlag(unicode);
        pageText.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
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
