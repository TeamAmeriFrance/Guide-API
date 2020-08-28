package amerifrance.guideapi.renderers;

import amerifrance.guideapi.api.IdTextProvider;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.RenderStack;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;

public class ItemstackRenderer<T extends IdTextProvider> implements Renderer<T> {

    private final RenderStack renderStack;

    public ItemstackRenderer(ItemStack itemStack) {
        this.renderStack = new RenderStack(itemStack, 2);
    }

    public ItemstackRenderer(Item item) {
        this.renderStack = new RenderStack(item, 2);
    }

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        renderStack.render(guideGui, matrixStack, x, y);
    }

    @Override
    public void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
        guideGui.renderTooltip(matrixStack, new LiteralText(object.getText()), mouseX, mouseY + RenderStack.TOOLTIP_Y_OFFSET);
    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        return renderStack.getArea();
    }
}
