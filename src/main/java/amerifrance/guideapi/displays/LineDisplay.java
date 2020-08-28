package amerifrance.guideapi.displays;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.TextButton;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.MouseHelper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class LineDisplay<T extends IdTextProvider & ParentOf<U>, U extends RendererProvider<U>> extends HistoryBaseDisplay {
    private final T object;
    private final Multimap<Integer, U> pages;

    private int currentPage;

    private Button previousButton;
    private Button nextButton;

    public LineDisplay(T object) {
        this.object = object;
        this.pages = ArrayListMultimap.create();
    }

    @Override
    public void init(GuideGui guideGui, int top, int left, int width, int height) {
        super.init(guideGui, top, left, width, height);

        this.pages.clear();
        this.computePages(guideGui);

        this.previousButton = new TextButton(() -> currentPage--, "Previous", left, top + height);
        this.nextButton = new TextButton(() -> currentPage++, "Next", left + width, top + height);
    }

    @Override
    public void draw(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        super.draw(guideGui, matrixStack, mouseX, mouseY, delta);

        TextRenderer textRenderer = guideGui.getTextRenderer();

        if (currentPage > 0)
            previousButton.draw(textRenderer, matrixStack, mouseX, mouseY);

        if (currentPage < pages.keySet().size() - 1)
            nextButton.draw(textRenderer, matrixStack, mouseX, mouseY);

        textRenderer.draw(matrixStack,
                object.getText(),
                guideGui.getLeft() + guideGui.getGuiWidth() / 2 - textRenderer.getWidth(object.getText()) / 2,
                guideGui.getTop(),
                0);

        int x = guideGui.getLeft();
        int y = guideGui.getTop() + textRenderer.fontHeight * 2;

        for (U object : pages.get(currentPage)) {
            if (object.getViewingRequirement().canView()) {
                object.getRenderer().render(object, guideGui, matrixStack, x, y, delta);

                Area area = object.getRenderer().getArea(object, guideGui);
                if (MouseHelper.isInArea(x, y, area, mouseX, mouseY)) {
                    object.getRenderer().hover(object, guideGui, matrixStack, x, y, mouseX, mouseY);

                    if (object instanceof DisplayProvider)
                        hovered = (DisplayProvider) object;
                }

                y += area.getHeight();
            }
        }
    }

    @Override
    public boolean mouseClicked(GuideGui guideGui, double mouseX, double mouseY, int button) {
        TextRenderer textRenderer = guideGui.getTextRenderer();

        if (currentPage > 0 && previousButton.mouseOver(textRenderer, mouseX, mouseY)) {
            return previousButton.click();
        }
        if (currentPage < pages.keySet().size() - 1 && nextButton.mouseOver(textRenderer, mouseX, mouseY)) {
            return nextButton.click();
        }

        return super.mouseClicked(guideGui, mouseX, mouseY, button);
    }

    private void computePages(GuideGui guideGui) {
        int page = 0;
        int y = guideGui.getTop() + guideGui.getTextRenderer().fontHeight * 2;

        for (U object : object.getChildren()) {
            if (object.getViewingRequirement().canView()) {
                Area area = object.getRenderer().getArea(object, guideGui);

                if (y + area.getHeight() > guideGui.getGuiHeight()) {
                    page++;
                    y = guideGui.getTop() + guideGui.getTextRenderer().fontHeight * 2;
                }

                y += area.getHeight();
                pages.put(page, object);
            }
        }
    }
}
