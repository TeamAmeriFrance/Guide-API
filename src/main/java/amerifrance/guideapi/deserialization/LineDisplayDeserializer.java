package amerifrance.guideapi.deserialization;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.api.TextProvider;
import amerifrance.guideapi.displays.LineDisplay;

public class LineDisplayDeserializer implements Deserializer {

    @Override
    public Object deserialize(String value, Object... initParameters) {
        return new LineDisplay((TextProvider) initParameters[0]);
    }
}
