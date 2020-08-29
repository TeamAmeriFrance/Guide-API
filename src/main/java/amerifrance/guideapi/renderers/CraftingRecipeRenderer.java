package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.RecipePair;
import amerifrance.guideapi.utils.RenderStack;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class CraftingRecipeRenderer<T> extends RecipeRenderer<T> {

    private static final Identifier TEXTURE = new Identifier("textures/gui/container/crafting_table.png");

    private final Item output;
    private List<RecipePair> recipePairs;

    public CraftingRecipeRenderer(Item output) {
        this.output = output;
    }

    @Override
    public void init(T object, GuideGui guideGui, int x, int y) {
        recipePairs = Lists.newArrayList();

        getRecipes(RecipeType.CRAFTING, output).forEach(recipe -> {
            List<RenderStack> recipeIngredients = Lists.newArrayList();
            DefaultedList<Ingredient> previewInputs = recipe.getPreviewInputs();

            int outputX = x + 4 * RenderStack.DRAW_SIZE;
            int outputY = y + 1 * RenderStack.DRAW_SIZE;
            RenderStack outputStack = new RenderStack(recipe.getOutput(), outputX, outputY);

            for (int i = 0; i < previewInputs.size(); i++) {
                ItemStack[] matchingStacks = previewInputs.get(i).getMatchingStacksClient();

                int xPos = x + (i % 3) * RenderStack.DRAW_SIZE;
                int yPos = y + (i / 3) * RenderStack.DRAW_SIZE;

                if (matchingStacks.length > 0) {
                    recipeIngredients.add(new RenderStack(matchingStacks, xPos, yPos));
                } else {
                    recipeIngredients.add(new RenderStack(ItemStack.EMPTY, xPos, yPos));
                }
            }

            recipePairs.add(new RecipePair(recipeIngredients, outputStack));
        });
    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        return new Area(RenderStack.DRAW_SIZE * 5, RenderStack.DRAW_SIZE * 3);
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
