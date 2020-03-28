package amerifrance.guideapi.page;

import amerifrance.guideapi.api.util.IngredientCycler;
import amerifrance.guideapi.page.reciperenderer.FurnaceRecipeRenderer;
import amerifrance.guideapi.api.IRecipeRenderer;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.gui.BaseScreen;
import amerifrance.guideapi.gui.EntryScreen;
import amerifrance.guideapi.page.reciperenderer.ShapedRecipesRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapelessRecipesRenderer;
import amerifrance.guideapi.util.LogHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class PageIRecipe extends Page {

    public IRecipe<?> recipe;
    public IRecipeRenderer iRecipeRenderer;
    protected boolean isValid;
    private IngredientCycler ingredientCycler = new IngredientCycler();
    /**
     * Use this if you are creating a page for a standard recipe, one of:
     * <p>
     * <ul>
     * <li>{@link ShapedRecipe}</li>
     * <li>{@link ShapelessRecipe}</li>
     * <li>{@link FurnaceRecipe}</li>
     * </ul>
     *
     * @param recipe - Recipe to draw
     */
    public PageIRecipe(IRecipe<?> recipe) {
        this(recipe, getRenderer(recipe));
    }

    /**
     * @param recipe          - Recipe to draw
     * @param iRecipeRenderer - Your custom Recipe drawer
     */
    public PageIRecipe(IRecipe<?> recipe, IRecipeRenderer iRecipeRenderer) {
        this.recipe = recipe;
        this.iRecipeRenderer = iRecipeRenderer;
        isValid = recipe != null && iRecipeRenderer != null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        if(isValid) {
            super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
            ingredientCycler.tick(guiBase.getMinecraft());
            iRecipeRenderer.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj,ingredientCycler);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        if(isValid) {
            super.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
            iRecipeRenderer.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
        }
    }

    @Override
    public boolean canSee(Book book, CategoryAbstract category, EntryAbstract entry, PlayerEntity player, ItemStack bookStack, EntryScreen guiEntry) {
        return isValid;
    }


    protected static IRecipeRenderer getRenderer(IRecipe<?> recipe) {
        if (recipe == null) {
            LogHelper.error("Cannot get renderer for null recipe.");
            return null;
        } else if (recipe instanceof ShapedRecipe) {
            return new ShapedRecipesRenderer((ShapedRecipe) recipe);
        } else if (recipe instanceof ShapelessRecipe) {
            return new ShapelessRecipesRenderer((ShapelessRecipe) recipe);
        } else if(recipe instanceof FurnaceRecipe){
            return new FurnaceRecipeRenderer((FurnaceRecipe)recipe);
        }
        else {
            LogHelper.error("Cannot get renderer for recipe type "+recipe.getClass().toString());
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageIRecipe)) return false;
        if (!super.equals(o)) return false;

        PageIRecipe that = (PageIRecipe) o;

        if (recipe != null ? !recipe.equals(that.recipe) : that.recipe != null) return false;
        return iRecipeRenderer != null ? iRecipeRenderer.equals(that.iRecipeRenderer) : that.iRecipeRenderer == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (recipe != null ? recipe.hashCode() : 0);
        result = 31 * result + (iRecipeRenderer != null ? iRecipeRenderer.hashCode() : 0);
        return result;
    }
}
