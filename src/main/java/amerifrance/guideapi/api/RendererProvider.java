package amerifrance.guideapi.api;

public interface RendererProvider<T> {
    Renderer<T> getRenderer();

    ViewingRequirement getViewingRequirement();
}
