package amerifrance.guideapi.guide;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.renderers.Renderer;

public class Element implements IdProvider, TextProvider, ChildOf<Entry>, RendererProvider<Element> {

    private String id;
    private String name;
    private Entry entry;
    private Renderer<Element> renderer;

    public Element(String id, String name, Entry entry, Renderer<Element> renderer) {
        this.id = id;
        this.name = name;
        this.entry = entry;
        this.renderer = renderer;
    }

    public Element(String id, Entry entry, Renderer<Element> renderer) {
        this(id, "", entry, renderer);
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
