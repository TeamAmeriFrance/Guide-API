package amerifrance.guideapi.page;

import amerifrance.guideapi.api.IRecipeRenderer;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.page.reciperenderer.ShapedOreRecipeRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapedRecipesRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapelessOreRecipeRenderer;
import amerifrance.guideapi.page.reciperenderer.ShapelessRecipesRenderer;
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
public class PageIRecipe extends Page {

    public IRecipe recipe;
    public IRecipeRenderer iRecipeRenderer;

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
}
