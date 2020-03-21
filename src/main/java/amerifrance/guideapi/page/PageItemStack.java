package amerifrance.guideapi.page;

import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import api.util.GuiHelper;
import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PageItemStack extends PageText {

    public ItemStack stack;

    /**
     * @param draw  - Unlocalized text to draw
     * @param stack - ItemStack to render
     */
    public PageItemStack(String draw, ItemStack stack) {
        super(draw, 60);
        this.stack = stack;
    }

    /**
     * @param draw - Unlocalized text to draw
     * @param item - Item to render
     */
    public PageItemStack(String draw, Item item) {
        this(draw, new ItemStack(item));
    }

    /**
     * @param draw  - Unlocalized text to draw
     * @param block - Block to render
     */
    public PageItemStack(String draw, Block block) {
        this(draw, new ItemStack(block));
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        GuiHelper.drawScaledItemStack(stack, guiLeft + 75, guiTop + 20, 3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageItemStack)) return false;
        if (!super.equals(o)) return false;

        PageItemStack that = (PageItemStack) o;

        return stack != null ? stack.equals(that.stack) : that.stack == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (stack != null ? stack.hashCode() : 0);
        return result;
    }
}
