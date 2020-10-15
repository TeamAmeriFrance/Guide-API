package amerifrance.guideapi.deserialization.markdown.renderers;

import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.deserialization.markdown.ItemStackDeserializer;
import amerifrance.guideapi.renderers.CraftingRecipeRenderer;
import amerifrance.guideapi.utils.ItemStackHelper;
import net.minecraft.item.ItemStack;

import java.util.regex.Matcher;

@RegisterDeserializer("CRAFTING_RECIPE-MARKDOWN")
public class CraftingRecipeRendererDeserializer implements ItemStackDeserializer {

    @Override
    public Object deserialize(String value, Object... initParameters) {
        Matcher matcher = PATTERN.matcher(value);

        if (matcher.find()) {
            ItemStack itemStack = ItemStackHelper.itemStackFromString(matcher.group(1));

            return new CraftingRecipeRenderer(itemStack.getItem());
        }

        return null;
    }

}
