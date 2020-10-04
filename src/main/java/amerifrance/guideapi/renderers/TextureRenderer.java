package amerifrance.guideapi.renderers;

import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.RenderElement;
import amerifrance.guideapi.utils.Area;
import net.minecraft.client.util.math.MatrixStack;

public class TextureRenderer implements Renderer {

    private final RenderElement renderElement;

    public TextureRenderer(RenderElement renderElement) {
        this.renderElement = renderElement;
    }

    @Override
    public void render(GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        renderElement.render(guideGui, matrixStack, guideGui.getMinecraftClient().getTextureManager(), x, y);
    }

    @Override
    public void hover(GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
    }

    @Override
    public Area getArea(GuideGui guideGui) {
        return renderElement.getArea();
    }
}
