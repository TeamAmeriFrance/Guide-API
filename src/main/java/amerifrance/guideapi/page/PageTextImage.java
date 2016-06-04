package amerifrance.guideapi.page;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import lombok.EqualsAndHashCode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EqualsAndHashCode(callSuper = true)
public class PageTextImage extends Page {

    public String draw;
    public ResourceLocation image;
    public boolean drawAtTop;

    /**
     * @param draw      - Localized text to draw
     * @param image     - Image to draw
     * @param drawAtTop - Draw Image at top and text at bottom. False reverses this.
     */
    public PageTextImage(String draw, ResourceLocation image, boolean drawAtTop) {

        this.draw = I18n.canTranslate(draw) ? TextHelper.localizeEffect(draw) : draw;
        this.image = image;
        this.drawAtTop = drawAtTop;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        GuiHelper.drawSizedIconWithoutColor(guiLeft + 50, guiTop + (drawAtTop ? 60 : 12), guiBase.xSize, guiBase.ySize, 0);

        PageText text = new PageText(draw, drawAtTop ? 0 : 100);
        text.setUnicodeFlag(unicode);
        text.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }
}
