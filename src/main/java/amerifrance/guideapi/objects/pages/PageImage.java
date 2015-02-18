package amerifrance.guideapi.objects.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import amerifrance.guideapi.util.GuiHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class PageImage extends PageBase {

    public ResourceLocation image;

    /**
     *
     * @param image - Image to draw
     */
    public PageImage(ResourceLocation image) {
        this.image = image;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        GuiHelper.drawSizedIconWithoutColor(guiLeft + 50, guiTop + 12, guiBase.xSize, guiBase.ySize, 0);
    }
}
