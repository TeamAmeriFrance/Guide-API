package de.maxanier.guideapi.page;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.Page;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

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
    public void draw(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, image);
        GuiHelper.drawSizedIconWithoutColor(stack, guiLeft + 60, guiTop + 34, guiBase.xSize, guiBase.ySize, 1F);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageImage pageImage)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(image, pageImage.image);
    }

    @Override
    public int hashCode() {
        return image != null ? image.hashCode() : 0;
    }
}
