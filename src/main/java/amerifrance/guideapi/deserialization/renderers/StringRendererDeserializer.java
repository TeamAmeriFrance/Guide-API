package amerifrance.guideapi.deserialization.renderers;

import amerifrance.guideapi.deserialization.JsonDeserializer;
import amerifrance.guideapi.renderers.StringRenderer;
import com.google.gson.JsonObject;

public class StringRendererDeserializer implements JsonDeserializer {

    private static final String TEXT = "text";
    private static final String UNDERLINE_ON_HOVER = "underlineOnHover";

    @Override
    public Object deserialize(String value, Object... initParameters) {
        JsonObject rendererJson = GSON.fromJson(value, JsonObject.class);
        JsonObject parameters = rendererJson.getAsJsonObject(PARAMETERS);

        String text = parameters.get(TEXT).getAsString();
        boolean underlineOnHover = parameters.has(UNDERLINE_ON_HOVER) && parameters.get(UNDERLINE_ON_HOVER).getAsBoolean();

        return new StringRenderer(text, underlineOnHover);
    }
}
