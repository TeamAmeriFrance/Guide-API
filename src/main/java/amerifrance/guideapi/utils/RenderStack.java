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

    public static final int DRAW_SIZE = 15;
    public static final int TOOLTIP_Y_OFFSET = 10;

    private final ItemStack itemStack;
    private final int scale;
    private final int x;
    private final int y;

    public RenderStack(ItemStack itemStack, int scale, int x, int y) {
        this.itemStack = itemStack;
        this.scale = scale;
        this.x = x;
        this.y = y;
    }

    public RenderStack(ItemStack itemStack, int x, int y) {
        this(itemStack, 1, x, y);
    }

    public RenderStack(Item item, int scale, int x, int y) {
        this(new ItemStack(item), scale, x, y);
    }

    public RenderStack(Item item, int x, int y) {
        this(item, 1, x, y);
    }

    public RenderStack(ItemStack itemStack, int scale) {
        this(itemStack, scale, 0, 0);
    }

    public RenderStack(ItemStack itemStack) {
        this(itemStack, 0, 0);
    }

    public RenderStack(Item item, int scale) {
        this(item, scale, 0, 0);
    }

    public RenderStack(Item item) {
        this(item, 0, 0);
    }

    public void render(Screen screen, MatrixStack matrixStack) {
        if (itemStack != ItemStack.EMPTY) {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.scalef(scale, scale, scale);

            DiffuseLighting.enable();

            renderItemStack(x, y);

            DiffuseLighting.disable();
            GlStateManager.scalef(1F / scale, 1F / scale, 1F / scale);
        }
    }

    public void hover(Screen screen, MatrixStack matrixStack, int mouseX, int mouseY) {
        if (itemStack != ItemStack.EMPTY) {
            screen.renderTooltip(matrixStack, screen.getTooltipFromItem(itemStack), mouseX, mouseY + TOOLTIP_Y_OFFSET);
        }
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void renderItemStack(int x, int y) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        itemRenderer.renderInGui(itemStack, x / scale, y / scale);
        itemRenderer.renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, itemStack, x / scale, y / scale);
    }

}
