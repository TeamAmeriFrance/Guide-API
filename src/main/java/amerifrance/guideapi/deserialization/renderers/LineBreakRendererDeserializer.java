package amerifrance.guideapi.deserialization.renderers;

import amerifrance.guideapi.deserialization.JsonDeserializer;
import amerifrance.guideapi.renderers.LineBreakRenderer;

public class LineBreakRendererDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(String value, Object... initParameters) {
        return new LineBreakRenderer();
    }
}
