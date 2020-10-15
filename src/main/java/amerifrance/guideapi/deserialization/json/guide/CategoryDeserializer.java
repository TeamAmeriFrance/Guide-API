package amerifrance.guideapi.deserialization.json.guide;

import amerifrance.guideapi.api.Display;
import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.deserialization.json.JsonDeserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.guide.Category;
import amerifrance.guideapi.guide.Entry;
import amerifrance.guideapi.utils.deserialization.JsonHelper;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

@RegisterDeserializer("CATEGORY")
public class CategoryDeserializer implements JsonDeserializer {

    private static final String ENTRIES = "entries";

    @Override
    public Category deserialize(String value, Object... initParameters) {
        JsonObject categoryJson = GSON.fromJson(value, JsonObject.class);

        String id = categoryJson.get(ID).getAsString();
        String name = categoryJson.get(NAME).getAsString();
        Renderer renderer = (Renderer) JsonHelper.deserializeFromJson(categoryJson.getAsJsonObject(RENDERER));

        List<Entry> entries = JsonHelper.jsonObjectStream(categoryJson.getAsJsonArray(ENTRIES))
                .map(JsonHelper::deserializeFromJson)
                .map(o -> (Entry) o)
                .collect(Collectors.toList());

        return new Category(id, name, renderer, category -> {
            Display display = (Display) JsonHelper.deserializeFromJson(categoryJson.getAsJsonObject(DISPLAY), category);
            category.setDisplay(display);

            entries.forEach(category::add);
        });
    }
}
