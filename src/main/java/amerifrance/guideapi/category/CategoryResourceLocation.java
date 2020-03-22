package amerifrance.guideapi.category;

import api.impl.Book;
import api.impl.Category;
import api.impl.abstraction.EntryAbstract;
import api.util.GuiHelper;
import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.config.GuiUtils;

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
    public void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, BaseScreen guiBase, boolean drawOnLeft, ItemRenderer renderItem) {
        Minecraft.getInstance().getTextureManager().bindTexture(resourceLocation);
        GuiHelper.drawSizedIconWithoutColor(categoryX, categoryY, 48, 48, 0);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, BaseScreen guiBase, boolean drawOnLeft, ItemRenderer renderItem) {
        if (canSee(guiBase.player, guiBase.bookStack) && GuiHelper.isMouseBetween(mouseX, mouseY, categoryX, categoryY, categoryWidth, categoryHeight))
            GuiUtils.drawHoveringText(this.getTooltip(), mouseX, mouseY, categoryWidth,categoryHeight,-1,Minecraft.getInstance().fontRenderer);
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
