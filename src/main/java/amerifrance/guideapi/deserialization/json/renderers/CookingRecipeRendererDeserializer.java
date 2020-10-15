package amerifrance.guideapi.deserialization.json.renderers;

import amerifrance.guideapi.deserialization.json.JsonDeserializer;
import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.renderers.CookingRecipeRenderer;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@RegisterDeserializer("COOKING_RECIPE")
public class CookingRecipeRendererDeserializer implements JsonDeserializer {

    private static final String ITEM = "item";
    private static final String TYPE = "type";

    @Override
    public Object deserialize(String value, Object... initParameters) {
        JsonObject rendererJson = GSON.fromJson(value, JsonObject.class);
        JsonObject parameters = rendererJson.getAsJsonObject(PARAMETERS);

        Item item = Registry.ITEM.get(new Identifier(parameters.get(ITEM).getAsString()));
        String type = parameters.get(TYPE).getAsString();
        RecipeType<?> recipeType = null;

        if (type.equalsIgnoreCase(RecipeType.SMELTING.toString())) {
            recipeType = RecipeType.SMELTING;
        } else if (type.equalsIgnoreCase(RecipeType.BLASTING.toString())) {
            recipeType = RecipeType.BLASTING;
        } else if (type.equalsIgnoreCase(RecipeType.SMOKING.toString())) {
            recipeType = RecipeType.SMOKING;
        } else if (type.equalsIgnoreCase(RecipeType.CAMPFIRE_COOKING.toString())) {
            recipeType = RecipeType.CAMPFIRE_COOKING;
        }

        return new CookingRecipeRenderer(item, recipeType);
    }
}
