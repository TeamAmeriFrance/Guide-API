package amerifrance.guideapi.deserialization.json.renderers;

import amerifrance.guideapi.deserialization.json.JsonDeserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.renderers.CraftingRecipeRenderer;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@RegisterDeserializer("CRAFTING_RECIPE")
public class CraftingRecipeRendererDeserializer implements JsonDeserializer {

    private static final String ITEM = "item";

    @Override
    public Object deserialize(String value, Object... initParameters) {
        JsonObject rendererJson = GSON.fromJson(value, JsonObject.class);
        JsonObject parameters = rendererJson.getAsJsonObject(PARAMETERS);

        Item item = Registry.ITEM.get(new Identifier(parameters.get(ITEM).getAsString()));

        return new CraftingRecipeRenderer(item);
    }
}
