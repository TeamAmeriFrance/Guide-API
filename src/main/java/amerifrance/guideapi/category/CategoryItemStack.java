package amerifrance.guideapi.category;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Category;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public class CategoryItemStack extends Category {

    public ItemStack stack;

    public CategoryItemStack(Map<ResourceLocation, EntryAbstract> entries, String name, ItemStack stack) {
        super(entries, name);
        this.stack = stack;
    }

    public CategoryItemStack(String name, ItemStack stack) {
        super(name);
        this.stack = stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
        GuiHelper.drawScaledItemStack(this.stack, categoryX, categoryY, 1.5F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
        if (canSee(guiBase.player, guiBase.bookStack) && GuiHelper.isMouseBetween(mouseX, mouseY, categoryX, categoryY, categoryWidth, categoryHeight))
            guiBase.drawHoveringText(this.getTooltip(), mouseX, mouseY, Minecraft.getMinecraft().fontRenderer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryItemStack)) return false;
        if (!super.equals(o)) return false;

        CategoryItemStack that = (CategoryItemStack) o;

        return stack != null ? stack.equals(that.stack) : that.stack == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (stack != null ? stack.hashCode() : 0);
        return result;
    }
}
