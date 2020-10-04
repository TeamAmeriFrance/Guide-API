package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.RenderElement;
import amerifrance.guideapi.gui.RenderStack;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.RecipeWrapper;
import com.google.common.collect.Lists;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

import java.util.List;

public class CookingRecipeRenderer<T> extends RecipeRenderer {

    private static final RenderElement COOKING_BACKGROUND = new RenderElement(RECIPE_ELEMENTS, 0, 104, 68, 28);

    private final Item output;

    private List<RecipeWrapper> recipeWrappers;

    public CookingRecipeRenderer(Item output, RecipeType<?> recipeType) {
        super(recipeType);
        this.output = output;
    }

    @Override
    public void initRecipe(GuideGui guideGui, int x, int y) {
        recipeWrappers = Lists.newArrayList();

        for (Recipe<?> recipe : getRecipes(guideGui, recipeType, output)) {
            AbstractCookingRecipe cookingRecipe = (AbstractCookingRecipe) recipe;

            List<RenderStack> recipeIngredients = Lists.newArrayList();
            Ingredient previewInput = cookingRecipe.getPreviewInputs().get(0);

            ItemStack[] matchingStacks = previewInput.getMatchingStacksClient();
            if (matchingStacks.length > 0) {
                recipeIngredients.add(new RenderStack(matchingStacks, x + 2, y + 6));
            } else {
                recipeIngredients.add(new RenderStack(ItemStack.EMPTY, x + 2, y + 6));
            }

            int outputX = x + 46;
            RenderStack outputStack = new RenderStack(cookingRecipe.getOutput(), outputX, y + 6);

            recipeWrappers.add(new RecipeWrapper(recipe, recipeIngredients, outputStack));
        }
    }

    @Override
    public void render(GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        super.render(guideGui, matrixStack, x, y, delta);

        Area area = getArea(guideGui);
        float xPos = x + RenderStack.DRAW_SIZE * 1.5F;
        int yPos = y + area.getHeight() - guideGui.getFontHeight();
        AbstractCookingRecipe cookingRecipe = (AbstractCookingRecipe) getRecipePairToDraw().getRecipe();

        String cookTime = cookingRecipe.getCookTime() / 20F + "s";
        guideGui.drawCenteredString(matrixStack, cookTime, xPos, yPos, 0);
    }

    @Override
    public void renderRecipeBackground(GuideGui guideGui, MatrixStack matrixStack, int x, int y) {
        COOKING_BACKGROUND.render(guideGui, matrixStack, guideGui.getMinecraftClient().getTextureManager(), x, y);
    }

    @Override
    public Area getRecipeArea(GuideGui guideGui) {
        return new Area(68, 28 + guideGui.getFontHeight());
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
