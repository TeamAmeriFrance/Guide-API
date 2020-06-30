package amerifrance.guideapi.test;

import amerifrance.guideapi.guide.Element;
import amerifrance.guideapi.guide.Page;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.MouseHelper;
import amerifrance.guideapi.displays.HistoryBaseDisplay;
import amerifrance.guideapi.gui.GuideGui;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

//TODO This is a test page. Code is not clean. Do not keep this for release.
public class TestPageDisplay extends HistoryBaseDisplay {
    private final Page page;

    public TestPageDisplay(Page page) {
        this.page = page;
    }

    @Override
    public void draw(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        super.draw(guideGui, matrixStack, mouseX, mouseY, delta);

        TextRenderer textRenderer = guideGui.getTextRenderer();

        textRenderer.draw(matrixStack,
                page.getText(),
                guideGui.getLeft() + guideGui.getGuiWidth() / 2 - textRenderer.getWidth(page.getText()) / 2,
                guideGui.getTop(),
                0);

        int x = guideGui.getLeft();
        int y = guideGui.getTop() + textRenderer.fontHeight * 2;
        int lowestY = y;

        for (Element element : page.getChildren()) {
            if (element.getViewingRequirement().canView()) {
                Area area = element.getRenderer().getArea(element, guideGui);

                if (x + area.getWidth() >= guideGui.getLeft() + guideGui.getGuiWidth()) {
                    x = guideGui.getLeft();
                }

                element.getRenderer().render(element, guideGui, matrixStack, x, y, delta);

                if (MouseHelper.isInRect(x, y, area.getWidth(), area.getHeight(), mouseX, mouseY)) {
                    element.getRenderer().hover(element, guideGui, matrixStack, x, y, mouseX, mouseY);
                }

                x += area.getWidth();
                if (y + area.getHeight() > lowestY) {
                    y += area.getHeight();
                }
            }
        }
    }
}
