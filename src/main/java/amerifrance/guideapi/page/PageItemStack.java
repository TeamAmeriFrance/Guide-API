package amerifrance.guideapi.page;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

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

    /**
     * @param draw  - Unlocalized text to draw
     * @param entry - OreDict entry to render
     */
    public PageItemStack(String draw, String entry) {
        super(draw, 60);
        this.stack = new ItemStack(Blocks.FIRE);

        if (!OreDictionary.getOres(entry).isEmpty()) {
            for (int i = 0; i < OreDictionary.getOres(entry).size(); i++) {
                ItemStack stack = OreDictionary.getOres(entry).get(i);
                this.stack = stack;
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
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
