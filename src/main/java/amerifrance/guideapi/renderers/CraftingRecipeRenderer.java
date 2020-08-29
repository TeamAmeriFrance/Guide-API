package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.RenderStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class CraftingRecipeRenderer<T> extends RecipeRenderer<T> {

    private static final Identifier TEXTURE = new Identifier("textures/gui/container/crafting_table.png");

    private final Item output;

    public CraftingRecipeRenderer(Item output) {
        this.output = output;
    }

    @Override
    public void init(T object, GuideGui guideGui, int x, int y) {
        this.clearInputStacks();

        CraftingRecipe recipe = (CraftingRecipe) getRecipes(RecipeType.CRAFTING, output).get(0);

        DefaultedList<Ingredient> previewInputs = recipe.getPreviewInputs();
        for (int i = 0; i < previewInputs.size(); i++) {
            Ingredient ingredient = previewInputs.get(i);
            ItemStack[] input = ingredient.getMatchingStacksClient();

            int xPos = x + (i % 3) * RenderStack.DRAW_SIZE;
            int yPos = y + (i / 3) * RenderStack.DRAW_SIZE;

            if (input.length > 0) {
                this.addRenderStack(new RenderStack(input[0], xPos, yPos));
            } else {
                this.addRenderStack(new RenderStack(ItemStack.EMPTY, xPos, yPos));
            }
        }

        int outputX = x + 4 * RenderStack.DRAW_SIZE;
        int outputY = y + 1 * RenderStack.DRAW_SIZE;
        this.setOutputStack(new RenderStack(recipe.getOutput(), outputX, outputY));
    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        return new Area(RenderStack.DRAW_SIZE * 5, RenderStack.DRAW_SIZE * 3);
    }
}
