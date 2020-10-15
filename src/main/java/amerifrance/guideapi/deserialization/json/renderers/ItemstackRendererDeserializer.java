package amerifrance.guideapi.deserialization.json.renderers;

import amerifrance.guideapi.deserialization.json.JsonDeserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.renderers.ItemstackRenderer;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@RegisterDeserializer("ITEM")
public class ItemstackRendererDeserializer implements JsonDeserializer {

    private static final String ITEM = "item";
    private static final String HOVER_TEXT = "hoverText";
    private static final String SCALE = "scale";

    @Override
    public Object deserialize(String value, Object... initParameters) {
        JsonObject rendererJson = GSON.fromJson(value, JsonObject.class);
        JsonObject parameters = rendererJson.getAsJsonObject(PARAMETERS);

        Item item = Registry.ITEM.get(new Identifier(parameters.get(ITEM).getAsString()));
        String hoverText = parameters.has(HOVER_TEXT) ? parameters.get(HOVER_TEXT).getAsString() : null;
        int scale = parameters.has(SCALE) ? parameters.get(SCALE).getAsInt() : ItemstackRenderer.DEFAULT_SCALE;

        return new ItemstackRenderer(item, hoverText, scale);
    }
}
