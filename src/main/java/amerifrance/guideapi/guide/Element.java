package amerifrance.guideapi.guide;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.displays.Display;
import amerifrance.guideapi.renderers.Renderer;

public class Element implements IdTextProvider, ChildOf<Entry>, RendererProvider<Element> {

    private String id;
    private String name;
    private Entry entry;
    private Display display;
    private Renderer<Element> renderer;

    public Element(String id, String name, Entry entry, Renderer<Element> renderer) {
        this.id = id;
        this.name = name;
        this.entry = entry;
        this.renderer = renderer;
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
    public Renderer<Element> getRenderer() {
        return renderer;
    }

    @Override
    public ViewingRequirement getViewingRequirement() {
        return () -> true;
    }
}
