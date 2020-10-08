package amerifrance.guideapi.deserialization.renderers;

import amerifrance.guideapi.deserialization.JsonDeserializer;
import amerifrance.guideapi.deserialization.RegisterDeserializer;
import amerifrance.guideapi.renderers.PageBreakRenderer;

@RegisterDeserializer("PAGE_BREAK")
public class PageBreakRendererDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(String value, Object... initParameters) {
        return new PageBreakRenderer();
    }
}
