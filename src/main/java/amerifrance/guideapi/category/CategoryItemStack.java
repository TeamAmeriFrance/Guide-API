package amerifrance.guideapi.category;

import api.impl.Book;
import api.impl.Category;
import api.impl.abstraction.EntryAbstract;
import api.util.GuiHelper;
import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.config.GuiUtils;

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
    @OnlyIn(Dist.CLIENT)
    public void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, BaseScreen guiBase, boolean drawOnLeft, ItemRenderer renderItem) {
        GuiHelper.drawScaledItemStack(this.stack, categoryX, categoryY, 1.5F);
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
