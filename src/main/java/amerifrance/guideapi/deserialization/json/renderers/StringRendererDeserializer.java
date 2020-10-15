package amerifrance.guideapi.deserialization.json.renderers;

import amerifrance.guideapi.deserialization.json.JsonDeserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.renderers.StringRenderer;
import com.google.gson.JsonObject;

@RegisterDeserializer("TEXT")
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
