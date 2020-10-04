package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.RenderStack;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.RecipeWrapper;
import com.google.common.collect.Lists;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

import java.util.List;

public class CuttingRecipeRenderer extends RecipeRenderer {

    private static final Area AREA = new Area(RenderStack.DRAW_SIZE * 3, RenderStack.DRAW_SIZE);

    private final Item output;
    private List<RecipeWrapper> recipeWrappers;

    public CuttingRecipeRenderer(Item output) {
        super(RecipeType.STONECUTTING);
        this.output = output;
    }

    @Override
    public void renderRecipeBackground(GuideGui guideGui, MatrixStack matrixStack, int x, int y) {

    }

    @Override
    public void initRecipe(GuideGui guideGui, int x, int y) {
        recipeWrappers = Lists.newArrayList();

        for (Recipe<?> recipe : getRecipes(guideGui, recipeType, output)) {
            CuttingRecipe cuttingRecipe = (CuttingRecipe) recipe;

            List<RenderStack> recipeIngredients = Lists.newArrayList();
            Ingredient previewInput = cuttingRecipe.getPreviewInputs().get(0);

            ItemStack[] matchingStacks = previewInput.getMatchingStacksClient();
            if (matchingStacks.length > 0) {
                recipeIngredients.add(new RenderStack(matchingStacks, x, y));
            } else {
                recipeIngredients.add(new RenderStack(ItemStack.EMPTY, x, y));
            }

            int outputX = x + 2 * RenderStack.DRAW_SIZE;
            RenderStack outputStack = new RenderStack(cuttingRecipe.getOutput(), outputX, y);

            recipeWrappers.add(new RecipeWrapper(recipe, recipeIngredients, outputStack));
        }
    }

    @Override
    public Area getRecipeArea(GuideGui guideGui) {
        return AREA;
    }

    @Override
    public RecipeWrapper getRecipePairToDraw() {
        if (recipeWrappers.size() > 1) {
            int time = (int) (System.currentTimeMillis() / 1000);
            return recipeWrappers.get(time % recipeWrappers.size());
        }

        return recipeWrappers.get(0);
    }
}
