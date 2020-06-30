package amerifrance.guideapi.renderers;

import amerifrance.guideapi.gui.GraphicalElement;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.util.math.MatrixStack;

public class TextureRenderer<T> implements Renderer<T> {

    private final GraphicalElement graphicalElement;

    public TextureRenderer(GraphicalElement graphicalElement) {
        this.graphicalElement = graphicalElement;
    }

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        graphicalElement.draw(1.0D, x, y);
    }

    @Override
    public void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        return new Area(graphicalElement.getArea().width, graphicalElement.getArea().height);
    }
}
