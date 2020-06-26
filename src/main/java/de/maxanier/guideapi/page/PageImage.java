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
    public void draw(MatrixStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        Minecraft.getInstance().getTextureManager().bindTexture(image);
        GuiHelper.drawSizedIconWithoutColor(guiLeft + 60, guiTop + 34, guiBase.xSize, guiBase.ySize, 1F);
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
