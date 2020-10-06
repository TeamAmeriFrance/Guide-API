package amerifrance.guideapi.deserialization;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.api.Display;
import amerifrance.guideapi.api.Renderer;
import amerifrance.guideapi.guide.Category;
import amerifrance.guideapi.guide.Entry;
import amerifrance.guideapi.utils.JsonHelper;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryDeserializer implements Deserializer {

    @Override
    public Category deserialize(String value, Object... initParameters) {
        JsonObject categoryJson = GSON.fromJson(value, JsonObject.class);

        String id = categoryJson.get("id").getAsString();
        String name = categoryJson.get("name").getAsString();
        Renderer renderer = (Renderer) JsonHelper.deserializeFromJson(categoryJson.getAsJsonObject("renderer"));

        List<Entry> entries = JsonHelper.jsonObjectStream(categoryJson.getAsJsonArray("entries"))
                .map(JsonHelper::deserializeFromJson)
                .map(o -> (Entry) o)
                .collect(Collectors.toList());

        return new Category(id, name, renderer, category -> {
            Display display = (Display) JsonHelper.deserializeFromJson(categoryJson.getAsJsonObject("display"), category);
            category.setDisplay(display);

            entries.forEach(category::add);
        });
    }
}
