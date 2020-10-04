package amerifrance.guideapi.renderers;

import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.RenderStack;
import amerifrance.guideapi.utils.Area;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.Collections;
import java.util.List;

public class ItemstackRenderer implements Renderer {

    private final ItemStack itemStack;

    private List<Text> hoverText;
    private RenderStack renderStack;

    public ItemstackRenderer(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.renderStack = new RenderStack(itemStack, 2);
    }

    public ItemstackRenderer(Item item) {
        this(new ItemStack(item));
    }

    public ItemstackRenderer(ItemStack itemStack, String hoverText) {
        this(itemStack);

        this.hoverText = Collections.singletonList(new LiteralText(hoverText));
    }

    public ItemstackRenderer(Item item, String hoverText) {
        this(new ItemStack(item), hoverText);
    }

    @Override
    public void init(GuideGui guideGui, int x, int y) {
        if (hoverText == null) hoverText = guideGui.getTooltipFromItem(itemStack);
        renderStack = new RenderStack(itemStack, 2, x, y);
    }

    @Override
    public void render(GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        renderStack.render(guideGui, matrixStack);
    }

    @Override
    public void hover(GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
        guideGui.renderTooltip(matrixStack, hoverText, mouseX, mouseY + RenderStack.TOOLTIP_Y_OFFSET);
    }

    @Override
    public Area getArea(GuideGui guideGui) {
        return renderStack.getArea();
    }
}
