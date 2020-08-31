package amerifrance.guideapi.guide;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.displays.Display;
import amerifrance.guideapi.renderers.Renderer;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.google.common.collect.Maps.newLinkedHashMap;

public class Category implements IdProvider, TextProvider, ChildOf<Guide>, ParentOf<Entry>, DisplayProvider, RendererProvider<Category> {

    private final String id;
    private final String name;
    private final Guide guide;
    private final Renderer<Category> renderer;
    private final Map<String, Entry> entries;

    private Display display;

    public Category(String id, String name, Guide guide, Renderer<Category> renderer, Consumer<Category> $) {
        this.id = id;
        this.name = name;
        this.guide = guide;
        this.renderer = renderer;
        this.entries = newLinkedHashMap();

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
    public Guide getParent() {
        return guide;
    }

    @Override
    public List<Entry> getChildren() {
        return Lists.newArrayList(entries.values());
    }

    @Override
    public void add(Entry child) {
        entries.put(child.getId(), child);
    }

    @Override
    public Entry getChild(String id) {
        return entries.getOrDefault(id, null);
    }

    @Override
    public Display getDisplay() {
        return display;
    }

    @Override
    public void setDisplay(Display display) {
        this.display = display;
    }

    @Override
    public Renderer<Category> getRenderer() {
        return renderer;
    }

    @Override
    public ViewingRequirement getViewingRequirement() {
        return () -> true;
    }
}
