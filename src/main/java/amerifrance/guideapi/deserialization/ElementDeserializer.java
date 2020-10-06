package amerifrance.guideapi.deserialization;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.guide.Element;
import amerifrance.guideapi.utils.JsonHelper;
import com.google.gson.JsonObject;

public class ElementDeserializer implements Deserializer {

    @Override
    public Element deserialize(String value, Object... initParameters) {
        JsonObject elementJson = GSON.fromJson(value, JsonObject.class);

        String id = elementJson.get("id").getAsString();
        String name = elementJson.get("text").getAsString();
        Renderer renderer = (Renderer) JsonHelper.deserializeFromJson(elementJson.getAsJsonObject("renderer"));

        return new Element(id, name, renderer);
    }
}
