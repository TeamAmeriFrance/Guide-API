package amerifrance.guideapi.guide;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.renderers.StringRenderer;

public class Element implements IdProvider, TextProvider, ChildOf<Entry>, RendererProvider {

    private final String id;
    private final String text;
    private final Renderer renderer;

    private Entry entry;

    public Element(String id, String text, Renderer renderer) {
        this.id = id;
        this.text = text;
        this.renderer = renderer;
    }

    public Element(String id, String text) {
        this(id, text, new StringRenderer(text));
    }

    public Element(String id, Renderer renderer) {
        this(id, "", renderer);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Entry getParent() {
        return entry;
    }

    @Override
    public void setParent(Entry parent) {
        this.entry = parent;
    }

    @Override
    public Renderer getRenderer() {
        return renderer;
    }

    @Override
    public ViewingRequirement getViewingRequirement() {
        return () -> true;
    }
}
