package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.RecipeWrapper;
import amerifrance.guideapi.utils.RenderStack;
import com.google.common.collect.Lists;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.List;

public class CookingRecipeRenderer<T> extends RecipeRenderer<T> {

    private static final Identifier TEXTURE = new Identifier("textures/gui/container/crafting_table.png");

    private final Item output;

    private List<RecipeWrapper> recipeWrappers;

    public CookingRecipeRenderer(Item output, RecipeType<?> recipeType) {
        super(recipeType);
        this.output = output;
    }

    @Override
    public void initRecipe(T object, GuideGui guideGui, int x, int y) {
        recipeWrappers = Lists.newArrayList();

        for (Recipe<?> recipe : getRecipes(guideGui, recipeType, output)) {
            AbstractCookingRecipe cookingRecipe = (AbstractCookingRecipe) recipe;

            List<RenderStack> recipeIngredients = Lists.newArrayList();
            Ingredient previewInput = cookingRecipe.getPreviewInputs().get(0);

            ItemStack[] matchingStacks = previewInput.getMatchingStacksClient();
            if (matchingStacks.length > 0) {
                recipeIngredients.add(new RenderStack(matchingStacks, x, y));
            } else {
                recipeIngredients.add(new RenderStack(ItemStack.EMPTY, x, y));
            }

            int outputX = x + 2 * RenderStack.DRAW_SIZE;
            RenderStack outputStack = new RenderStack(cookingRecipe.getOutput(), outputX, y);

            recipeWrappers.add(new RecipeWrapper(recipe, recipeIngredients, outputStack));
        }
    }

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        super.render(object, guideGui, matrixStack, x, y, delta);

        Area area = getArea(object, guideGui);
        float xPos = x + RenderStack.DRAW_SIZE * 1.5F;
        int yPos = y + area.getHeight() - guideGui.getFontHeight();
        AbstractCookingRecipe cookingRecipe = (AbstractCookingRecipe) getRecipePairToDraw().getRecipe();

        String cookTime = cookingRecipe.getCookTime() / 20F + "s";
        guideGui.drawCenteredString(matrixStack, cookTime, xPos, yPos, 0);
    }

    @Override
    public Area getRecipeArea(T object, GuideGui guideGui) {
        return new Area(RenderStack.DRAW_SIZE * 3, RenderStack.DRAW_SIZE + guideGui.getFontHeight());
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
