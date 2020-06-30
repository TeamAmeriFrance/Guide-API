package amerifrance.guideapi.renderers;

import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.gui.GuideGui;
import net.minecraft.client.util.math.MatrixStack;

public interface Renderer<T> {
    void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta);

    void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY);

    Area getArea(T object, GuideGui guideGui);
}
