package amerifrance.guideapi.categories;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.CategoryBase;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class CategoryItemStack extends CategoryBase {

    public ItemStack stack;

    public CategoryItemStack(List<EntryAbstract> entryList, String unlocCategoryName, ItemStack stack) {
        super(entryList, unlocCategoryName);
        this.stack = stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
        if (drawOnLeft) {
            guiBase.mc.getTextureManager().bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "category_left.png"));
            GuiHelper.drawIconWithColor(categoryX - 5, categoryY + 2, 102, 12, guiBase.publicZLevel, book.bookColor);
        } else {
            guiBase.mc.getTextureManager().bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "category_right.png"));
            GuiHelper.drawIconWithColor(categoryX - 80, categoryY + 2, 102, 12, guiBase.publicZLevel, book.bookColor);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
        GuiHelper.drawItemStack(this.stack, categoryX, categoryY);
        if (canSee(guiBase.player, guiBase.bookStack) && GuiHelper.isMouseBetween(mouseX, mouseY, categoryX, categoryY, categoryWidth, categoryHeight)) {
            guiBase.drawHoveringText(this.getTooltip(), mouseX, mouseY, Minecraft.getMinecraft().fontRenderer);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CategoryItemStack that = (CategoryItemStack) o;
        if (stack != null ? !stack.isItemEqual(that.stack) : that.stack != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (stack != null ? stack.hashCode() : 0);
        return result;
    }
}
