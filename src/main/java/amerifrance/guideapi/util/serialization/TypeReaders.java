package amerifrance.guideapi.util.serialization;

import java.lang.reflect.Type;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import amerifrance.guideapi.entries.EntryText;
import amerifrance.guideapi.interfaces.IEntrySerializing;
import amerifrance.guideapi.interfaces.ITypeReader;
import amerifrance.guideapi.objects.EntryBase;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import amerifrance.guideapi.objects.abstraction.IPage;
import amerifrance.guideapi.pages.PageFurnaceRecipe;
import amerifrance.guideapi.pages.PageIRecipe;
import amerifrance.guideapi.pages.PageImage;
import amerifrance.guideapi.pages.PageLocImage;
import amerifrance.guideapi.pages.PageLocItemStack;
import amerifrance.guideapi.pages.PageLocText;
import amerifrance.guideapi.pages.PageSound;
import amerifrance.guideapi.pages.PageUnlocImage;
import amerifrance.guideapi.pages.PageUnlocItemStack;
import amerifrance.guideapi.pages.PageUnlocText;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.reflect.TypeToken;

public abstract class TypeReaders<T> implements ITypeReader<T> {

    // Pages
    public static TypeReaders<PageFurnaceRecipe> pageFurnaceRecipeSerializing = new TypeReaders<PageFurnaceRecipe>(PageFurnaceRecipe.class, "furnaceRecipe")
    {
        @Override
        public PageFurnaceRecipe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ItemStack input = context.deserialize(json.getAsJsonObject().get("input"), ItemStack.class);
            return new PageFurnaceRecipe(input);
        }

