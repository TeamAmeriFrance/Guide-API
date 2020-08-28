package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.MouseHelper;
import amerifrance.guideapi.utils.RenderStack;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class ShapedRecipeRenderer<T> implements Renderer<T> {

    private static final Identifier TEXTURE = new Identifier("textures/gui/container/crafting_table.png");

    private final Item output;

    private ShapedRecipe shapedRecipe;
    private List<RenderStack> inputStacks;
    private RenderStack outputStack;

    public ShapedRecipeRenderer(Item output) {
        this.output = output;
        this.inputStacks = Lists.newArrayList();
    }

    @Override
    public void init(T object, GuideGui guideGui, int x, int y) {
        inputStacks.clear();

        shapedRecipe = (ShapedRecipe) MinecraftClient.getInstance().world.getRecipeManager().get(Registry.ITEM.getId(output)).get();

        DefaultedList<Ingredient> previewInputs = shapedRecipe.getPreviewInputs();
        for (int i = 0; i < previewInputs.size(); i++) {
            Ingredient ingredient = previewInputs.get(i);
            ItemStack[] input = ingredient.getMatchingStacksClient();

            int xPos = x + (i % 3) * RenderStack.DRAW_SIZE;
            int yPos = y + (i / 3) * RenderStack.DRAW_SIZE;

            if (input.length > 0) {
                inputStacks.add(new RenderStack(input[0], xPos, yPos));
            } else {
                inputStacks.add(new RenderStack(ItemStack.EMPTY, xPos, yPos));
            }
        }

        int outputX = x + 4 * RenderStack.DRAW_SIZE;
        int outputY = y + 1 * RenderStack.DRAW_SIZE;
        outputStack = new RenderStack(shapedRecipe.getOutput(), outputX, outputY);
    }

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
//        drawBackground(guideGui, matrixStack, x, y);

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

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        return new Area(RenderStack.DRAW_SIZE * 5, RenderStack.DRAW_SIZE * 3);
    }

    private void drawBackground(GuideGui guideGui, MatrixStack matrices, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
        guideGui.drawTexture(matrices, x, y, 0, 0, 176, 166);
    }
}
