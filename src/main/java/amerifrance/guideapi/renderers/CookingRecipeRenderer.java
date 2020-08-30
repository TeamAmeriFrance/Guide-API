package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.RecipePair;
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
    private final RecipeType<?> recipeType;
    private final String recipeTypeDescription;
    private List<RecipePair> recipePairs;

    public CookingRecipeRenderer(Item output, RecipeType<?> recipeType) {
        this.output = output;
        this.recipeType = recipeType;
        this.recipeTypeDescription = getRecipeTypeDescription(recipeType);
    }

    @Override
    public void init(T object, GuideGui guideGui, int x, int y) {
        recipePairs = Lists.newArrayList();
        int yPos = (int) (y + 1.5 * guideGui.getTextRenderer().fontHeight);

        for (Recipe<?> recipe : getRecipes(recipeType, output)) {
            AbstractCookingRecipe cookingRecipe = (AbstractCookingRecipe) recipe;

            List<RenderStack> recipeIngredients = Lists.newArrayList();
            Ingredient previewInput = cookingRecipe.getPreviewInputs().get(0);

            ItemStack[] matchingStacks = previewInput.getMatchingStacksClient();
            if (matchingStacks.length > 0) {
                recipeIngredients.add(new RenderStack(matchingStacks, x, yPos));
            } else {
                recipeIngredients.add(new RenderStack(ItemStack.EMPTY, x, yPos));
            }

            int outputX = x + 2 * RenderStack.DRAW_SIZE;
            RenderStack outputStack = new RenderStack(cookingRecipe.getOutput(), outputX, yPos);

            recipePairs.add(new RecipePair(recipeIngredients, outputStack));
        }
    }

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        super.render(object, guideGui, matrixStack, x, y, delta);

        guideGui.getTextRenderer().draw(matrixStack, recipeTypeDescription, x, y, 0);
    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        return new Area(
                Math.max(RenderStack.DRAW_SIZE * 3, guideGui.getTextRenderer().getWidth(recipeTypeDescription)),
                RenderStack.DRAW_SIZE + 2 * guideGui.getTextRenderer().fontHeight
        );
    }

    @Override
    public RecipePair getRecipePairToDraw() {
        if (recipePairs.size() > 1) {
            int time = (int) (System.currentTimeMillis() / 1000);
            return recipePairs.get(time % recipePairs.size());
        }

        return recipePairs.get(0);
    }

    private String getRecipeTypeDescription(RecipeType<?> recipeType) {
        if (recipeType == RecipeType.SMELTING)
            return "SMELTING";
        if (recipeType == RecipeType.BLASTING)
            return "BLASTING";
        if (recipeType == RecipeType.SMOKING)
            return "SMOKING";
        if (recipeType == RecipeType.CAMPFIRE_COOKING)
            return "CAMPFIRE COOKING";

        return "";
    }
}
