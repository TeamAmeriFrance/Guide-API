package amerifrance.guideapi.util.serialization;

import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.CategoryBase;
import amerifrance.guideapi.api.base.EntryBase;
import amerifrance.guideapi.categories.CategoryItemStack;
import amerifrance.guideapi.entries.EntryText;
import amerifrance.guideapi.entries.EntryUniText;
import amerifrance.guideapi.interfaces.ITypeReader;
import amerifrance.guideapi.pages.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.List;

public abstract class TypeReaders<T> implements ITypeReader<T> {

    // Pages
    public static TypeReaders<PageFurnaceRecipe> PAGE_FURNACE_RECIPE = new TypeReaders<PageFurnaceRecipe>(PageFurnaceRecipe.class) {
        @Override
        public PageFurnaceRecipe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ItemStack input = context.deserialize(json.getAsJsonObject().get("input"), ItemStack.class);
            return new PageFurnaceRecipe(input);
        }

        @Override
        public void addData(JsonObject jsonObject, PageFurnaceRecipe src, JsonSerializationContext context) {
            jsonObject.add("input", context.serialize(src.input));
        }
    };

    public static TypeReaders<PageImage> PAGE_IMAGE = new TypeReaders<PageImage>(PageImage.class) {
        @Override
        public PageImage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation location = context.deserialize(json.getAsJsonObject().get("image"), ResourceLocation.class);
            return new PageImage(location);
        }

        @Override
        public void addData(JsonObject jsonObject, PageImage src, JsonSerializationContext context) {
            jsonObject.add("image", context.serialize(src.image));
        }
    };

    public static TypeReaders<PageIRecipe> PAGE_IRECIPE = new TypeReaders<PageIRecipe>(PageIRecipe.class) {
        @Override
        public PageIRecipe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            IRecipe recipe = context.deserialize(json.getAsJsonObject().get("recipe"), IRecipe.class);
            return new PageIRecipe(recipe);
        }

        @Override
        public void addData(JsonObject jsonObject, PageIRecipe src, JsonSerializationContext context) {
            jsonObject.add("recipe", context.serialize(src.recipe));
        }
    };

    public static TypeReaders<PageLocImage> PAGE_IMAGE_LOC = new TypeReaders<PageLocImage>(PageLocImage.class) {
        @Override
        public PageLocImage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation location = context.deserialize(json.getAsJsonObject().get("image"), ResourceLocation.class);
            String locText = context.deserialize(json.getAsJsonObject().get("locText"), String.class);
            boolean drawAtTop = context.deserialize(json.getAsJsonObject().get("drawAtTop"), Boolean.class);
            return new PageLocImage(locText, location, drawAtTop);
        }

        @Override
        public void addData(JsonObject jsonObject, PageLocImage src, JsonSerializationContext context) {
            jsonObject.add("image", context.serialize(src.image));
            jsonObject.add("locText", context.serialize(src.locText));
            jsonObject.add("drawAtTop", context.serialize(src.drawAtTop));
        }
    };

    public static TypeReaders<PageLocItemStack> PAGE_ITEMSTACK_LOC = new TypeReaders<PageLocItemStack>(PageLocItemStack.class) {
        @Override
        public PageLocItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String locText = context.deserialize(json.getAsJsonObject().get("locText"), String.class);
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("itemStack"), ItemStack.class);
            return new PageLocItemStack(locText, stack);
        }

        @Override
        public void addData(JsonObject jsonObject, PageLocItemStack src, JsonSerializationContext context) {
            jsonObject.add("itemStack", context.serialize(src.stack));
            jsonObject.add("locText", context.serialize(src.locText));

        }
    };

    public static TypeReaders<PageLocText> PAGE_TEXT_LOC = new TypeReaders<PageLocText>(PageLocText.class) {
        @Override
        public PageLocText deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String locText = context.deserialize(json.getAsJsonObject().get("locText"), String.class);
            return new PageLocText(locText);
        }

        @Override
        public void addData(JsonObject jsonObject, PageLocText src, JsonSerializationContext context) {
            jsonObject.add("locText", context.serialize(src.locText));
        }
    };

    public static TypeReaders<PageSound> PAGE_SOUND = new TypeReaders<PageSound>(PageSound.class) {
        @Override
        public PageSound deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String sound = context.deserialize(json.getAsJsonObject().get("sound"), String.class);
            IPage pageToEmulate = context.deserialize(json.getAsJsonObject().get("pageToEmulate"), IPage.class);
            return new PageSound(pageToEmulate, sound);
        }

        @Override
        public void addData(JsonObject jsonObject, PageSound src, JsonSerializationContext context) {
            jsonObject.add("sound", context.serialize(src.sound));
            jsonObject.add("pageToEmulate", context.serialize(src.pageToEmulate));
        }
    };

    public static TypeReaders<PageUnlocImage> PAGE_IMAGE_UNLOC = new TypeReaders<PageUnlocImage>(PageUnlocImage.class) {
        @Override
        public PageUnlocImage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation location = context.deserialize(json.getAsJsonObject().get("image"), ResourceLocation.class);
            String unlocText = context.deserialize(json.getAsJsonObject().get("unlocText"), String.class);
            boolean drawAtTop = context.deserialize(json.getAsJsonObject().get("drawAtTop"), Boolean.class);
            return new PageUnlocImage(unlocText, location, drawAtTop);
        }

        @Override
        public void addData(JsonObject jsonObject, PageUnlocImage src, JsonSerializationContext context) {
            jsonObject.add("image", context.serialize(src.image));
            jsonObject.add("unlocText", context.serialize(src.unlocText));
            jsonObject.add("drawAtTop", context.serialize(src.drawAtTop));
        }
    };

    public static TypeReaders<PageUnlocItemStack> PAGE_ITEMSTACK_UNLOC = new TypeReaders<PageUnlocItemStack>(PageUnlocItemStack.class) {
        @Override
        public PageUnlocItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String unlocText = context.deserialize(json.getAsJsonObject().get("unlocText"), String.class);
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("itemStack"), ItemStack.class);
            return new PageUnlocItemStack(unlocText, stack);
        }

        @Override
        public void addData(JsonObject jsonObject, PageUnlocItemStack src, JsonSerializationContext context) {
            jsonObject.add("itemStack", context.serialize(src.stack));
            jsonObject.add("unlocText", context.serialize(src.unlocText));
        }
    };

    public static TypeReaders<PageUnlocText> PAGE_TEXT_UNLOC = new TypeReaders<PageUnlocText>(PageUnlocText.class) {
        @Override
        public PageUnlocText deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String unlocText = context.deserialize(json.getAsJsonObject().get("unlocText"), String.class);
            return new PageUnlocText(unlocText);
        }

        @Override
        public void addData(JsonObject jsonObject, PageUnlocText src, JsonSerializationContext context) {
            jsonObject.add("unlocText", context.serialize(src.unlocText));
        }
    };

    // Entries

    public static TypeReaders<EntryBase> ENTRY_BASE = new TypeReaders<EntryBase>(EntryBase.class) {
        @Override
        public EntryBase deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("unlocEntryName").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pageList"), new TypeToken<List<IPage>>() {
            }.getType());
            return new EntryBase(list, name);
        }

        @Override
        public void addData(JsonObject jsonObject, EntryBase src, JsonSerializationContext context) {
            jsonObject.add("unlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("pageList", context.serialize(src.pageList));
        }
    };

    public static TypeReaders<EntryText> ENTRY_TEXT = new TypeReaders<EntryText>(EntryText.class) {
        @Override
        public EntryText deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("unlocEntryName").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pageList"), new TypeToken<List<IPage>>() {
            }.getType());
            return new EntryText(list, name);
        }

        @Override
        public void addData(JsonObject jsonObject, EntryText src, JsonSerializationContext context) {
            jsonObject.add("unlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("pageList", context.serialize(src.pageList));
        }
    };

    public static TypeReaders<EntryUniText> ENTRY_UNI_TEXT = new TypeReaders<EntryUniText>(EntryUniText.class) {
        @Override
        public EntryUniText deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("unlocEntryName").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pageList"), new TypeToken<List<IPage>>() {
            }.getType());
            return new EntryUniText(list, name);
        }

        @Override
        public void addData(JsonObject jsonObject, EntryUniText src, JsonSerializationContext context) {
            jsonObject.add("unlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("pageList", context.serialize(src.pageList));
        }
    };

    // Categories

    public static TypeReaders<CategoryBase> CATEGORY_BASE = new TypeReaders<CategoryBase>(CategoryBase.class) {
        @Override
        public CategoryBase deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            List<EntryAbstract> list = context.deserialize(json.getAsJsonObject().get("entryList"), new TypeToken<List<EntryAbstract>>() {
            }.getType());
            String name = json.getAsJsonObject().get("unlocCategoryName").getAsString();
            return new CategoryBase(list, name);
        }

        @Override
        public void addData(JsonObject jsonObject, CategoryBase src, JsonSerializationContext context) {
            jsonObject.add("unlocCategoryName", context.serialize(src.unlocCategoryName));
            jsonObject.add("entryList", context.serialize(src.entryList));
        }
    };

    public static TypeReaders<CategoryItemStack> CATEGORY_ITEMSTACK = new TypeReaders<CategoryItemStack>(CategoryItemStack.class) {
        @Override
        public CategoryItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            List<EntryAbstract> list = context.deserialize(json.getAsJsonObject().get("entryList"), new TypeToken<List<EntryAbstract>>() {
            }.getType());
            String name = json.getAsJsonObject().get("unlocCategoryName").getAsString();
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("itemStack"), ItemStack.class);
            return new CategoryItemStack(list, name, stack);
        }

        @Override
        public void addData(JsonObject jsonObject, CategoryItemStack src, JsonSerializationContext context) {
            jsonObject.add("unlocCategoryName", context.serialize(src.unlocCategoryName));
            jsonObject.add("itemStack", context.serialize(src.stack));
            jsonObject.add("entryList", context.serialize(src.entryList));
        }
    };

    private Class<? extends T> type;

    private TypeReaders(Class<? extends T> type) {
        this.type = type;
        BookCreator.registerSerializer(this);
    }

    public static void init() {
        // Run those static initializers
    }

    @Override
    public Class<? extends T> getType() {
        return type;
    }

    @Override
    public JsonObject serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        addData(jsonObject, src, context);
        return jsonObject;
    }

    protected abstract void addData(JsonObject jsonObject, T src, JsonSerializationContext context);
}
