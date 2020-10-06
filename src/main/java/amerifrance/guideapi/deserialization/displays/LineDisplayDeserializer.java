package amerifrance.guideapi.deserialization.displays;

import amerifrance.guideapi.api.TextProvider;
import amerifrance.guideapi.deserialization.JsonDeserializer;
import amerifrance.guideapi.displays.LineDisplay;

public class LineDisplayDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(String value, Object... initParameters) {
        return new LineDisplay((TextProvider) initParameters[0]);
    }
}
