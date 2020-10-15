package amerifrance.guideapi.deserialization.json.renderers;

import amerifrance.guideapi.deserialization.json.JsonDeserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.renderers.PageBreakRenderer;

@RegisterDeserializer("PAGE_BREAK")
public class PageBreakRendererDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(String value, Object... initParameters) {
        return new PageBreakRenderer();
    }
}
