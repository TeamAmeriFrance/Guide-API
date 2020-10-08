package amerifrance.guideapi.deserialization.renderers;

import amerifrance.guideapi.deserialization.JsonDeserializer;
import amerifrance.guideapi.deserialization.RegisterDeserializer;
import amerifrance.guideapi.renderers.LineBreakRenderer;

@RegisterDeserializer("LINE_BREAK")
public class LineBreakRendererDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(String value, Object... initParameters) {
        return new LineBreakRenderer();
    }
}
