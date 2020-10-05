package amerifrance.guideapi.displays;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.RenderGuideObject;
import amerifrance.guideapi.utils.Area;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collections;
import java.util.List;

public class LineDisplay<T extends TextProvider & ParentOf<U>, U extends RendererProvider> extends PaginatedBaseDisplay<T, U> {

    public LineDisplay(T object) {
        super(object);
    }

    @Override
    public Multimap<Integer, RenderGuideObject> computePagesAndPositions(GuideGui guideGui) {
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
