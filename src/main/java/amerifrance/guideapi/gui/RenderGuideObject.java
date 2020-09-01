package amerifrance.guideapi.gui;

import amerifrance.guideapi.api.RendererProvider;
import amerifrance.guideapi.utils.MouseHelper;
import net.minecraft.client.util.math.MatrixStack;

public class RenderGuideObject<T extends RendererProvider<T>> {

    private final T object;
    private final int x;
    private final int y;

    public RenderGuideObject(T object, int x, int y) {
        this.object = object;
        this.x = x;
        this.y = y;
    }

    public T getObject() {
        return object;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void render(GuideGui guideGui, MatrixStack matrixStack, float delta) {
        object.getRenderer().render(object, guideGui, matrixStack, x, y, delta);
    }

    public void hover(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY) {
        object.getRenderer().hover(object, guideGui, matrixStack, x, y, mouseX, mouseY);
    }

    public boolean isHovering(GuideGui guideGui, int mouseX, int mouseY) {
        return MouseHelper.isInArea(x, y, object.getRenderer().getArea(object, guideGui), mouseX, mouseY);
    }
}
