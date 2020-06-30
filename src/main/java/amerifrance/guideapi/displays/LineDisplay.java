package amerifrance.guideapi.displays;

import amerifrance.guideapi.api.IdTextProvider;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.MouseHelper;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.api.DisplayProvider;
import amerifrance.guideapi.api.ParentOf;
import amerifrance.guideapi.api.RendererProvider;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class LineDisplay<T extends IdTextProvider & ParentOf<U>, U extends RendererProvider<U> & DisplayProvider> extends HistoryBaseDisplay {

    private final T object;

    public LineDisplay(T object) {
        this.object = object;
    }

    @Override
    public void draw(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        super.draw(guideGui, matrixStack, mouseX, mouseY, delta);

        TextRenderer textRenderer = guideGui.getTextRenderer();

        textRenderer.draw(matrixStack,
                object.getText(),
                guideGui.getLeft() + guideGui.getGuiWidth() / 2 - textRenderer.getWidth(object.getText()) / 2,
                guideGui.getTop(),
                0);

        int x = guideGui.getLeft();
        int y = guideGui.getTop() + textRenderer.fontHeight * 2;

        for (U object : object.getChildren()) {
            if (object.getViewingRequirement().canView()) {
                object.getRenderer().render(object, guideGui, matrixStack, x, y, delta);

                Area area = object.getRenderer().getArea(object, guideGui);
                if (MouseHelper.isInRect(x, y, area.getWidth(), area.getHeight(), mouseX, mouseY)) {
                    object.getRenderer().hover(object, guideGui, matrixStack, x, y, mouseX, mouseY);

                    hovered = object;
                }

                y += area.getHeight();
            }
        }
    }
}
