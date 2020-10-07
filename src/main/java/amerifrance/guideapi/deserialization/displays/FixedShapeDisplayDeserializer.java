package amerifrance.guideapi.deserialization.displays;

import amerifrance.guideapi.api.TextProvider;
import amerifrance.guideapi.deserialization.JsonDeserializer;
import amerifrance.guideapi.displays.FixedShapeDisplay;
import amerifrance.guideapi.utils.JsonHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;
import java.util.stream.Collectors;

public class FixedShapeDisplayDeserializer implements JsonDeserializer {

    private static final String CENTER_RENDERERS = "centerRenderers";

    @Override
    public Object deserialize(String value, Object... initParameters) {
        JsonObject displayJson = GSON.fromJson(value, JsonObject.class);
        JsonObject parameters = displayJson.getAsJsonObject(PARAMETERS);

        boolean centerRenderers = parameters.has(CENTER_RENDERERS) && parameters.get(CENTER_RENDERERS).getAsBoolean();
        List<String> shape = JsonHelper.jsonPrimitiveStream(parameters.get("shape").getAsJsonArray())
                .map(JsonPrimitive::getAsString)
                .collect(Collectors.toList());

        return new FixedShapeDisplay((TextProvider) initParameters[0], centerRenderers, shape);
    }
}
