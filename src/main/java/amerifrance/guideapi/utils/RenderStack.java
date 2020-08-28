package amerifrance.guideapi.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RenderStack {

    public final static int DRAW_SIZE = 15;
    public final static int TOOLTIP_Y_OFFSET = 10;

    private final ItemStack itemStack;
    private final int scale;

    public RenderStack(ItemStack itemStack, int scale) {
        this.itemStack = itemStack;
        this.scale = scale;
    }

    public RenderStack(ItemStack itemStack) {
        this(itemStack, 1);
    }

    public RenderStack(Item item, int scale) {
        this(new ItemStack(item), scale);
    }

    public RenderStack(Item item) {
        this(item, 1);
    }

    public void render(Screen screen, MatrixStack matrixStack, int x, int y) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.scalef(scale, scale, scale);

        DiffuseLighting.enable();

        renderItemStack(x, y);

        DiffuseLighting.disable();
        GlStateManager.scalef(1F / scale, 1F / scale, 1F / scale);
    }

    public void hover(Screen screen, MatrixStack matrixStack, int mouseX, int mouseY) {
        renderTooltip(screen, matrixStack, mouseX, mouseY);
    }

    public Area getArea() {
        return new Area(DRAW_SIZE * scale, DRAW_SIZE * scale);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getScale() {
        return scale;
    }

    private void renderItemStack(int x, int y) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        itemRenderer.renderInGui(itemStack, x / scale, y / scale);
        itemRenderer.renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, itemStack, x / scale, y / scale);
    }

    private void renderTooltip(Screen screen, MatrixStack matrixStack, int x, int y) {
        screen.renderTooltip(matrixStack, screen.getTooltipFromItem(itemStack), x, y + TOOLTIP_Y_OFFSET);
    }
}
