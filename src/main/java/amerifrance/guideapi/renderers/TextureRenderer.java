package amerifrance.guideapi.renderers;

import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.RenderElement;
import amerifrance.guideapi.utils.Area;
import net.minecraft.client.util.math.MatrixStack;

public class TextureRenderer<T> implements Renderer<T> {

    private final RenderElement renderElement;

    public TextureRenderer(RenderElement renderElement) {
        this.renderElement = renderElement;
    }

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        renderElement.render(guideGui, matrixStack, guideGui.getMinecraftClient().getTextureManager(), x, y);
    }

    @Override
    public void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        return renderElement.getArea();
    }
}
