package de.maxanier.guideapi.page;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.api.util.IngredientCycler;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PageItemStack extends PageText {

    public Ingredient ingredient;
    private final IngredientCycler ingredientCycler = new IngredientCycler();


    public PageItemStack(ITextProperties draw, Ingredient ingredient) {
        super(draw, 60);
        this.ingredient = ingredient;
    }

    /**
     * @param draw       - Unlocalized text to draw
     * @param ingredient - ItemStack to render
     */
    public PageItemStack(ITextProperties draw, ItemStack ingredient) {
        this(draw, Ingredient.fromStacks(ingredient));
    }

    /**
     * @param draw - Unlocalized text to draw
     * @param item - Item to render
     */
    public PageItemStack(ITextProperties draw, Item item) {
        this(draw, new ItemStack(item));
    }

    /**
     * @param draw  - Unlocalized text to draw
     * @param block - Block to render
     */
    public PageItemStack(ITextProperties draw, Block block) {
        this(draw, new ItemStack(block));
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(MatrixStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        ingredientCycler.tick(guiBase.getMinecraft());
        ingredientCycler.getCycledIngredientStack(ingredient, 0).ifPresent(s -> {
            GuiHelper.drawScaledItemStack(stack, s, guiLeft + 101, guiTop + 20, 3);
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
