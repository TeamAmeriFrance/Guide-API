package amerifrance.guideapi.util.serialization;

import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Category;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.entry.EntryResourceLocation;
import amerifrance.guideapi.iface.ITypeReader;
import amerifrance.guideapi.page.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

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

    public static TypeReaders<PageTextImage> PAGE_IMAGE_TEXT = new TypeReaders<PageTextImage>(PageTextImage.class) {
        @Override
        public PageTextImage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation location = context.deserialize(json.getAsJsonObject().get("image"), ResourceLocation.class);
            String text = context.deserialize(json.getAsJsonObject().get("text"), String.class);
            boolean drawAtTop = context.deserialize(json.getAsJsonObject().get("drawAtTop"), Boolean.class);
            return new PageTextImage(text, location, drawAtTop);
        }

        @Override
        public void addData(JsonObject jsonObject, PageTextImage src, JsonSerializationContext context) {
            jsonObject.add("image", context.serialize(src.image));
            jsonObject.add("text", context.serialize(src.draw));
            jsonObject.add("drawAtTop", context.serialize(src.drawAtTop));
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

    public static TypeReaders<PageItemStack> PAGE_ITEMSTACK = new TypeReaders<PageItemStack>(PageItemStack.class) {
        @Override
        public PageItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String text = context.deserialize(json.getAsJsonObject().get("text"), String.class);
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("itemStack"), ItemStack.class);
            return new PageItemStack(text, stack);
        }

        @Override
        public void addData(JsonObject jsonObject, PageItemStack src, JsonSerializationContext context) {
            jsonObject.add("itemStack", context.serialize(src.stack));
            jsonObject.add("text", context.serialize(src.draw));

        }
    };

    public static TypeReaders<PageText> PAGE_TEXT_LOC = new TypeReaders<PageText>(PageText.class) {
        @Override
        public PageText deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String text = context.deserialize(json.getAsJsonObject().get("text"), String.class);
            return new PageText(text);
        }

        @Override
        public void addData(JsonObject jsonObject, PageText src, JsonSerializationContext context) {
            jsonObject.add("text", context.serialize(src.draw));
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

    // Entries

    public static TypeReaders<Entry> ENTRY_BASE = new TypeReaders<Entry>(Entry.class) {
        @Override
        public Entry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("unlocEntryName").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pageList"), new TypeToken<List<IPage>>() {
            }.getType());
            return new Entry(list, name);
        }

        @Override
        public void addData(JsonObject jsonObject, Entry src, JsonSerializationContext context) {
            jsonObject.add("unlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("pageList", context.serialize(src.pageList));
        }
    };

    public static TypeReaders<Entry> ENTRY_TEXT = new TypeReaders<Entry>(Entry.class) {
        @Override
        public Entry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("unlocEntryName").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pageList"), new TypeToken<List<IPage>>() {
            }.getType());
            return new Entry(list, name);
        }

        @Override
        public void addData(JsonObject jsonObject, Entry src, JsonSerializationContext context) {
            jsonObject.add("unlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("pageList", context.serialize(src.pageList));
        }
    };

    public static TypeReaders<EntryItemStack> ENTRY_ITEMSTACK = new TypeReaders<EntryItemStack>(EntryItemStack.class) {
        @Override
        public EntryItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("unlocEntryName").getAsString();
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("itemStack"), ItemStack.class);
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pageList"), new TypeToken<List<IPage>>() {
            }.getType());
            return new EntryItemStack(list, name, stack);
        }

        @Override
        public void addData(JsonObject jsonObject, EntryItemStack src, JsonSerializationContext context) {
            jsonObject.add("unlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("itemStack", context.serialize(src.stack));
            jsonObject.add("pageList", context.serialize(src.pageList));
        }
    };

    public static TypeReaders<EntryResourceLocation> ENTRY_IMAGE = new TypeReaders<EntryResourceLocation>(EntryResourceLocation.class) {
        @Override
        public EntryResourceLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("unlocEntryName").getAsString();
            String resource = json.getAsJsonObject().get("resource").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pageList"), new TypeToken<List<IPage>>() {
            }.getType());
            return new EntryResourceLocation(list, name, new ResourceLocation(resource));
        }

        @Override
        public void addData(JsonObject jsonObject, EntryResourceLocation src, JsonSerializationContext context) {
            jsonObject.add("unlocEntryName", context.serialize(src.unlocEntryName));
            jsonObject.add("resource", context.serialize(src.image.toString()));
            jsonObject.add("pageList", context.serialize(src.pageList));
        }
    };

    // Categories

    public static TypeReaders<Category> CATEGORY_BASE = new TypeReaders<Category>(Category.class) {
        @Override
        public Category deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map<ResourceLocation, EntryAbstract> entries = context.deserialize(json.getAsJsonObject().get("entries"), new TypeToken<Map<ResourceLocation, EntryAbstract>>() {
            }.getType());
            String name = json.getAsJsonObject().get("unlocCategoryName").getAsString();
            return new Category(entries, name);
        }

        @Override
        public void addData(JsonObject jsonObject, Category src, JsonSerializationContext context) {
            jsonObject.add("unlocCategoryName", context.serialize(src.unlocCategoryName));
            jsonObject.add("entries", context.serialize(src.entries));
        }
    };

    public static TypeReaders<CategoryItemStack> CATEGORY_ITEMSTACK = new TypeReaders<CategoryItemStack>(CategoryItemStack.class) {
        @Override
        public CategoryItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map<ResourceLocation, EntryAbstract> entries = context.deserialize(json.getAsJsonObject().get("entries"), new TypeToken<Map<ResourceLocation, EntryAbstract>>() {
            }.getType());
            String name = json.getAsJsonObject().get("unlocCategoryName").getAsString();
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("itemStack"), ItemStack.class);
            return new CategoryItemStack(entries, name, stack);
        }

        @Override
        public void addData(JsonObject jsonObject, CategoryItemStack src, JsonSerializationContext context) {
            jsonObject.add("unlocCategoryName", context.serialize(src.unlocCategoryName));
            jsonObject.add("itemStack", context.serialize(src.stack));
            jsonObject.add("entries", context.serialize(src.entries));
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
