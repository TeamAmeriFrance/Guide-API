package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.api.IdTextProvider;
import amerifrance.guideapi.utils.Area;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;

public class ItemstackRenderer<T extends IdTextProvider> implements Renderer<T> {

    private ItemStack itemStack;
    private static final int DRAW_SIZE = 30;

    public ItemstackRenderer(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemstackRenderer(Item item) {
        this.itemStack = new ItemStack(item);
    }

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.scalef(2.0F, 2.0F, 2.0F);

        DiffuseLighting.enable();
        guideGui.getMinecraftClient().getItemRenderer().renderGuiItemIcon(itemStack, x / 2, y / 2); // Scaled up by 2x, so half the position
        DiffuseLighting.disable();
        GlStateManager.scalef(0.5F, 0.5F, 0.5F);
    }

    @Override
    public void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
        guideGui.renderTooltip(matrixStack, new LiteralText(object.getText()), mouseX, mouseY + 10);
    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        return new Area(DRAW_SIZE, DRAW_SIZE);
    }
}
