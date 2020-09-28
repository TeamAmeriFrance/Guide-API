package amerifrance.guideapi.renderers;

import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import net.minecraft.client.util.math.MatrixStack;

public class LineBreakRenderer<T> implements Renderer<T> {

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
    }

    @Override
    public void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        return new Area(GuideGui.GUI_WIDTH, guideGui.getFontHeight());
    }
}
