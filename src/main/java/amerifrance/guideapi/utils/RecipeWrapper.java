package amerifrance.guideapi.utils;

import net.minecraft.recipe.Recipe;

import java.util.List;

public class RecipeWrapper {

    private final Recipe<?> recipe;
    private final List<RenderStack> inputs;
    private final RenderStack output;

    public RecipeWrapper(Recipe<?> recipe, List<RenderStack> inputs, RenderStack output) {
        this.recipe = recipe;
        this.inputs = inputs;
        this.output = output;
    }

    public Recipe<?> getRecipe() {
        return recipe;
    }

    public List<RenderStack> getInputs() {
        return inputs;
    }

    public RenderStack getOutput() {
        return output;
    }
}
