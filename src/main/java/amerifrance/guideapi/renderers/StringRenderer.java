package amerifrance.guideapi.renderers;

import amerifrance.guideapi.api.TextProvider;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringRenderable;

import java.util.List;

public class StringRenderer<T extends TextProvider> implements Renderer<T> {

    private static final int HOVER_COLOR = 0xffffff;

    @Override
    public void render(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        List<StringRenderable> lines = guideGui.getTextRenderer().wrapLines(new LiteralText(object.getText()), guideGui.getGuiWidth());

        int yPosition = y;
        for (StringRenderable line : lines) {
            guideGui.getTextRenderer().draw(matrixStack, line, x, yPosition, 0);

            yPosition += guideGui.getTextRenderer().fontHeight;
        }
    }

    @Override
    public void hover(T object, GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
        List<StringRenderable> lines = guideGui.getTextRenderer().wrapLines(new LiteralText(object.getText()), guideGui.getGuiWidth());

        int yPosition = y;
        for (StringRenderable line : lines) {
            guideGui.getTextRenderer().draw(matrixStack, line, x, yPosition, HOVER_COLOR);

            yPosition += guideGui.getTextRenderer().fontHeight;
        }
    }

    @Override
    public Area getArea(T object, GuideGui guideGui) {
        TextRenderer textRenderer = guideGui.getTextRenderer();

        List<StringRenderable> lines = textRenderer.wrapLines(new LiteralText(object.getText()), guideGui.getGuiWidth());

        return new Area(textRenderer.getWidth(lines.get(0)), textRenderer.fontHeight * lines.size());
    }
}
