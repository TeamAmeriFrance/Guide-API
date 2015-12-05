package amerifrance.guideapi.pages;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IRecipeRenderer;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.PageBase;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.pages.reciperenderers.ShapedOreRecipeRenderer;
import amerifrance.guideapi.pages.reciperenderers.ShapedRecipesRenderer;
import amerifrance.guideapi.pages.reciperenderers.ShapelessOreRecipeRenderer;
import amerifrance.guideapi.pages.reciperenderers.ShapelessRecipesRenderer;
import lombok.EqualsAndHashCode;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@EqualsAndHashCode(callSuper = true)
public class PageIRecipe extends PageBase {

    public IRecipe recipe;
    public IRecipeRenderer iRecipeRenderer;

    /**
     * Use this if you are creating a page for a standard recipe, one of:
     *
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
    }

    private static IRecipeRenderer getRenderer(IRecipe recipe) {
        if (recipe == null) {
            return null;
        } else if (recipe.getClass() == ShapedRecipes.class) {
            return new ShapedRecipesRenderer((ShapedRecipes) recipe);
        } else if (recipe.getClass() == ShapelessRecipes.class) {
            return new ShapelessRecipesRenderer((ShapelessRecipes) recipe);
        } else if (recipe.getClass() == ShapedOreRecipe.class) {
            return new ShapedOreRecipeRenderer((ShapedOreRecipe) recipe);
        } else if (recipe.getClass() == ShapelessOreRecipe.class) {
            return new ShapelessOreRecipeRenderer((ShapelessOreRecipe) recipe);
        } else {
            return null;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
        iRecipeRenderer.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        super.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
        iRecipeRenderer.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }
}
