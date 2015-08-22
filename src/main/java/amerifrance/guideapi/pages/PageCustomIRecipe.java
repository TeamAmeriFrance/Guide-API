package amerifrance.guideapi.pages;

import net.minecraft.item.crafting.IRecipe;
import amerifrance.guideapi.api.abstraction.IRecipeRenderer;

/**
 * Deprecated, use {@link PageIRecipe} now.
 */
@Deprecated
public class PageCustomIRecipe extends PageIRecipe {

	/**
	 * @param recipe
	 *            - Recipe to draw
	 * @param iRecipeRenderer
	 *            - Your custom Recipe drawer
	 */
	public PageCustomIRecipe(IRecipe recipe, IRecipeRenderer iRecipeRenderer) {
		super(recipe, iRecipeRenderer);
	}
}
