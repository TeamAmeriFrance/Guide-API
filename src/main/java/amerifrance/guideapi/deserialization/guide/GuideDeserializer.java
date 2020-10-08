package amerifrance.guideapi.deserialization.guide;

import amerifrance.guideapi.api.Display;
import amerifrance.guideapi.deserialization.JsonDeserializer;
import amerifrance.guideapi.deserialization.RegisterDeserializer;
import amerifrance.guideapi.guide.Category;
import amerifrance.guideapi.guide.Guide;
import amerifrance.guideapi.utils.JsonHelper;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

@RegisterDeserializer("GUIDE")
public class GuideDeserializer implements JsonDeserializer {

    private static final String GUIDE = "guide";
    private static final String CATEGORIES = "categories";

    public Guide deserialize(String value, Object... initParameters) {
        JsonObject jsonObject = GSON.fromJson(value, JsonObject.class);
        JsonObject guideJson = jsonObject.getAsJsonObject(GUIDE);

        String id = guideJson.get(ID).getAsString();
        String name = guideJson.get(NAME).getAsString();

        List<Category> categories = JsonHelper.jsonObjectStream(guideJson.getAsJsonArray(CATEGORIES))
                .map(JsonHelper::deserializeFromJson)
                .map(o -> (Category) o)
                .collect(Collectors.toList());

        return new Guide(id, name, guide -> {
            Display display = (Display) JsonHelper.deserializeFromJson(guideJson.getAsJsonObject(DISPLAY), guide);
            guide.setDisplay(display);

            categories.forEach(guide::add);
        });
    }
}
