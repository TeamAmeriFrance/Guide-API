package amerifrance.guideapi.deserialization.json.guide;

import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.deserialization.json.JsonDeserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.guide.Element;
import amerifrance.guideapi.utils.deserialization.JsonHelper;
import com.google.gson.JsonObject;

@RegisterDeserializer("ELEMENT")
public class ElementDeserializer implements JsonDeserializer {

    @Override
    public Element deserialize(String value, Object... initParameters) {
        JsonObject elementJson = GSON.fromJson(value, JsonObject.class);

        String id = elementJson.get(ID).getAsString();
        String name = elementJson.get(NAME).getAsString();
        Renderer renderer = (Renderer) JsonHelper.deserializeFromJson(elementJson.getAsJsonObject(RENDERER));

        return new Element(id, name, renderer);
    }
}
