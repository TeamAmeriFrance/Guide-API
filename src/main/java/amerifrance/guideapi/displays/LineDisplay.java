package amerifrance.guideapi.displays;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.TextButton;
import amerifrance.guideapi.utils.Area;
import amerifrance.guideapi.utils.MouseHelper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Collections;
import java.util.List;

public class LineDisplay<T extends TextProvider & ParentOf<U>, U extends RendererProvider<U>> extends HistoryBaseDisplay {
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

        pages.clear();
        computePages(guideGui);

        //FIXME Lang for buttons
        this.previousButton = new TextButton(() -> currentPage--, "Previous", left, top + height);
        this.nextButton = new TextButton(() -> currentPage++, "Next", left + width, top + height);

        int x = guideGui.getLeft();

        pages.keySet().forEach(pageNumber -> {
            int y = guideGui.getDrawStartHeight();
            for (U object : pages.get(pageNumber)) {
                if (object.getViewingRequirement().canView()) {
                    object.getRenderer().init(object, guideGui, x, y);

                    y += object.getRenderer().getArea(object, guideGui).getHeight();
                }
            }
        });
    }

    @Override
    public void draw(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        super.draw(guideGui, matrixStack, mouseX, mouseY, delta);

        if (currentPage > 0)
            previousButton.draw(guideGui.getTextRenderer(), matrixStack, mouseX, mouseY);

        if (currentPage < pages.keySet().size() - 1)
            nextButton.draw(guideGui.getTextRenderer(), matrixStack, mouseX, mouseY);

        guideGui.drawCenteredString(matrixStack,
                object.getText(),
                guideGui.getLeft() + guideGui.getGuiWidth() / 2F,
                guideGui.getTop(),
                0);

        int x = guideGui.getLeft();
        int y = guideGui.getDrawStartHeight();

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
        if (currentPage > 0 && previousButton.mouseOver(guideGui.getTextRenderer(), mouseX, mouseY))
            return previousButton.click();

        if (currentPage < pages.keySet().size() - 1 && nextButton.mouseOver(guideGui.getTextRenderer(), mouseX, mouseY))
            return nextButton.click();

        return super.mouseClicked(guideGui, mouseX, mouseY, button);
    }

    private void computePages(GuideGui guideGui) {
        int page = 0;
        int y = guideGui.getDrawStartHeight();

        for (U child : object.getChildren()) {
            if (child.getViewingRequirement().canView()) {
                List<U> items = Collections.singletonList(child);

                if (child instanceof MultipageProvider<?>) {
                    MultipageProvider<U> multipageProvider = (MultipageProvider<U>) child;
                    items = multipageProvider.split(guideGui, guideGui.getLeft(), y);
                }

                for (U u : items) {
                    Area area = u.getRenderer().getArea(u, guideGui);

                    if (y + area.getHeight() > guideGui.getDrawEndHeight()) {
                        page++;
                        y = guideGui.getDrawStartHeight();
                    }

                    y += area.getHeight();
                    pages.put(page, u);
                }
            }
        }
    }
}
