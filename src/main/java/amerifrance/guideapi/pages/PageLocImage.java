package amerifrance.guideapi.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.PageBase;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import amerifrance.guideapi.util.GuiHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class PageLocImage extends PageBase {

    public String locText;
    public ResourceLocation image;
    public boolean drawAtTop;

    /**
     * @param locText - Localized text to draw
     * @param image - Image to draw
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
            fontRenderer.drawSplitString(locText, guiLeft + 37, guiTop + 112, 4 * guiBase.xSize / 6, 0);
            fontRenderer.setUnicodeFlag(false);
        } else {
            GuiHelper.drawSizedIconWithoutColor(guiLeft + 50, guiTop + 60, guiBase.xSize, guiBase.ySize, 0);

            fontRenderer.setUnicodeFlag(true);
            fontRenderer.drawSplitString(locText, guiLeft + 37, guiTop + 12, 4 * guiBase.xSize / 6, 0);
            fontRenderer.setUnicodeFlag(false);
        }
    }
}