        @Override
        public JsonObject serialize(PageFurnaceRecipe src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageFurnaceRecipe) {
                PageFurnaceRecipe page = (PageFurnaceRecipe) src;
                jsonObject.add("input", context.serialize(page.input));
            }
            return jsonObject;
        }
    };

    public static TypeReaders<PageImage> pageImageSerializing = new TypeReaders<PageImage>(PageImage.class, "image")
    {
        @Override
        public PageImage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation location = context.deserialize(json.getAsJsonObject().get("image"), ResourceLocation.class);
            return new PageImage(location);
        }

        @Override
        public JsonObject serialize(PageImage src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageImage) {
                PageImage page = (PageImage) src;
                jsonObject.add("image", context.serialize(page.image));
            }
            return jsonObject;
        }
    };

    public static IJsonReader pageIRecipeSerializing = new IJsonReader() {
        @Override
        public IPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            IRecipe recipe = context.deserialize(json.getAsJsonObject().get("recipe"), IRecipe.class);
            return new PageIRecipe(recipe);
        }

        @Override
        public JsonObject serialize(IPage src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageIRecipe) {
                PageIRecipe page = (PageIRecipe) src;
                jsonObject.add("recipe", context.serialize(page.recipe));
            }
            return jsonObject;
        }
    };

    public static IJsonReader pageLocImageSerializing = new IJsonReader() {
        @Override
        public IPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation location = context.deserialize(json.getAsJsonObject().get("image"), ResourceLocation.class);
            String locText = context.deserialize(json.getAsJsonObject().get("locText"), String.class);
            boolean drawAtTop = context.deserialize(json.getAsJsonObject().get("drawAtTop"), Boolean.class);
            return new PageLocImage(locText, location, drawAtTop);
        }

        @Override
        public JsonObject serialize(IPage src, Type typeofSrc, JsonSerializationContext context) {
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

    public static IJsonReader pageLocItemStackSerializing = new IJsonReader() {
        @Override
        public IPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String locText = context.deserialize(json.getAsJsonObject().get("locText"), String.class);
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("itemStack"), ItemStack.class);
            return new PageLocItemStack(locText, stack);
        }

        @Override
        public JsonObject serialize(IPage src, Type typeofSrc, JsonSerializationContext context) {
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

    public static IJsonReader pageLocTextSerializing = new IJsonReader() {
        @Override
        public IPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String locText = context.deserialize(json.getAsJsonObject().get("locText"), String.class);
            return new PageLocText(locText);
        }

        @Override
        public JsonObject serialize(IPage src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageLocText) {
                PageLocText page = (PageLocText) src;
                jsonObject.add("locText", context.serialize(page.locText));
            }
            return jsonObject;
        }
    };

    public static IJsonReader pageSoundSerializing = new IJsonReader() {
        @Override
        public IPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String sound = context.deserialize(json.getAsJsonObject().get("sound"), String.class);
            IPage pageToEmulate = context.deserialize(json.getAsJsonObject().get("pageToEmulate"), IPage.class);
            return new PageSound(pageToEmulate, sound);
        }

        @Override
        public JsonObject serialize(IPage src, Type typeofSrc, JsonSerializationContext context) {
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

    public static IJsonReader pageUnlocImageSerializing = new IJsonReader() {
        @Override
        public IPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation location = context.deserialize(json.getAsJsonObject().get("image"), ResourceLocation.class);
            String unlocText = context.deserialize(json.getAsJsonObject().get("unlocText"), String.class);
            boolean drawAtTop = context.deserialize(json.getAsJsonObject().get("drawAtTop"), Boolean.class);
            return new PageUnlocImage(unlocText, location, drawAtTop);
        }

        @Override
        public JsonObject serialize(IPage src, Type typeofSrc, JsonSerializationContext context) {
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

    public static IJsonReader pageUnlocItemStackSerializing = new IJsonReader() {
        @Override
        public IPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String unlocText = context.deserialize(json.getAsJsonObject().get("unlocText"), String.class);
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("itemStack"), ItemStack.class);
            return new PageUnlocItemStack(unlocText, stack);
        }

        @Override
        public JsonObject serialize(IPage src, Type typeofSrc, JsonSerializationContext context) {
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

    public static IJsonReader pageUnlocTextSerializing = new IJsonReader() {
        @Override
        public IPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String unlocText = context.deserialize(json.getAsJsonObject().get("unlocText"), String.class);
            return new PageUnlocText(unlocText);
        }

        @Override
        public JsonObject serialize(IPage src, Type typeofSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("pageType", context.serialize(src.getClass().getSimpleName()));
            if (src instanceof PageUnlocText) {
                PageUnlocText page = (PageUnlocText) src;
                jsonObject.add("unlocText", context.serialize(page.unlocText));
            }
            return jsonObject;
        }
    };
    
    // Entries
    
    public static TypeReaders<EntryBase> entryBaseSerialization = new TypeReaders<EntryBase>(EntryBase.class, "baseEntry")
    {
        @Override
        public EntryBase deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("unlocEntryName").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pageList"), new TypeToken<List<IPage>>() {
            }.getType());
            return new EntryBase(list, name);
        }

        @Override
        public JsonObject serialize(EntryBase src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("entryType", context.serialize(src.getClass().getSimpleName()));
            jsonObject.add("unlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("pageList", context.serialize(src.pageList));
            return jsonObject;
        }
    };

    public static IEntrySerializing entryTextSerialization = new IEntrySerializing() {
        @Override
        public EntryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("unlocEntryName").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pageList"), new TypeToken<List<IPage>>() {
            }.getType());
            return new EntryText(list, name);
        }

        @Override
        public JsonObject serialize(EntryAbstract src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("entryType", context.serialize(src.getClass().getSimpleName()));
            jsonObject.add("unlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("pageList", context.serialize(src.pageList));
            return jsonObject;
        }
    };
    
    private Class<? extends T> type;
    private String ident;
    
    private TypeReaders(Class<? extends T> type, String ident) {
        this.type = type;
        this.ident = ident;
        BookCreator.registerSerializer(this);
    }
    
    @Override
    public Class<? extends T> getType() {
        return type;
    }
    
    public String identifier() {
        return ident;
    }

    public static void init() {
    }

//    public static void registerPageSerializers() {
//        BookCreator.registerPageSerializer(PageFurnaceRecipe.class, pageFurnaceRecipeSerializing);
//        BookCreator.registerPageSerializer(PageImage.class, pageImageSerializing);
//        BookCreator.registerPageSerializer(PageIRecipe.class, pageIRecipeSerializing);
//        BookCreator.registerPageSerializer(PageLocImage.class, pageLocImageSerializing);
//        BookCreator.registerPageSerializer(PageLocItemStack.class, pageLocItemStackSerializing);
//        BookCreator.registerPageSerializer(PageLocText.class, pageLocTextSerializing);
//        BookCreator.registerPageSerializer(PageSound.class, pageSoundSerializing);
//        BookCreator.registerPageSerializer(PageUnlocImage.class, pageUnlocImageSerializing);
//        BookCreator.registerPageSerializer(PageUnlocItemStack.class, pageUnlocItemStackSerializing);
//        BookCreator.registerPageSerializer(PageUnlocText.class, pageUnlocTextSerializing);
//    }
}
