package amerifrance.guideapi.guide;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.displays.Display;
import amerifrance.guideapi.renderers.Renderer;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.google.common.collect.Maps.newLinkedHashMap;

public class Page implements IdTextProvider, ChildOf<Entry>, ParentOf<Element>, DisplayProvider, RendererProvider<Page> {

    private String id;
    private String name;
    private Entry entry;
    private Display display;
    private Renderer<Page> renderer;
    private Map<String, Element> elements;

    public Page(String id, String name, Entry entry, Renderer<Page> renderer, Consumer<Page> $) {
        this.id = id;
        this.name = name;
        this.entry = entry;
        this.renderer = renderer;
        this.elements = newLinkedHashMap();

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
    public Entry getParent() {
        return entry;
    }

    @Override
    public List<Element> getChildren() {
        return Lists.newArrayList(elements.values());
    }

    @Override
    public void add(Element child) {
        elements.put(child.getId(), child);
    }

    @Override
    public Element getChild(String id) {
        return elements.getOrDefault(id, null);
    }

    @Override
    public Renderer<Page> getRenderer() {
        return renderer;
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
    public ViewingRequirement getViewingRequirement() {
        return () -> true;
    }
}
