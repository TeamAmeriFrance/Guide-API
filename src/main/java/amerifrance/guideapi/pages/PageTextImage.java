package amerifrance.guideapi.pages;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.PageBase;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import lombok.EqualsAndHashCode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EqualsAndHashCode(callSuper = true)
public class PageTextImage extends PageBase {

    public String draw;
    public ResourceLocation image;
    public boolean drawAtTop;

    /**
     * @param draw      - Localized text to draw
     * @param image     - Image to draw
     * @param drawAtTop - Draw Image at top and text at bottom. False reverses this.
     */
    public PageTextImage(String draw, ResourceLocation image, boolean drawAtTop) {

        this.draw = StatCollector.canTranslate(draw) ? StatCollector.translateToLocal(draw) : draw;
        this.image = image;
        this.drawAtTop = drawAtTop;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        boolean startFlag = fontRendererObj.getUnicodeFlag();

        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        GuiHelper.drawSizedIconWithoutColor(guiLeft + 50, guiTop + (drawAtTop ? 60 : 12), guiBase.xSize, guiBase.ySize, 0);

        if (unicode)
            fontRendererObj.setUnicodeFlag(true);
        fontRendererObj.drawSplitString(draw, guiLeft + 39, guiTop + (drawAtTop ? 12 : 112), 3 * guiBase.xSize / 5, 0);
        if (unicode && !startFlag)
            fontRendererObj.setUnicodeFlag(false);
    }
}
