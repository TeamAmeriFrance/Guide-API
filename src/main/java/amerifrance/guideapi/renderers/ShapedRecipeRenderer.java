package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public class ShapedRecipeRenderer<T> implements Renderer<T> {

    private static final int DRAW_SIZE = 15;
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/crafting_table.png");

    private final Item output;
    private ShapedRecipe shapedRecipe;

    public ShapedRecipeRenderer(Item output) {
        this.output = output;
    }

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        drawBackground(guideGui, matrixStack, x, y);

        if (shapedRecipe == null) {
            shapedRecipe = (ShapedRecipe) MinecraftClient.getInstance().world.getRecipeManager().get(Registry.ITEM.getId(output)).get();
        }

        DefaultedList<Ingredient> previewInputs = shapedRecipe.getPreviewInputs();
        for (int i = 0; i < previewInputs.size(); i++) {
            Ingredient ingredient = previewInputs.get(i);
            ItemStack[] input = ingredient.getMatchingStacksClient();

            if (input.length > 0) {
                int row = i / 3;
                int col = i % 3;

                DiffuseLighting.enable();
                guideGui.getMinecraftClient().getItemRenderer().renderGuiItemIcon(input[0], x + col * DRAW_SIZE, y + row * DRAW_SIZE);
                DiffuseLighting.disable();
            }
        }

        DiffuseLighting.enable();
        guideGui.getMinecraftClient().getItemRenderer().renderGuiItemIcon(shapedRecipe.getOutput(), x + 4 * DRAW_SIZE, y + 1 * DRAW_SIZE);
        guideGui.getMinecraftClient().getItemRenderer().renderGuiItemOverlay(guideGui.getTextRenderer(), shapedRecipe.getOutput(), x + 4 * DRAW_SIZE, y + 1 * DRAW_SIZE);
        DiffuseLighting.disable();
    }

    @Override
    public void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {

    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        if (shapedRecipe == null) {
            shapedRecipe = (ShapedRecipe) MinecraftClient.getInstance().world.getRecipeManager().get(Registry.ITEM.getId(output)).get();
        }

        return new Area(DRAW_SIZE * shapedRecipe.getWidth(), DRAW_SIZE * shapedRecipe.getHeight());
    }

    private void drawBackground(GuideGui guideGui, MatrixStack matrices, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
        guideGui.drawTexture(matrices, x, y, 0, 0, 176, 166);
    }
}
