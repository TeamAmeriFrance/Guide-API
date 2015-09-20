package amerifrance.guideapi.pages;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.PageBase;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * Use {@link PageTextImage}
 */
@Deprecated
public class PageLocImage extends PageBase {

    public String locText;
    public ResourceLocation image;
    public boolean drawAtTop;

    /**
     * @param locText   - Localized text to draw
     * @param image     - Image to draw
     * @param drawAtTop - Draw Image at top and text at bottom. False reverses this.
     */
    public PageLocImage(String locText, ResourceLocation image, boolean drawAtTop) {

        this.locText = locText;
        this.image = image;
        this.drawAtTop = drawAtTop;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        if (drawAtTop) {
            GuiHelper.drawSizedIconWithoutColor(guiLeft + 50, guiTop + 12, guiBase.xSize, guiBase.ySize, 0);

            fontRenderer.setUnicodeFlag(true);
            fontRenderer.drawSplitString(locText, guiLeft + 39, guiTop + 112, 3 * guiBase.xSize / 5, 0);
            fontRenderer.setUnicodeFlag(false);
        } else {
            GuiHelper.drawSizedIconWithoutColor(guiLeft + 50, guiTop + 60, guiBase.xSize, guiBase.ySize, 0);

            fontRenderer.setUnicodeFlag(true);
            fontRenderer.drawSplitString(locText, guiLeft + 39, guiTop + 12, 3 * guiBase.xSize / 5, 0);
            fontRenderer.setUnicodeFlag(false);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PageLocImage that = (PageLocImage) o;
        if (drawAtTop != that.drawAtTop) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (locText != null ? !locText.equals(that.locText) : that.locText != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = locText != null ? locText.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (drawAtTop ? 1 : 0);
        return result;
    }
}
