package amerifrance.guideapi.util.json.serialization;

import amerifrance.guideapi.util.json.JsonHelper;
import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.lang.reflect.Type;

public class SerializerItemStack extends SerializerBase<ItemStack> {

    public static final String NAME = "name";
    public static final String AMOUNT = "amount";
    public static final String META = "meta";

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ResourceLocation name = new ResourceLocation(JsonHelper.getString(json, NAME, "minecraft:air"));
        int amount = MathHelper.clamp_int(JsonHelper.getInteger(json, AMOUNT, 1), 1, 64);
        int meta = JsonHelper.getInteger(json, META, 0);

        return new ItemStack(ForgeRegistries.ITEMS.getValue(name), amount, meta);
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(NAME, src.getItem().getRegistryName().toString());
        jsonObject.addProperty(AMOUNT, src.stackSize);
        jsonObject.addProperty(META, src.getItemDamage());
        return jsonObject;
    }
}
