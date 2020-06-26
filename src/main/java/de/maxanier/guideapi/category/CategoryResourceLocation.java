package de.maxanier.guideapi.category;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.Category;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

public class CategoryResourceLocation extends Category {

    public ResourceLocation resourceLocation;

    public CategoryResourceLocation(Map<ResourceLocation, EntryAbstract> entries, String unlocCategoryName, ResourceLocation resourceLocation) {
        super(entries, unlocCategoryName);
        this.resourceLocation = resourceLocation;
    }

    public CategoryResourceLocation(String name, ResourceLocation resourceLocation) {
        super(name);
        this.resourceLocation = resourceLocation;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(MatrixStack stack, Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, BaseScreen guiBase, boolean drawOnLeft, ItemRenderer renderItem) {
        Minecraft.getInstance().getTextureManager().bindTexture(resourceLocation);
        GuiHelper.drawSizedIconWithoutColor(stack, categoryX, categoryY, 48, 48, 0);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(MatrixStack stack, Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, BaseScreen guiBase, boolean drawOnLeft, ItemRenderer renderItem) {
        if (canSee(guiBase.player, guiBase.bookStack) && GuiHelper.isMouseBetween(mouseX, mouseY, categoryX, categoryY, categoryWidth, categoryHeight))
            guiBase.func_238654_b_(stack, this.getTooltip(), mouseX, mouseY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryResourceLocation)) return false;
        if (!super.equals(o)) return false;

        CategoryResourceLocation that = (CategoryResourceLocation) o;

        return resourceLocation != null ? resourceLocation.equals(that.resourceLocation) : that.resourceLocation == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (resourceLocation != null ? resourceLocation.hashCode() : 0);
        return result;
    }
}
