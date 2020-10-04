package amerifrance.guideapi.displays;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.ImageButton;
import amerifrance.guideapi.gui.RenderElement;
import amerifrance.guideapi.gui.RenderGuideObject;
import amerifrance.guideapi.utils.Area;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;

public class LineDisplay<T extends TextProvider & ParentOf<U>, U extends RendererProvider> extends HistoryBaseDisplay {

    private static final RenderElement PREVIOUS_BUTTON_RENDER = new RenderElement(
            new Identifier("guideapi", "textures/gui/background.png"),
            0, 225, 18, 10);

    private static final RenderElement PREVIOUS_BUTTON_HOVER_RENDER = new RenderElement(
            new Identifier("guideapi", "textures/gui/background.png"),
            18, 225, 18, 10);

    private static final RenderElement NEXT_BUTTON_RENDER = new RenderElement(
            new Identifier("guideapi", "textures/gui/background.png"),
            36, 225, 18, 10);

    private static final RenderElement NEXT_BUTTON_HOVER_RENDER = new RenderElement(
            new Identifier("guideapi", "textures/gui/background.png"),
            54, 225, 18, 10);

    private final T object;

    private int currentPage;
    private Button previousButton;
    private Button nextButton;
    private Multimap<Integer, RenderGuideObject> pages;

    public LineDisplay(T object) {
        this.object = object;
    }

    @Override
    public void init(GuideGui guideGui, int top, int left, int width, int height) {
        super.init(guideGui, top, left, width, height);

        pages = computePagesAndPositions(guideGui);

        this.previousButton = new ImageButton(() -> currentPage--,
                guideGui,
                PREVIOUS_BUTTON_RENDER, PREVIOUS_BUTTON_HOVER_RENDER,
                left, guideGui.getDrawEndHeight());
        this.nextButton = new ImageButton(() -> currentPage++,
                guideGui,
                NEXT_BUTTON_RENDER, NEXT_BUTTON_HOVER_RENDER,
                left + width - NEXT_BUTTON_RENDER.getArea().getWidth(), guideGui.getDrawEndHeight());
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

        for (RenderGuideObject renderGuideObject : pages.get(currentPage)) {
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

    private Multimap<Integer, RenderGuideObject> computePagesAndPositions(GuideGui guideGui) {
        int page = 0;
        final int x = guideGui.getLeft();
        int y = guideGui.getDrawStartHeight();
        Multimap<Integer, RenderGuideObject> pages = ArrayListMultimap.create();

        for (U child : object.getChildren()) {
            if (child.getViewingRequirement().canView()) {
                child.getRenderer().init(guideGui, x, y);

                for (Renderer renderer : splitChildIfMultipage(guideGui, child, y)) {
                    renderer.init(guideGui, x, y);
                    Area area = renderer.getArea(guideGui);

                    if (y + area.getHeight() > guideGui.getDrawEndHeight()) {
                        page++;
                        y = guideGui.getDrawStartHeight();
                    }

                    renderer.init(guideGui, x, y);
                    pages.put(page, new RenderGuideObject(child, renderer, x, y));

                    y += area.getHeight();
                }
            }
        }

        return pages;
    }

    private List<Renderer> splitChildIfMultipage(GuideGui guideGui, U child, int y) {
        if (child.getRenderer() instanceof MultipageProvider) {
            return ((MultipageProvider) child.getRenderer()).split(guideGui, guideGui.getLeft(), y);
        }

        return Collections.singletonList(child.getRenderer());
    }
}
