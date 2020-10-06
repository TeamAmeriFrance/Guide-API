package amerifrance.guideapi.deserialization;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.renderers.StringRenderer;
import com.google.gson.JsonObject;

public class StringRendererDeserializer implements Deserializer {

    @Override
    public Object deserialize(String value, Object... initParameters) {
        JsonObject rendererJson = GSON.fromJson(value, JsonObject.class);
        JsonObject parameters = rendererJson.getAsJsonObject("parameters");

        return new StringRenderer(parameters.get("text").getAsString());
    }
}
