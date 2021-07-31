package de.maxanier.guideapi.entry;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.Entry;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Objects;

public class EntryResourceLocation extends Entry {

    public ResourceLocation image;

    public EntryResourceLocation(List<IPage> pageList, Component name, ResourceLocation resourceLocation) {
        super(pageList, name);
        this.image = resourceLocation;
    }

    public EntryResourceLocation(Component name, ResourceLocation image) {
        super(name);
        this.image = image;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(PoseStack stack, Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, image);
        GuiHelper.drawSizedIconWithoutColor(stack, entryX + 2, entryY, 16, 16, 1F);

        super.drawExtras(stack, book, category, entryX, entryY, entryWidth, entryHeight, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryResourceLocation that)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
