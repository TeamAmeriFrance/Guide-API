package amerifrance.guideapi.page;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.IngredientCycler;
import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PageItemStack extends PageText {

    public Ingredient ingredient;
    private IngredientCycler ingredientCycler = new IngredientCycler();


    public PageItemStack(String draw, Ingredient ingredient){
        super(draw,60);
        this.ingredient=ingredient;
    }
    /**
     * @param draw  - Unlocalized text to draw
     * @param ingredient - ItemStack to render
     */
    public PageItemStack(String draw, ItemStack ingredient) {
        this(draw,Ingredient.fromStacks(ingredient));
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
        ingredientCycler.tick(guiBase.getMinecraft());
        ingredientCycler.getCycledIngredientStack(ingredient,0).ifPresent(stack -> {
            GuiHelper.drawScaledItemStack(stack, guiLeft + 75, guiTop + 20, 3);
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageItemStack)) return false;
        if (!super.equals(o)) return false;

        PageItemStack that = (PageItemStack) o;

        return ingredient != null ? ingredient.equals(that.ingredient) : that.ingredient == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ingredient != null ? ingredient.hashCode() : 0);
        return result;
    }
}
