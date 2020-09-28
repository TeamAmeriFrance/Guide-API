package amerifrance.guideapi.api;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import net.minecraft.client.util.math.MatrixStack;

public interface Renderer<T> {
    default void init(T object, GuideGui guideGui, int x, int y) {
    }

    void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta);

    void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY);

    Area getArea(T object, GuideGui guideGui);
}
