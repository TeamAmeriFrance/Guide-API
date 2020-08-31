package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.RecipeWrapper;
import amerifrance.guideapi.utils.RenderStack;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class CraftingRecipeRenderer<T> extends RecipeRenderer<T> {

    private static final Identifier TEXTURE = new Identifier("textures/gui/container/crafting_table.png");
    private static final Area AREA = new Area(RenderStack.DRAW_SIZE * 5, RenderStack.DRAW_SIZE * 3);

    private final Item output;
    private List<RecipeWrapper> recipeWrappers;

    public CraftingRecipeRenderer(Item output) {
        super(RecipeType.CRAFTING);
        this.output = output;
    }

    @Override
    public void initRecipe(T object, GuideGui guideGui, int x, int y) {
        recipeWrappers = Lists.newArrayList();

        for (Recipe<?> recipe : getRecipes(guideGui, recipeType, output)) {
            CraftingRecipe craftingRecipe = (CraftingRecipe) recipe;

            List<RenderStack> recipeIngredients = Lists.newArrayList();
            DefaultedList<Ingredient> previewInputs = craftingRecipe.getPreviewInputs();

            for (int i = 0; i < previewInputs.size(); i++) {
                int xPos = x + (i % 3) * RenderStack.DRAW_SIZE;
                int yPos = y + (i / 3) * RenderStack.DRAW_SIZE;

                if (craftingRecipe instanceof ShapedRecipe)
                    xPos = x + (i % ((ShapedRecipe) craftingRecipe).getWidth()) * RenderStack.DRAW_SIZE;

                if (craftingRecipe instanceof ShapedRecipe && previewInputs.size() > 3)
                    yPos = y + (i / ((ShapedRecipe) craftingRecipe).getHeight()) * RenderStack.DRAW_SIZE;

                ItemStack[] matchingStacks = previewInputs.get(i).getMatchingStacksClient();
                if (matchingStacks.length > 0) {
                    recipeIngredients.add(new RenderStack(matchingStacks, xPos, yPos));
                } else {
                    recipeIngredients.add(new RenderStack(ItemStack.EMPTY, xPos, yPos));
                }
            }

            int outputX = x + 4 * RenderStack.DRAW_SIZE;
            int outputY = y + 1 * RenderStack.DRAW_SIZE;
            RenderStack outputStack = new RenderStack(craftingRecipe.getOutput(), outputX, outputY);

            recipeWrappers.add(new RecipeWrapper(recipe, recipeIngredients, outputStack));
        }
    }

    @Override
    public Area getRecipeArea(T object, GuideGui guideGui) {
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
