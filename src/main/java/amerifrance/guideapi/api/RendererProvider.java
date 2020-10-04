package amerifrance.guideapi.api;

public interface RendererProvider {
    Renderer getRenderer();

    ViewingRequirement getViewingRequirement();
}
