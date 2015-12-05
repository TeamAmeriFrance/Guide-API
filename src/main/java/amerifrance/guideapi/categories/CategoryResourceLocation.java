package amerifrance.guideapi.categories;

import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.CategoryBase;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class CategoryResourceLocation extends CategoryBase {

    public ResourceLocation resourceLocation;

    public CategoryResourceLocation(List<EntryAbstract> entryList, String unlocCategoryName, ResourceLocation resourceLocation) {
        super(entryList, unlocCategoryName);
        this.resourceLocation = resourceLocation;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GuiHelper.drawSizedIconWithoutColor(categoryX, categoryY, 48, 48, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
        if (canSee(guiBase.player, guiBase.bookStack) && GuiHelper.isMouseBetween(mouseX, mouseY, categoryX, categoryY, categoryWidth, categoryHeight))
            guiBase.drawHoveringText(this.getTooltip(), mouseX, mouseY, Minecraft.getMinecraft().fontRenderer);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        CategoryResourceLocation that = (CategoryResourceLocation) object;

        return !(resourceLocation != null ? !resourceLocation.equals(that.resourceLocation) : that.resourceLocation != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (resourceLocation != null ? resourceLocation.hashCode() : 0);
        return result;
    }
}
