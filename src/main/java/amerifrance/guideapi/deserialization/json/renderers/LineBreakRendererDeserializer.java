package amerifrance.guideapi.deserialization.json.renderers;

import amerifrance.guideapi.deserialization.json.JsonDeserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.renderers.LineBreakRenderer;

@RegisterDeserializer("LINE_BREAK")
public class LineBreakRendererDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(String value, Object... initParameters) {
        return new LineBreakRenderer();
    }
}
