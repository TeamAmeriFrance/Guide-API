package amerifrance.guideapi.renderers;

import amerifrance.guideapi.api.TextProvider;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringRenderable;

import java.util.List;

public class StringRenderer<T extends TextProvider> implements Renderer<T> {

    private static final int HOVER_COLOR = 0xffffff;

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        List<StringRenderable> lines = guideGui.wrapLines(object.getText(), GuideGui.GUI_WIDTH);

        int yPosition = y;
        for (StringRenderable line : lines) {
            guideGui.drawString(matrixStack, line, x, yPosition, 0);

            yPosition += guideGui.getFontHeight();
        }
    }

    @Override
    public void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
        List<StringRenderable> lines = guideGui.wrapLines(object.getText(), GuideGui.GUI_WIDTH);

        int yPosition = y;
        for (StringRenderable line : lines) {
            guideGui.drawString(matrixStack, line, x, yPosition, HOVER_COLOR);

            yPosition += guideGui.getFontHeight();
        }
    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        List<StringRenderable> lines = guideGui.wrapLines(object.getText(), GuideGui.GUI_WIDTH);

        return new Area(guideGui.getStringWidth(lines.get(0)), guideGui.getFontHeight() * lines.size());
    }
}
