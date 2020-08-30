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

    protected final RecipeType<?> recipeType;
    protected final String recipeTypeDescription;

    public RecipeRenderer(RecipeType<?> recipeType) {
        this.recipeType = recipeType;
        this.recipeTypeDescription = getRecipeTypeDescription(recipeType);
    }

    @Override
    public void init(T object, GuideGui guideGui, int x, int y) {
        initRecipe(object, guideGui, x, y + getDescriptionArea(guideGui).getHeight());
    }

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        Area area = getArea(object, guideGui);

        Gradient.VERTICAL.draw(x, y, area.getWidth(), area.getHeight(), Color.WHITE.getRGB(), Color.YELLOW.getRGB());
        drawRecipe(guideGui, matrixStack, getRecipePairToDraw());
        guideGui.getTextRenderer().draw(matrixStack, recipeTypeDescription, x, y, 0);
    }

    @Override
    public void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
        hoverRecipe(guideGui, matrixStack, mouseX, mouseY, getRecipePairToDraw());
    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        Area descriptionArea = getDescriptionArea(guideGui);
        Area recipeArea = getRecipeArea(object, guideGui);

        return new Area(Math.max(descriptionArea.getWidth(), recipeArea.getWidth()),
                descriptionArea.getHeight() + recipeArea.getHeight());
    }

    public abstract void initRecipe(T object, GuideGui guideGui, int x, int y);

    public abstract Area getRecipeArea(T object, GuideGui guideGui);

    public abstract RecipePair getRecipePairToDraw();

    protected List<Recipe<?>> getRecipes(RecipeType<?> recipeType, Item output) {
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

    //FIXME LANG FILE
    private String getRecipeTypeDescription(RecipeType<?> recipeType) {
        if (recipeType == RecipeType.CRAFTING)
            return "CRAFTING";
        if (recipeType == RecipeType.SMELTING)
            return "SMELTING";
        if (recipeType == RecipeType.BLASTING)
            return "BLASTING";
        if (recipeType == RecipeType.SMOKING)
            return "SMOKING";
        if (recipeType == RecipeType.CAMPFIRE_COOKING)
            return "CAMPFIRE COOKING";
        if (recipeType == RecipeType.STONECUTTING)
            return "STONECUTTING";
        if (recipeType == RecipeType.SMITHING)
            return "SMITHING";

        return "";
    }

    private Area getDescriptionArea(GuideGui guideGui) {
        return new Area(guideGui.getTextRenderer().getWidth(recipeTypeDescription), (int) (1.5F * guideGui.getFontHeight()));
    }
}
