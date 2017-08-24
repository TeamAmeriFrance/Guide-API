package amerifrance.guideapi.page;

import amerifrance.guideapi.api.IRecipeRenderer;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.page.reciperenderer.ShapedOreRecipeRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapedRecipesRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapelessOreRecipeRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapelessRecipesRenderer;
import amerifrance.guideapi.util.LogHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class PageIRecipe extends Page {

    public IRecipe recipe;
    public IRecipeRenderer iRecipeRenderer;
    protected boolean isValid;

    /**
     * Use this if you are creating a page for a standard recipe, one of:
     * <p>
     * <ul>
     * <li>{@link ShapedRecipes}</li>
     * <li>{@link ShapelessRecipes}</li>
     * <li>{@link ShapedOreRecipe}</li>
     * <li>{@link ShapelessOreRecipe}</li>
     * </ul>
     *
     * @param recipe - Recipe to draw
     */
    public PageIRecipe(IRecipe recipe) {
        this(recipe, getRenderer(recipe));
    }

    /**
     * @param recipe          - Recipe to draw
     * @param iRecipeRenderer - Your custom Recipe drawer
     */
    public PageIRecipe(IRecipe recipe, IRecipeRenderer iRecipeRenderer) {
        this.recipe = recipe;
        this.iRecipeRenderer = iRecipeRenderer;
        isValid = recipe != null && iRecipeRenderer != null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        if(isValid) {
            super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
            iRecipeRenderer.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        if(isValid) {
            super.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
            iRecipeRenderer.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
        }
    }

    @Override
    public boolean canSee(Book book, CategoryAbstract category, EntryAbstract entry, EntityPlayer player, ItemStack bookStack, GuiEntry guiEntry) {
        return isValid;
    }

    public static PageIRecipe newShaped(ItemStack output, Object... input) {
        return new PageIRecipe(new ShapedOreRecipe(null, output, input));
    }

    public static PageIRecipe newShapeless(ItemStack output, Object... input) {
        return new PageIRecipe(new ShapelessOreRecipe(null, output, input));
    }

    static IRecipeRenderer getRenderer(IRecipe recipe) {
        if (recipe == null) {
            LogHelper.error("Cannot get renderer for null recipe.");
            return null;
        } else if (recipe instanceof ShapedRecipes) {
            return new ShapedRecipesRenderer((ShapedRecipes) recipe);
        } else if (recipe instanceof ShapelessRecipes) {
            return new ShapelessRecipesRenderer((ShapelessRecipes) recipe);
        } else if (recipe instanceof ShapedOreRecipe) {
            return new ShapedOreRecipeRenderer((ShapedOreRecipe) recipe);
        } else if (recipe instanceof ShapelessOreRecipe) {
            return new ShapelessOreRecipeRenderer((ShapelessOreRecipe) recipe);
        } else {
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
