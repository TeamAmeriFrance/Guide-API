package amerifrance.guideapi.deserialization.json.guide;

import amerifrance.guideapi.api.Display;
import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.deserialization.json.JsonDeserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.guide.Element;
import amerifrance.guideapi.guide.Entry;
import amerifrance.guideapi.utils.JsonHelper;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

@RegisterDeserializer("ENTRY")
public class EntryDeserializer implements JsonDeserializer {

    private static final String ELEMENTS = "elements";

    @Override
    public Entry deserialize(String value, Object... initParameters) {
        JsonObject entryJson = GSON.fromJson(value, JsonObject.class);

        String id = entryJson.get(ID).getAsString();
        String name = entryJson.get(NAME).getAsString();
        Renderer renderer = (Renderer) JsonHelper.deserializeFromJson(entryJson.getAsJsonObject(RENDERER));

        List<Element> elements = JsonHelper.jsonObjectStream(entryJson.getAsJsonArray(ELEMENTS))
                .map(JsonHelper::deserializeFromJson)
                .map(o -> (Element) o)
                .collect(Collectors.toList());

        return new Entry(id, name, renderer, entry -> {
            Display display = (Display) JsonHelper.deserializeFromJson(entryJson.getAsJsonObject(DISPLAY), entry);
            entry.setDisplay(display);

            elements.forEach(entry::add);
        });
    }
}
