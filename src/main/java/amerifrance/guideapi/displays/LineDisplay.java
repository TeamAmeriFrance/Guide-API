package amerifrance.guideapi.displays;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.RenderGuideObject;
import amerifrance.guideapi.gui.TextButton;
import amerifrance.guideapi.utils.Area;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Collections;
import java.util.List;

public class LineDisplay<T extends TextProvider & ParentOf<U>, U extends RendererProvider<U>> extends HistoryBaseDisplay {
    private final T object;

    private int currentPage;
    private Button previousButton;
    private Button nextButton;
    private Multimap<Integer, RenderGuideObject<U>> pages;

    public LineDisplay(T object) {
        this.object = object;
    }

    @Override
    public void init(GuideGui guideGui, int top, int left, int width, int height) {
        super.init(guideGui, top, left, width, height);

        pages = computePagesAndPositions(guideGui);

        //FIXME Lang for buttons or icons
        this.previousButton = new TextButton(() -> currentPage--, "Previous", left, top + height);
        this.nextButton = new TextButton(() -> currentPage++, "Next", left + width, top + height);
    }

    @Override
    public void draw(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        super.draw(guideGui, matrixStack, mouseX, mouseY, delta);

        if (currentPage > 0)
            previousButton.draw(guideGui.getTextRenderer(), matrixStack, mouseX, mouseY);

        if (currentPage < pages.keySet().size() - 1)
            nextButton.draw(guideGui.getTextRenderer(), matrixStack, mouseX, mouseY);

        guideGui.drawCenteredString(matrixStack, object.getText(), guideGui.getLeft() + GuideGui.GUI_WIDTH / 2F, guideGui.getTop(), 0);

        pages.get(currentPage).forEach(renderGuideObject -> renderGuideObject.render(guideGui, matrixStack, delta));

        for (RenderGuideObject<U> renderGuideObject : pages.get(currentPage)) {
            if (renderGuideObject.isHovering(guideGui, mouseX, mouseY)) {
                renderGuideObject.hover(guideGui, matrixStack, mouseX, mouseY);

                if (renderGuideObject.getObject() instanceof DisplayProvider)
                    hovered = (DisplayProvider) renderGuideObject.getObject();
            }
        }
    }

    @Override
    public boolean mouseClicked(GuideGui guideGui, double mouseX, double mouseY, int button) {
        if (currentPage > 0 && previousButton.mouseOver(guideGui.getTextRenderer(), mouseX, mouseY))
            return previousButton.click();

        if (currentPage < pages.keySet().size() - 1 && nextButton.mouseOver(guideGui.getTextRenderer(), mouseX, mouseY))
            return nextButton.click();

        return super.mouseClicked(guideGui, mouseX, mouseY, button);
    }

    private Multimap<Integer, RenderGuideObject<U>> computePagesAndPositions(GuideGui guideGui) {
        int page = 0;
        final int x = guideGui.getLeft();
        int y = guideGui.getDrawStartHeight();
        Multimap<Integer, RenderGuideObject<U>> pages = ArrayListMultimap.create();

        for (U child : object.getChildren()) {
            if (child.getViewingRequirement().canView()) {
                for (U u : splitChildIfMultipage(guideGui, child, y)) {
                    Area area = u.getRenderer().getArea(u, guideGui);
                    if (y + area.getHeight() > guideGui.getDrawEndHeight()) {
                        page++;
                        y = guideGui.getDrawStartHeight();
                    }

                    u.getRenderer().init(u, guideGui, x, y);
                    pages.put(page, new RenderGuideObject<>(u, x, y));

                    y += area.getHeight();
                }
            }
        }

        return pages;
    }

    private List<U> splitChildIfMultipage(GuideGui guideGui, U child, int y) {
        if (child instanceof MultipageProvider<?>) {
            return ((MultipageProvider<U>) child).split(guideGui, guideGui.getLeft(), y);
        }

        return Collections.singletonList(child);
    }
}
