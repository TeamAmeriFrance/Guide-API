package amerifrance.guideapi.deserialization;

import amerifrance.guideapi.api.Deserializer;
import amerifrance.guideapi.api.Display;
import amerifrance.guideapi.guide.Category;
import amerifrance.guideapi.guide.Guide;
import amerifrance.guideapi.utils.JsonHelper;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public class GuideDeserializer implements Deserializer {

    public Guide deserialize(String value, Object... initParameters) {
        JsonObject jsonObject = GSON.fromJson(value, JsonObject.class);
        JsonObject guideJson = jsonObject.getAsJsonObject("guide");

        String id = guideJson.get("id").getAsString();
        String name = guideJson.get("name").getAsString();

        List<Category> categories = JsonHelper.jsonObjectStream(guideJson.getAsJsonArray("categories"))
                .map(JsonHelper::deserializeFromJson)
                .map(o -> (Category) o)
                .collect(Collectors.toList());

        return new Guide(id, name, guide -> {
            Display display = (Display) JsonHelper.deserializeFromJson(guideJson.getAsJsonObject("display"), guide);
            guide.setDisplay(display);

            categories.forEach(guide::add);
        });
    }
}
