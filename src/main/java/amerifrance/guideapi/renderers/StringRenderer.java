package amerifrance.guideapi.renderers;

import amerifrance.guideapi.api.MultipageProvider;
import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.utils.Area;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StringRenderer implements Renderer, MultipageProvider {

    private final String text;
    private final boolean underlineOnHover;

    private List<LiteralText> lines;

    public StringRenderer(String text, boolean underlineOnHover) {
        this.text = text;
        this.underlineOnHover = underlineOnHover;
    }

    public StringRenderer(String text) {
        this(text, false);
    }

    @Override
    public void init(GuideGui guideGui, int x, int y) {
        this.lines = guideGui.wrapLines(text, GuideGui.GUI_WIDTH).stream()
                .map(stringRenderable -> new LiteralText(stringRenderable.getString()))
                .collect(Collectors.toList());
    }

    @Override
    public void render(GuideGui guideGui, MatrixStack matrixStack, int x, int y, float delta) {
        int yPosition = y;
        for (LiteralText line : lines) {
            guideGui.drawString(matrixStack, line, x, yPosition, 0);

            yPosition += guideGui.getFontHeight();
        }
    }

    @Override
    public void hover(GuideGui guideGui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
        if (underlineOnHover) {
            lines.forEach(line -> line.formatted(Formatting.UNDERLINE));
            render(guideGui, matrixStack, x, y, 0);
            lines.forEach(line -> line.formatted(Formatting.RESET));
        }
    }

    @Override
    public Area getArea(GuideGui guideGui) {
        return new Area(GuideGui.GUI_WIDTH, guideGui.getFontHeight() * lines.size());
    }

    @Override
    public List<Renderer> split(GuideGui guideGui, int x, int y) {
        if (y + lines.size() * guideGui.getFontHeight() > guideGui.getDrawEndHeight()) {
            return lines.stream()
                    .map(line -> new StringRenderer(line.getString()))
                    .collect(Collectors.toList());
        }

        return Collections.singletonList(this);
    }
}
