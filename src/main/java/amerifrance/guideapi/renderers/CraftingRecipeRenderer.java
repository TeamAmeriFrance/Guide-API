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
import net.minecraft.recipe.*;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class CraftingRecipeRenderer extends RecipeRenderer {

    private static final RenderElement CRAFTING_BACKGROUND = new RenderElement(RECIPE_ELEMENTS, 0, 48, 102, 56);
    private static final Area AREA = new Area(102, 56);
    public static final int RENDER_LINE_HEIGHT = 2;

    private final Item output;
    private List<RecipeWrapper> recipeWrappers;

    public CraftingRecipeRenderer(Item output) {
        super(RecipeType.CRAFTING);
        this.output = output;
    }

    @Override
    public void renderRecipeBackground(GuideGui guideGui, MatrixStack matrixStack, int x, int y) {
        CRAFTING_BACKGROUND.render(guideGui, matrixStack, guideGui.getMinecraftClient().getTextureManager(), x, y);
    }

    @Override
    public void initRecipe(GuideGui guideGui, int x, int y) {
        recipeWrappers = Lists.newArrayList();

        for (Recipe<?> recipe : getRecipes(guideGui, recipeType, output)) {
            CraftingRecipe craftingRecipe = (CraftingRecipe) recipe;

            List<RenderStack> recipeIngredients = Lists.newArrayList();
            DefaultedList<Ingredient> previewInputs = craftingRecipe.getPreviewInputs();

            for (int i = 0; i < previewInputs.size(); i++) {
                int line = i / 3;
                int column = i % 3;

                if (craftingRecipe instanceof ShapedRecipe)
                    column = i % ((ShapedRecipe) craftingRecipe).getWidth();

                if (craftingRecipe instanceof ShapedRecipe && previewInputs.size() > 3)
                    line = i / ((ShapedRecipe) craftingRecipe).getHeight();

                int xPos = x + RENDER_LINE_HEIGHT * (column + 1) + column * RenderStack.DRAW_SIZE;
                int yPos = y + RENDER_LINE_HEIGHT * (line + 1) + line * RenderStack.DRAW_SIZE;

                ItemStack[] matchingStacks = previewInputs.get(i).getMatchingStacksClient();
                if (matchingStacks.length > 0) {
                    recipeIngredients.add(new RenderStack(matchingStacks, xPos, yPos));
                } else {
                    recipeIngredients.add(new RenderStack(ItemStack.EMPTY, xPos, yPos));
                }
            }

            int outputX = x + 5 * RenderStack.DRAW_SIZE;
            int outputY = y + 2 * RENDER_LINE_HEIGHT + 1 * RenderStack.DRAW_SIZE;
            RenderStack outputStack = new RenderStack(craftingRecipe.getOutput(), outputX, outputY);

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
