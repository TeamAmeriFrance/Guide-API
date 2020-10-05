package amerifrance.guideapi.renderers;

import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import net.minecraft.client.util.math.MatrixStack;

public class PageBreakRenderer implements Renderer {

    private Area area;

    @Override
    public void init(GuideGui guideGui, int x, int y) {
        this.area = new Area(GuideGui.GUI_WIDTH, guideGui.getDrawEndHeight() - y);
    }

    @Override
    public void render(GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
    }

    @Override
    public void hover(GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
    }

    @Override
    public Area getArea(GuideGui guideGui) {
        return area;
    }
}
