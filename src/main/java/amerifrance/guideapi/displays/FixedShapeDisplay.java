package amerifrance.guideapi.displays;

import amerifrance.guideapi.api.ParentOf;
import amerifrance.guideapi.api.RendererProvider;
import amerifrance.guideapi.api.TextProvider;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.RenderGuideObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class FixedShapeDisplay<T extends TextProvider & ParentOf<U>, U extends RendererProvider> extends PaginatedBaseDisplay<T, U> {

    private final List<String> shape;

    public FixedShapeDisplay(T object, List<String> shape) {
        super(object);

        this.shape = shape;
    }

    public FixedShapeDisplay(T object, String... shape) {
        this(object, Lists.newArrayList(shape));
    }

    @Override
    public Multimap<Integer, RenderGuideObject> computePagesAndPositions(GuideGui guideGui) {
        Multimap<Integer, RenderGuideObject> pages = ArrayListMultimap.create();

        int i = 0;
        List<U> children = object.getChildren();

        for (int page = 0; page < computePageCount(shape, children); page++) {
            final int rowCount = shape.size();
            final int rowHeight = GuideGui.GUI_HEIGHT / rowCount;
            int y = guideGui.getDrawStartHeight();

            for (String row : shape) {
                final int columnCount = row.length();
                final int columnWidth = GuideGui.GUI_WIDTH / columnCount;
                int x = guideGui.getLeft();

                for (char c : row.toCharArray()) {
                    if (!Character.isWhitespace(c)) {
                        U child = children.get(i);

                        child.getRenderer().init(guideGui, x, y);
                        pages.put(page, new RenderGuideObject(child, x, y));
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
