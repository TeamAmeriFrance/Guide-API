package amerifrance.guideapi.page;

import api.impl.Book;
import api.impl.Page;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import api.util.GuiHelper;
import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PageImage extends Page {

    public ResourceLocation image;

    /**
     * @param image - Image to draw
     */
    public PageImage(ResourceLocation image) {
        this.image = image;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        Minecraft.getInstance().getTextureManager().bindTexture(image);
        GuiHelper.drawSizedIconWithoutColor(guiLeft + 50, guiTop + 34, guiBase.xSize, guiBase.ySize, 1F);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageImage)) return false;
        if (!super.equals(o)) return false;

        PageImage pageImage = (PageImage) o;

        return image != null ? image.equals(pageImage.image) : pageImage.image == null;
    }

    @Override
    public int hashCode() {
        return image != null ? image.hashCode() : 0;
    }
}
