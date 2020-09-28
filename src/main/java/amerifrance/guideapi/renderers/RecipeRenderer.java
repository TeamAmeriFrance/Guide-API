package amerifrance.guideapi.renderers;

import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.MouseHelper;
import amerifrance.guideapi.utils.RecipeWrapper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Collectors;

public abstract class RecipeRenderer<T> implements Renderer<T> {

    protected static final Identifier RECIPE_ELEMENTS = new Identifier("guideapi", "textures/gui/recipe_elements.png");

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
        renderRecipeBackground(object, guideGui, matrixStack, x, y + getDescriptionArea(guideGui).getHeight());

        drawRecipe(guideGui, matrixStack, getRecipePairToDraw());
        guideGui.drawCenteredString(matrixStack, recipeTypeDescription, x + getArea(object, guideGui).getWidth() / 2, y, 0);
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

    public abstract void renderRecipeBackground(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y);

    public abstract void initRecipe(T object, GuideGui guideGui, int x, int y);

    public abstract Area getRecipeArea(T object, GuideGui guideGui);

    public abstract RecipeWrapper getRecipePairToDraw();

    protected List<Recipe<?>> getRecipes(GuideGui guideGui, RecipeType<?> recipeType, Item output) {
        RecipeManager recipeManager = guideGui.getMinecraftClient().world.getRecipeManager();
        return recipeManager.values().stream()
                .filter(recipe -> recipe.getType() == recipeType && recipe.getOutput().getItem() == output)
                .collect(Collectors.toList());
    }

    private void drawRecipe(GuideGui guideGui, MatrixStack matrixStack, RecipeWrapper recipeWrapper) {
        recipeWrapper.getInputs().forEach(renderStack -> renderStack.render(guideGui, matrixStack));
        recipeWrapper.getOutput().render(guideGui, matrixStack);
    }

    private void hoverRecipe(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, RecipeWrapper recipeWrapper) {
        recipeWrapper.getInputs().stream()
                .filter(renderStack -> MouseHelper.isInRenderStack(renderStack, mouseX, mouseY))
                .forEach(renderStack -> renderStack.hover(guideGui, matrixStack, mouseX, mouseY));

        if (MouseHelper.isInRenderStack(recipeWrapper.getOutput(), mouseX, mouseY))
            recipeWrapper.getOutput().hover(guideGui, matrixStack, mouseX, mouseY);
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
            return "CAMPFIRE";
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
