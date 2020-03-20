package amerifrance.guideapi.page.reciperenderer;

import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;

// TODO: Fix rendering of recipe
public class ShapedOreRecipeRenderer extends ShapedRecipesRenderer {

    public ShapedOreRecipeRenderer(ShapedOreRecipe recipe) {
        super(new ShapedRecipe(recipe.getGroup(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getRecipeOutput()));
    }
}
