package amerifrance.guideapi.api;

import amerifrance.guideapi.renderers.Renderer;

public interface RendererProvider<T> {
    Renderer<T> getRenderer();

    ViewingRequirement getViewingRequirement();
}
