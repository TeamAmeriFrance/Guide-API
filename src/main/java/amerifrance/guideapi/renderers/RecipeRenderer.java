package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.Gradient;
import amerifrance.guideapi.utils.MouseHelper;
import amerifrance.guideapi.utils.RenderStack;
import com.google.common.collect.Lists;
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

    private List<RenderStack> inputStacks;
    private RenderStack outputStack;

    public RecipeRenderer() {
        this.inputStacks = Lists.newArrayList();
    }

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        Area area = getArea(object, guideGui);
        Gradient.VERTICAL.draw(x, y, area.getWidth(), area.getHeight(), Color.WHITE.getRGB(), Color.YELLOW.getRGB());

        inputStacks.forEach(renderStack -> renderStack.render(guideGui, matrixStack));
        outputStack.render(guideGui, matrixStack);
    }

    @Override
    public void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
        inputStacks.stream()
                .filter(renderStack -> MouseHelper.isInRenderStack(renderStack, mouseX, mouseY))
                .forEach(renderStack -> renderStack.hover(guideGui, matrixStack, mouseX, mouseY));

        if (MouseHelper.isInRenderStack(outputStack, mouseX, mouseY))
            outputStack.hover(guideGui, matrixStack, mouseX, mouseY);
    }

    public void setOutputStack(RenderStack outputStack) {
        this.outputStack = outputStack;
    }

    public void clearInputStacks() {
        inputStacks.clear();
    }

    public void addRenderStack(RenderStack renderStack) {
        inputStacks.add(renderStack);
    }

    protected List<Recipe> getRecipes(RecipeType recipeType, Item output) {
        RecipeManager recipeManager = MinecraftClient.getInstance().world.getRecipeManager();
        return recipeManager.values().stream()
                .filter(recipe -> recipe.getType() == recipeType && recipe.getOutput().getItem() == output)
                .collect(Collectors.toList());
    }
}
