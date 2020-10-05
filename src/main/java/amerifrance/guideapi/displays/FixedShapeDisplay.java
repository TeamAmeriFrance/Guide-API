package amerifrance.guideapi.displays;

import amerifrance.guideapi.api.ParentOf;
import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.api.RendererProvider;
import amerifrance.guideapi.api.TextProvider;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.RenderGuideObject;
import amerifrance.guideapi.utils.Area;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class FixedShapeDisplay<T extends TextProvider & ParentOf<U>, U extends RendererProvider> extends PaginatedBaseDisplay<T, U> {

    private boolean centerRenderers;
    private final List<String> shape;

    public FixedShapeDisplay(T object, boolean centerRenderers, List<String> shape) {
        super(object);
        this.centerRenderers = centerRenderers;
        this.shape = shape;
    }

    public FixedShapeDisplay(T object, List<String> shape) {
        this(object, false, shape);
    }

    public FixedShapeDisplay(T object, boolean centerRenderers, String... shape) {
        this(object, centerRenderers, Lists.newArrayList(shape));
    }

    public FixedShapeDisplay(T object, String... shape) {
        this(object, false, shape);
    }

    @Override
    public Multimap<Integer, RenderGuideObject> computePagesAndPositions(GuideGui guideGui) {
        Multimap<Integer, RenderGuideObject> pages = ArrayListMultimap.create();
        List<U> children = object.getChildren();

        int i = 0;
        for (int page = 0; page < computePageCount(shape, children); page++) {
            final int rowCount = shape.size();
            final int rowHeight = GuideGui.GUI_HEIGHT / rowCount;
            int y = guideGui.getDrawStartHeight() + rowHeight / 2;

            for (String row : shape) {
                final int columnCount = row.length();
                final int columnWidth = GuideGui.GUI_WIDTH / columnCount;
                int x = guideGui.getLeft() + columnWidth / 2;

                for (char c : row.toCharArray()) {
                    if (i >= children.size()) continue;

                    if (!Character.isWhitespace(c)) {
                        U child = children.get(i);

                        Renderer renderer = child.getRenderer();
                        renderer.init(guideGui, x, y);

                        Area area = renderer.getArea(guideGui);
                        int xPos = x - area.getWidth();
                        int yPos = y - area.getHeight();

                        if (centerRenderers) {
                            xPos = x - area.getWidth() / 2;
                            yPos = y - area.getHeight() / 2;
                        }

                        renderer.init(guideGui, xPos, yPos);
                        pages.put(page, new RenderGuideObject(child, xPos, yPos));

                        i++;
                    }

                    x += columnWidth;
                }

                y += rowHeight;
            }
        }

        return pages;
    }

    private int computePageCount(List<String> shape, List<U> children) {
        return (getItemsInPageCount(shape) % children.size()) + 1;
    }

    private int getItemsInPageCount(List<String> shape) {
        return shape.stream()
                .map(StringUtils::deleteWhitespace)
                .mapToInt(String::length)
                .sum();
    }
}
