package amerifrance.guideapi.pages;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	 * @param recipe
	 *            - Recipe to draw
	 */
	public PageIRecipe(IRecipe recipe) {
		this(recipe, getRenderer(recipe));
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

	/**
	 * @param recipe
	 *            - Recipe to draw
	 * @param iRecipeRenderer
	 *            - Your custom Recipe drawer
	 */
	public PageIRecipe(IRecipe recipe, IRecipeRenderer iRecipeRenderer) {
		this.recipe = recipe;
		this.iRecipeRenderer = iRecipeRenderer;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
		super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
		iRecipeRenderer.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
		super.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
		iRecipeRenderer.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iRecipeRenderer == null) ? 0 : iRecipeRenderer.hashCode());
		result = prime * result + ((recipe == null) ? 0 : recipe.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageIRecipe other = (PageIRecipe) obj;
		if (iRecipeRenderer == null) {
			if (other.iRecipeRenderer != null)
				return false;
		} else if (!iRecipeRenderer.equals(other.iRecipeRenderer))
			return false;
		if (recipe == null) {
			if (other.recipe != null)
				return false;
		} else if (!recipe.equals(other.recipe))
			return false;
		return true;
	}
}
