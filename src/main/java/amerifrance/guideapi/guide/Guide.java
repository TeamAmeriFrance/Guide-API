package amerifrance.guideapi.guide;

import amerifrance.guideapi.api.IdProvider;
import amerifrance.guideapi.displays.Display;
import amerifrance.guideapi.api.DisplayProvider;
import amerifrance.guideapi.api.TextProvider;
import amerifrance.guideapi.api.ParentOf;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.google.common.collect.Maps.newLinkedHashMap;

public class Guide implements IdProvider, TextProvider, ParentOf<Category>, DisplayProvider {

    private String id;
    private String name;
    private Display display;
    private Map<String, Category> categories;

    public Guide(String id, String name, Consumer<Guide> $) {
        this.id = id;
        this.name = name;
        this.categories = newLinkedHashMap();

        $.accept(this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return name;
    }

    @Override
    public List<Category> getChildren() {
        return Lists.newArrayList(categories.values());
    }

    @Override
    public void add(Category child) {
        categories.put(child.getId(), child);
    }

    @Override
    public Category getChild(String id) {
        return categories.getOrDefault(id, null);
    }

    @Override
    public Display getDisplay() {
        return display;
    }

    @Override
    public void setDisplay(Display display) {
        this.display = display;
    }
}
