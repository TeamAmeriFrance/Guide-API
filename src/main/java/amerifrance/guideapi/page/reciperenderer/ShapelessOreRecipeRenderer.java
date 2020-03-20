package amerifrance.guideapi.page.reciperenderer;

import net.minecraft.item.crafting.ShapelessRecipe;

public class ShapelessOreRecipeRenderer extends ShapelessRecipesRenderer {

    public ShapelessOreRecipeRenderer(ShapelessOreRecipe recipe) {
        super(new ShapelessRecipes(recipe.getGroup(), recipe.getRecipeOutput(), recipe.getIngredients()));
    }
}
