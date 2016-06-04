package amerifrance.guideapi.util.json.serialization;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.ITypeReader;
import amerifrance.guideapi.api.impl.Category;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.entry.EntryResourceLocation;
import amerifrance.guideapi.page.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TypeReaders<T> implements ITypeReader<T> {

    // Categories

    public static TypeReaders<CategoryAbstract> CATEGORY_ABSTRACT = new TypeReaders<CategoryAbstract>(CategoryAbstract.class) {
        @Override
        public CategoryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map<String, EntryAbstract> stringEntries = context.deserialize(json.getAsJsonObject().get("entries"), new TypeToken<Map<String, EntryAbstract>>() {
            }.getType());

            Map<ResourceLocation, EntryAbstract> entries = new HashMap<ResourceLocation, EntryAbstract>();
            for (Map.Entry<String, EntryAbstract> entry : stringEntries.entrySet())
                entries.put(new ResourceLocation(entry.getKey()), entry.getValue());

            String name = json.getAsJsonObject().get("name").getAsString();
            return new Category(entries, name);
        }

        @Override
        public void addData(JsonObject jsonObject, CategoryAbstract src, JsonSerializationContext context) {
            jsonObject.add("name", context.serialize(src.unlocCategoryName));
            jsonObject.add("entries", context.serialize(src.entries));
        }
    };

    public static TypeReaders<Category> CATEGORY_BASE = new TypeReaders<Category>(Category.class) {
        @Override
        public Category deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map<String, EntryAbstract> stringEntries = context.deserialize(json.getAsJsonObject().get("entries"), new TypeToken<Map<String, EntryAbstract>>() {
            }.getType());

            Map<ResourceLocation, EntryAbstract> entries = new HashMap<ResourceLocation, EntryAbstract>();
            for (Map.Entry<String, EntryAbstract> entry : stringEntries.entrySet())
                entries.put(new ResourceLocation(entry.getKey()), entry.getValue());

            String name = json.getAsJsonObject().get("name").getAsString();
            return new Category(entries, name);
        }

        @Override
        public void addData(JsonObject jsonObject, Category src, JsonSerializationContext context) {
            jsonObject.add("name", context.serialize(src.unlocCategoryName));
            jsonObject.add("entries", context.serialize(src.entries));
        }
    };

    public static TypeReaders<CategoryItemStack> CATEGORY_ITEMSTACK = new TypeReaders<CategoryItemStack>(CategoryItemStack.class) {
        @Override
        public CategoryItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map<String, EntryAbstract> stringEntries = context.deserialize(json.getAsJsonObject().get("entries"), new TypeToken<Map<String, EntryAbstract>>() {
            }.getType());

            Map<ResourceLocation, EntryAbstract> entries = new HashMap<ResourceLocation, EntryAbstract>();
            for (Map.Entry<String, EntryAbstract> entry : stringEntries.entrySet())
                entries.put(new ResourceLocation(entry.getKey()), entry.getValue());

            String name = json.getAsJsonObject().get("name").getAsString();
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("stack"), ItemStack.class);
            return new CategoryItemStack(entries, name, stack);
        }

        @Override
        public void addData(JsonObject jsonObject, CategoryItemStack src, JsonSerializationContext context) {
            jsonObject.add("name", context.serialize(src.unlocCategoryName));
            jsonObject.add("stack", context.serialize(src.stack));
            jsonObject.add("entries", context.serialize(src.entries));
        }
    };

    // Entries

    public static TypeReaders<EntryAbstract> ENTRY_ABSTRACT = new TypeReaders<EntryAbstract>(EntryAbstract.class) {
        @Override
        public EntryAbstract deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("name").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pages"), new TypeToken<List<IPage>>() {
            }.getType());
            return new Entry(list, name);
        }

        @Override
        public void addData(JsonObject jsonObject, EntryAbstract src, JsonSerializationContext context) {
            jsonObject.add("name", context.serialize(src.unlocEntryName));
            jsonObject.add("pages", context.serialize(src.pageList));
        }
    };

    public static TypeReaders<Entry> ENTRY_BASE = new TypeReaders<Entry>(Entry.class) {
        @Override
        public Entry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("name").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pages"), new TypeToken<List<IPage>>() {
            }.getType());
            return new Entry(list, name);
        }

        @Override
        public void addData(JsonObject jsonObject, Entry src, JsonSerializationContext context) {
            jsonObject.add("name", context.serialize(src.unlocEntryName));
            jsonObject.add("pages", context.serialize(src.pageList));
        }
    };

    public static TypeReaders<Entry> ENTRY_TEXT = new TypeReaders<Entry>(Entry.class) {
        @Override
        public Entry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("name").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pages"), new TypeToken<List<IPage>>() {
            }.getType());
            return new Entry(list, name);
        }

        @Override
        public void addData(JsonObject jsonObject, Entry src, JsonSerializationContext context) {
            jsonObject.add("name", context.serialize(src.unlocEntryName));
            jsonObject.add("pages", context.serialize(src.pageList));
        }
    };

    public static TypeReaders<EntryItemStack> ENTRY_ITEMSTACK = new TypeReaders<EntryItemStack>(EntryItemStack.class) {
        @Override
        public EntryItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("name").getAsString();
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("stack"), ItemStack.class);
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pages"), new TypeToken<List<IPage>>() {
            }.getType());
            return new EntryItemStack(list, name, stack);
        }

        @Override
        public void addData(JsonObject jsonObject, EntryItemStack src, JsonSerializationContext context) {
            jsonObject.add("name", context.serialize(src.unlocEntryName));
            jsonObject.add("stack", context.serialize(src.stack));
            jsonObject.add("pages", context.serialize(src.pageList));
        }
    };

    public static TypeReaders<EntryResourceLocation> ENTRY_IMAGE = new TypeReaders<EntryResourceLocation>(EntryResourceLocation.class) {
        @Override
        public EntryResourceLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String name = json.getAsJsonObject().get("name").getAsString();
            String resource = json.getAsJsonObject().get("resource").getAsString();
            List<IPage> list = context.deserialize(json.getAsJsonObject().get("pages"), new TypeToken<List<IPage>>() {
            }.getType());
            return new EntryResourceLocation(list, name, new ResourceLocation(resource));
        }

        @Override
        public void addData(JsonObject jsonObject, EntryResourceLocation src, JsonSerializationContext context) {
            jsonObject.add("name", context.serialize(src.unlocEntryName));
            jsonObject.add("resource", context.serialize(src.image.toString()));
            jsonObject.add("pages", context.serialize(src.pageList));
        }
    };

    // Pages

    public static TypeReaders<IPage> PAGE_IPAGE = new TypeReaders<IPage>(IPage.class) {
        @Override
        public IPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ItemStack input = context.deserialize(json.getAsJsonObject().get("input"), ItemStack.class);
            return new PageFurnaceRecipe(input);
        }

        @Override
        public void addData(JsonObject jsonObject, IPage src, JsonSerializationContext context) {

        }
    };

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
            ItemStack stack = context.deserialize(json.getAsJsonObject().get("stack"), ItemStack.class);
            return new PageItemStack(text, stack);
        }

        @Override
        public void addData(JsonObject jsonObject, PageItemStack src, JsonSerializationContext context) {
            jsonObject.add("stack", context.serialize(src.stack));
            jsonObject.add("text", context.serialize(src.draw));

        }
    };

    public static TypeReaders<PageText> PAGE_TEXT = new TypeReaders<PageText>(PageText.class) {
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
            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(json.getAsJsonObject().get("sound").getAsString()));
            IPage pageToEmulate = context.deserialize(json.getAsJsonObject().get("pageToEmulate"), IPage.class);
            return new PageSound(pageToEmulate, sound);
        }

        @Override
        public void addData(JsonObject jsonObject, PageSound src, JsonSerializationContext context) {
            jsonObject.addProperty("sound", src.sound.getRegistryName().toString());
            jsonObject.add("pageToEmulate", context.serialize(src.pageToEmulate));
        }
    };

    private Class<? extends T> type;

    private TypeReaders(Class<? extends T> type) {
        this.type = type;
        GuideAPI.addTypeReader(this);
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

    public static void init() {
        // Run those static initializers
    }
}
