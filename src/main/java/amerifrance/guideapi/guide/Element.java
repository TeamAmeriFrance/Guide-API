package amerifrance.guideapi.guide;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.displays.Display;
import amerifrance.guideapi.renderers.Renderer;

public class Element implements IdTextProvider, ChildOf<Page>, DisplayProvider, RendererProvider<Element> {

    private String id;
    private String name;
    private Page page;
    private Display display;
    private Renderer<Element> renderer;

    public Element(String id, String name, Page page, Renderer<Element> renderer) {
        this.id = id;
        this.name = name;
        this.page = page;
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
    public Page getParent() {
        return page;
    }

    @Override
    public Renderer<Element> getRenderer() {
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
