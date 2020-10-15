package amerifrance.guideapi.deserialization.markdown.renderers;

import amerifrance.guideapi.api.RegisterDeserializer;
import amerifrance.guideapi.deserialization.markdown.ItemStackDeserializer;
import amerifrance.guideapi.renderers.ItemstackRenderer;
import amerifrance.guideapi.utils.ItemStackHelper;
import net.minecraft.item.ItemStack;

import java.util.regex.Matcher;

@RegisterDeserializer("ITEMSTACK-MARKDOWN")
public class ItemstackRendererDeserializer implements ItemStackDeserializer {

    @Override
    public Object deserialize(String value, Object... initParameters) {
        Matcher matcher = PATTERN.matcher(value);

        if (matcher.find()) {
            ItemStack itemStack = ItemStackHelper.itemStackFromString(matcher.group(1));

            return new ItemstackRenderer(itemStack);
        }

        return null;
    }
}
