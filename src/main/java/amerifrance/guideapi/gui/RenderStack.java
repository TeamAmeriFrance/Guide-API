package amerifrance.guideapi.gui;

import amerifrance.guideapi.utils.Area;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RenderStack {

    public static final int DRAW_SIZE = 16;
    public static final int TOOLTIP_Y_OFFSET = 10;

    private final List<ItemStack> itemStacks;
    private final int scale;
    private final int x;
    private final int y;

    private int currentDisplayedIndex;

    public RenderStack(List<ItemStack> itemStacks, int scale, int x, int y) {
        this.itemStacks = itemStacks;
        this.scale = scale;
        this.x = x;
        this.y = y;

        if (itemStacks.size() == 0) {
            itemStacks.add(ItemStack.EMPTY);
        }
    }

    public RenderStack(List<ItemStack> itemStacks, int x, int y) {
        this(itemStacks, 1, x, y);
    }

    public RenderStack(ItemStack[] itemStacks, int scale, int x, int y) {
        this(Arrays.asList(itemStacks), scale, x, y);
    }

    public RenderStack(ItemStack[] itemStacks, int x, int y) {
        this(itemStacks, 1, x, y);
    }

    public RenderStack(ItemStack itemStack, int scale, int x, int y) {
        this(Collections.singletonList(itemStack), scale, x, y);
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
        if (itemStacks.size() > 1)
            currentDisplayedIndex = (int) ((System.currentTimeMillis() / 1000) % itemStacks.size());

        ItemStack itemStack = itemStacks.get(currentDisplayedIndex);
        if (itemStack != ItemStack.EMPTY) {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.scalef(getScale(), getScale(), getScale());

            DiffuseLighting.enable();

            renderItemStack(itemStack, x, y);

            DiffuseLighting.disable();
            GlStateManager.scalef(1F / getScale(), 1F / getScale(), 1F / getScale());
        }
    }

    public void hover(Screen screen, MatrixStack matrixStack, int mouseX, int mouseY) {
        ItemStack itemStack = itemStacks.get(currentDisplayedIndex);

        if (itemStack != ItemStack.EMPTY) {
            screen.renderTooltip(matrixStack, screen.getTooltipFromItem(itemStack), mouseX, mouseY + TOOLTIP_Y_OFFSET);
        }
    }

    public Area getArea() {
        return new Area(DRAW_SIZE * getScale(), DRAW_SIZE * getScale());
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

    private void renderItemStack(ItemStack itemStack, int x, int y) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        itemRenderer.renderInGui(itemStack, x / scale, y / scale);
        itemRenderer.renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, itemStack, x / scale, y / scale);
    }
}
