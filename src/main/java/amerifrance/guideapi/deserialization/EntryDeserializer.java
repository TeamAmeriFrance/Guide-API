package amerifrance.guideapi.deserialization;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.api.Display;
import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.guide.Element;
import amerifrance.guideapi.guide.Entry;
import amerifrance.guideapi.utils.JsonHelper;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public class EntryDeserializer implements Deserializer {

    @Override
    public Entry deserialize(String value, Object... initParameters) {
        JsonObject entryJson = GSON.fromJson(value, JsonObject.class);

        String id = entryJson.get("id").getAsString();
        String name = entryJson.get("name").getAsString();
        Renderer renderer = (Renderer) JsonHelper.deserializeFromJson(entryJson.getAsJsonObject("renderer"));

        List<Element> elements = JsonHelper.jsonObjectStream(entryJson.getAsJsonArray("elements"))
                .map(JsonHelper::deserializeFromJson)
                .map(o -> (Element) o)
                .collect(Collectors.toList());

        return new Entry(id, name, renderer, entry -> {
            Display display = (Display) JsonHelper.deserializeFromJson(entryJson.getAsJsonObject("display"), entry);
            entry.setDisplay(display);

            elements.forEach(entry::add);
        });
    }
}
