package amerifrance.guideapi.util.serialization;

import amerifrance.guideapi.interfaces.IPageSerializing;
import amerifrance.guideapi.objects.abstraction.PageAbstract;
import amerifrance.guideapi.pages.*;
import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

public class PageSerialization {

    public static IPageSerializing pageFurnaceRecipeSerializing = new IPageSerializing() {
        @Override
        public PageAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ItemStack input = context.deserialize(json.getAsJsonObject().get("input"), ItemStack.class);
            return new PageFurnaceRecipe(input);
        }

        @Override
        public JsonObject serialize(PageAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageFurnaceRecipe) {
                PageFurnaceRecipe page = (PageFurnaceRecipe) src;
                jsonObject.add("input", context.serialize(page.input));
            }
            return jsonObject;
        }
    };

    public static IPageSerializing pageImageSerializing = new IPageSerializing() {
        @Override
        public PageAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation location = context.deserialize(json.getAsJsonObject().get("image"), ResourceLocation.class);
            return new PageImage(location);
        }

        @Override
        public JsonObject serialize(PageAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageImage) {
                PageImage page = (PageImage) src;
                jsonObject.add("image", context.serialize(page.image));
            }
            return jsonObject;
        }
    };

    public static IPageSerializing pageIRecipeSerializing = new IPageSerializing() {
        @Override
        public PageAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            IRecipe recipe = context.deserialize(json.getAsJsonObject().get("recipe"), IRecipe.class);
            return new PageIRecipe(recipe);
        }

        @Override
        public JsonObject serialize(PageAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageIRecipe) {
                PageIRecipe page = (PageIRecipe) src;
                jsonObject.add("recipe", context.serialize(page.recipe));
            }
            return jsonObject;
        }
    };

    public static IPageSerializing pageLocImageSerializing = new IPageSerializing() {
        @Override
        public PageAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation location = context.deserialize(json.getAsJsonObject().get("image"), ResourceLocation.class);
            String locText = context.deserialize(json.getAsJsonObject().get("locText"), String.class);
            boolean drawAtTop = context.deserialize(json.getAsJsonObject().get("drawAtTop"), Boolean.class);
            return new PageLocImage(locText, location, drawAtTop);
        }

        @Override
        public JsonObject serialize(PageAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageLocImage) {
                PageLocImage page = (PageLocImage) src;
                jsonObject.add("image", context.serialize(page.image));
                jsonObject.add("locText", context.serialize(page.locText));
                jsonObject.add("drawAtTop", context.serialize(page.drawAtTop));
            }
            return jsonObject;
        }
    };

    public static IPageSerializing pageLocItemStackSerializing = new IPageSerializing() {
        @Override
        public PageAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String locText = context.deserialize(json.getAsJsonObject().get("locText"), String.class);
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("itemStack"), ItemStack.class);
            return new PageLocItemStack(locText, stack);
        }

        @Override
        public JsonObject serialize(PageAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageLocItemStack) {
                PageLocItemStack page = (PageLocItemStack) src;
                jsonObject.add("itemStack", context.serialize(page.stack));
                jsonObject.add("locText", context.serialize(page.locText));
            }
            return jsonObject;
        }
    };

    public static IPageSerializing pageLocTextSerializing = new IPageSerializing() {
        @Override
        public PageAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String locText = context.deserialize(json.getAsJsonObject().get("locText"), String.class);
            return new PageLocText(locText);
        }

        @Override
        public JsonObject serialize(PageAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageLocText) {
                PageLocText page = (PageLocText) src;
                jsonObject.add("locText", context.serialize(page.locText));
            }
            return jsonObject;
        }
    };

    public static IPageSerializing pageSoundSerializing = new IPageSerializing() {
        @Override
        public PageAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String sound = context.deserialize(json.getAsJsonObject().get("sound"), String.class);
            PageAbstract pageToEmulate = context.deserialize(json.getAsJsonObject().get("pageToEmulate"), PageAbstract.class);
            return new PageSound(pageToEmulate, sound);
        }

        @Override
        public JsonObject serialize(PageAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageSound) {
                PageSound page = (PageSound) src;
                jsonObject.add("sound", context.serialize(page.sound));
                jsonObject.add("pageToEmulate", context.serialize(page.pageToEmulate));
            }
            return jsonObject;
        }
    };

    public static IPageSerializing pageUnlocImageSerializing = new IPageSerializing() {
        @Override
        public PageAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation location = context.deserialize(json.getAsJsonObject().get("image"), ResourceLocation.class);
            String unlocText = context.deserialize(json.getAsJsonObject().get("unlocText"), String.class);
            boolean drawAtTop = context.deserialize(json.getAsJsonObject().get("drawAtTop"), Boolean.class);
            return new PageUnlocImage(unlocText, location, drawAtTop);
        }

        @Override
        public JsonObject serialize(PageAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageUnlocImage) {
                PageUnlocImage page = (PageUnlocImage) src;
                jsonObject.add("image", context.serialize(page.image));
                jsonObject.add("unlocText", context.serialize(page.unlocText));
                jsonObject.add("drawAtTop", context.serialize(page.drawAtTop));
            }
            return jsonObject;
        }
    };

    public static IPageSerializing pageUnlocItemStackSerializing = new IPageSerializing() {
        @Override
        public PageAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String unlocText = context.deserialize(json.getAsJsonObject().get("unlocText"), String.class);
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("itemStack"), ItemStack.class);
            return new PageUnlocItemStack(unlocText, stack);
        }

        @Override
        public JsonObject serialize(PageAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageUnlocItemStack) {
                PageUnlocItemStack page = (PageUnlocItemStack) src;
                jsonObject.add("itemStack", context.serialize(page.stack));
                jsonObject.add("unlocText", context.serialize(page.unlocText));
            }
            return jsonObject;
        }
    };

    public static IPageSerializing pageUnlocTextSerializing = new IPageSerializing() {
        @Override
        public PageAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String unlocText = context.deserialize(json.getAsJsonObject().get("unlocText"), String.class);
            return new PageUnlocText(unlocText);
        }

        @Override
        public JsonObject serialize(PageAbstract src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageUnlocText) {
                PageUnlocText page = (PageUnlocText) src;
                jsonObject.add("unlocText", context.serialize(page.unlocText));
            }
            return jsonObject;
        }
    };

    public static void registerPageSerializers() {
        BookCreator.addPageSerializingToMap(PageFurnaceRecipe.class, pageFurnaceRecipeSerializing);
        BookCreator.addPageSerializingToMap(PageImage.class, pageImageSerializing);
        BookCreator.addPageSerializingToMap(PageIRecipe.class, pageIRecipeSerializing);
        BookCreator.addPageSerializingToMap(PageLocImage.class, pageLocImageSerializing);
        BookCreator.addPageSerializingToMap(PageLocItemStack.class, pageLocItemStackSerializing);
        BookCreator.addPageSerializingToMap(PageLocText.class, pageLocTextSerializing);
        BookCreator.addPageSerializingToMap(PageSound.class, pageSoundSerializing);
        BookCreator.addPageSerializingToMap(PageUnlocImage.class, pageUnlocImageSerializing);
        BookCreator.addPageSerializingToMap(PageUnlocItemStack.class, pageUnlocItemStackSerializing);
        BookCreator.addPageSerializingToMap(PageUnlocText.class, pageUnlocTextSerializing);
    }
}
