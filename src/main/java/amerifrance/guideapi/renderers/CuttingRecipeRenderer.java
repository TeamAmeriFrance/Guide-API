package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.RecipePair;
import amerifrance.guideapi.utils.RenderStack;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;

import java.util.List;

public class CuttingRecipeRenderer<T> extends RecipeRenderer<T> {

    private static final Identifier TEXTURE = new Identifier("textures/gui/container/crafting_table.png");
    private static final Area AREA = new Area(RenderStack.DRAW_SIZE * 3, RenderStack.DRAW_SIZE);

    private final Item output;
    private List<RecipePair> recipePairs;

    public CuttingRecipeRenderer(Item output) {
        super(RecipeType.STONECUTTING);
        this.output = output;
    }

    @Override
    public void initRecipe(T object, GuideGui guideGui, int x, int y) {
        recipePairs = Lists.newArrayList();

        for (Recipe<?> recipe : getRecipes(recipeType, output)) {
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

            recipePairs.add(new RecipePair(recipeIngredients, outputStack));
        }
    }

    @Override
    public Area getRecipeArea(T object, GuideGui guideGui) {
        return AREA;
    }

    @Override
    public RecipePair getRecipePairToDraw() {
        if (recipePairs.size() > 1) {
            int time = (int) (System.currentTimeMillis() / 1000);
            return recipePairs.get(time % recipePairs.size());
        }

        return recipePairs.get(0);
    }
}
