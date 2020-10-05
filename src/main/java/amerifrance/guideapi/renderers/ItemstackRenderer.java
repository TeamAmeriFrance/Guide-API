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

    private static final int DEFAULT_SCALE = 2;
    private final ItemStack itemStack;
    private final int scale;

    private List<Text> hoverText;
    private RenderStack renderStack;

    public ItemstackRenderer(ItemStack itemStack, int scale) {
        this.itemStack = itemStack;
        this.renderStack = new RenderStack(itemStack, scale);
        this.scale = scale;
    }

    public ItemstackRenderer(ItemStack itemStack) {
        this(itemStack, DEFAULT_SCALE);
    }

    public ItemstackRenderer(Item item) {
        this(new ItemStack(item));
    }

    public ItemstackRenderer(ItemStack itemStack, String hoverText, int scale) {
        this(itemStack, scale);

        this.hoverText = Collections.singletonList(new LiteralText(hoverText));
    }

    public ItemstackRenderer(ItemStack itemStack, String hoverText) {
        this(itemStack, hoverText, DEFAULT_SCALE);
    }

    public ItemstackRenderer(Item item, String hoverText, int scale) {
        this(new ItemStack(item), hoverText, scale);
    }

    public ItemstackRenderer(Item item, String hoverText) {
        this(item, hoverText, DEFAULT_SCALE);
    }

    @Override
    public void init(GuideGui guideGui, int x, int y) {
        if (hoverText == null) hoverText = guideGui.getTooltipFromItem(itemStack);
        renderStack = new RenderStack(itemStack, scale, x, y);
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
