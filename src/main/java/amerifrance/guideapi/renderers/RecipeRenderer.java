package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.Gradient;
import amerifrance.guideapi.utils.MouseHelper;
import amerifrance.guideapi.utils.RecipePair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class RecipeRenderer<T> implements Renderer<T> {

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        Area area = getArea(object, guideGui);

        Gradient.VERTICAL.draw(x, y, area.getWidth(), area.getHeight(), Color.WHITE.getRGB(), Color.YELLOW.getRGB());
        drawRecipe(guideGui, matrixStack, getRecipePairToDraw());
    }

    @Override
    public void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
        hoverRecipe(guideGui, matrixStack, mouseX, mouseY, getRecipePairToDraw());
    }

    public abstract RecipePair getRecipePairToDraw();

    protected List<Recipe> getRecipes(RecipeType recipeType, Item output) {
        RecipeManager recipeManager = MinecraftClient.getInstance().world.getRecipeManager();
        return recipeManager.values().stream()
                .filter(recipe -> recipe.getType() == recipeType && recipe.getOutput().getItem() == output)
                .collect(Collectors.toList());
    }

    private void drawRecipe(GuideGui guideGui, MatrixStack matrixStack, RecipePair recipePair) {
        recipePair.getInputs().forEach(renderStack -> renderStack.render(guideGui, matrixStack));
        recipePair.getOutput().render(guideGui, matrixStack);
    }

    private void hoverRecipe(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, RecipePair recipePair) {
        recipePair.getInputs().stream()
                .filter(renderStack -> MouseHelper.isInRenderStack(renderStack, mouseX, mouseY))
                .forEach(renderStack -> renderStack.hover(guideGui, matrixStack, mouseX, mouseY));

        if (MouseHelper.isInRenderStack(recipePair.getOutput(), mouseX, mouseY))
            recipePair.getOutput().hover(guideGui, matrixStack, mouseX, mouseY);
    }
}
