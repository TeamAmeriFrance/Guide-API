package amerifrance.guideapi.api;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import net.minecraft.client.util.math.MatrixStack;

public interface Renderer {
    default void init(GuideGui guideGui, int x, int y) {
    }

    void render(GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta);

    void hover(GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY);

    Area getArea(GuideGui guideGui);
}
