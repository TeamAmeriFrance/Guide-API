package de.maxanier.guideapi.api.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.IRecipeRenderer;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.page.PageHolderWithLinks;
import de.maxanier.guideapi.page.PageIRecipe;
import de.maxanier.guideapi.page.PageJsonRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.ForgeI18n;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Assists with book content creation and allows using {@link ItemInfoBuilder}.
 * Create one instant for your book using the {@link BookHelper.Builder}.
 * <p>
 * If you want to create links between entries, add links to pages with {@link BookHelper#addLinks(List, Object...)} and register your entries with {@link BookHelper#registerLinkablePages(List)}.
 */
public class BookHelper {

    private final Logger LOGGER;
    private final String modid;
    private final String baseKey;
    private final Function<IRecipe<?>, IRecipeRenderer> recipeRendererSupplier;
    private final BiFunction<String, Object[], ITextComponent> localizer;
    private final Function<Block, String> blockNameMapper;
    private final Function<Item, String> itemNameMapper;
    private final Map<ResourceLocation, EntryAbstract> links = Maps.newHashMap();

    private BookHelper(String modid, String baseKey, Function<IRecipe<?>, IRecipeRenderer> recipeRendererSupplier, BiFunction<String, Object[], ITextComponent> localizer, Function<Block, String> blockNameMapper, Function<Item, String> itemNameMapper) {
        LOGGER = LogManager.getLogger("BookHelper_" + modid);
        this.modid = modid;
        this.baseKey = baseKey;
        this.recipeRendererSupplier = recipeRendererSupplier;
        this.localizer = localizer;
        this.blockNameMapper = blockNameMapper;
        this.itemNameMapper = itemNameMapper;
    }

    /**
     * Converts the given pages to {@link PageHolderWithLinks} and adds the given links
     * Links can be
     * - ResourceLocation: Link to an entry by its id. Entries have to be registered by {@link BookHelper#registerLinkablePages(List)}
     * - EntryAbstract: Link directly to an entry object
     * - {@link PageHolderWithLinks.URLLink}: Link to a web url
     *
     * @return The SAME list with the old pages removed and the new page holders added
     */
    public List<IPage> addLinks(List<IPage> pages, Object... links) {
        List<PageHolderWithLinks> linkPages = Lists.newArrayList();
        for (IPage p : pages) {
            linkPages.add(new PageHolderWithLinks(this, p));
        }

        for (Object l : links) {
            if (l instanceof ResourceLocation) {
                for (PageHolderWithLinks p : linkPages) {
                    p.addLink((ResourceLocation) l);
                }
            } else if (l instanceof EntryAbstract) {
                for (PageHolderWithLinks p : linkPages) {
                    p.addLink((EntryAbstract) l);
                }
            } else if (l instanceof PageHolderWithLinks.URLLink) {
                for (PageHolderWithLinks p : linkPages) {
                    p.addLink((PageHolderWithLinks.URLLink) l);
                }
            } else {
                LOGGER.warn("Given link object cannot be linked {}", l);
            }
        }
        pages.clear();
        pages.addAll(linkPages);
        return pages;
    }

    /**
     * Return a brewing recipe that results in the given stack
     *
     * @return Null if none found
     */
    @Nullable
    public BrewingRecipe getBrewingRecipe(ItemStack stack) {
        return (BrewingRecipe) BrewingRecipeRegistry.getRecipes().stream().filter(iBrewingRecipe -> iBrewingRecipe instanceof BrewingRecipe && ItemStack.areItemStacksEqual(((BrewingRecipe) iBrewingRecipe).getOutput(), stack)).findFirst().orElse(null);
    }

    @Nullable
    public EntryAbstract getLinkedEntry(ResourceLocation location) {
        return links.get(location);
    }

    public IPage getRecipePage(ResourceLocation id) {
        return new PageJsonRecipe(id, recipeRendererSupplier);
    }

    /**
     * Cycles through all the matching item stacks
     *
     * @param mainStack is used to determine the translation keys.
     * @param block     Whether to use "block" or "item" translation keys
     * @return A ItemInfoBuilder for the given itemsstacks.
     */
    public ItemInfoBuilder info(boolean block, Ingredient ingredient, ItemStack mainStack) {
        Item item = mainStack.getItem();
        String name = item instanceof BlockItem ? blockNameMapper.apply(((BlockItem) item).getBlock()) : itemNameMapper.apply(item);
        return new ItemInfoBuilder(this, ingredient, mainStack, name, block);
    }

    /**
     * The first item is used to determine the translation keys.
     *
     * @param items The resulting page will cycle through the items in the given order
     * @return A ItemInfoBuilder for the given items.
     */
    public ItemInfoBuilder info(Item... items) {
        assert items.length > 0;
        Item i0 = items[0];
        String name = itemNameMapper.apply(i0);
        return new ItemInfoBuilder(this, Ingredient.fromItems(items), new ItemStack(i0), name, false);
    }

    /**
     * The first stack is used to determine the translation keys.
     *
     * @param stacks The resulting page will cycle through the stacks in the given order
     * @param block  Whether to use "block" or "item" translation keys
     * @return A ItemInfoBuilder for the given itemsstacks.
     */
    public ItemInfoBuilder info(boolean block, ItemStack... stacks) {
        assert stacks.length > 0;
        ItemStack i0 = stacks[0];
        Item item = i0.getItem();
        String name = item instanceof BlockItem ? blockNameMapper.apply(((BlockItem) item).getBlock()) : itemNameMapper.apply(item);
        return new ItemInfoBuilder(this, Ingredient.fromStacks(stacks), i0, name, block);
    }

    /**
     * The first block is used to determine the translation keys.
     *
     * @param blocks The resulting page will cycle through the blocks in the given order
     * @return A ItemInfoBuilder for the given blocks.
     */
    public ItemInfoBuilder info(Block... blocks) {
        assert blocks.length > 0;
        Block i0 = blocks[0];
        String name = blockNameMapper.apply(i0);
        return new ItemInfoBuilder(this, Ingredient.fromItems(blocks), new ItemStack(i0), name, true);
    }

    public ITextComponent localize(String key, Object... formats) {
        return localizer.apply(key, formats);
    }

    /**
     * After building your categories register them here, so the links for the individual pages can be resolved
     */
    public void registerLinkablePages(List<CategoryAbstract> categories) {
        for (CategoryAbstract c : categories) {
            this.links.putAll(c.entries);
        }
    }

    protected String getBaseKey() {
        return baseKey;
    }

    protected String getModid() {
        return modid;
    }

    public static class Builder {
        private final String modid;
        private String baseKey;
        private Function<IRecipe<?>, IRecipeRenderer> recipeRendererSupplier = PageIRecipe::getRenderer;
        private BiFunction<String, Object[], ITextComponent> localizer = TranslationTextComponent::new;
        private Function<Block, String> blockNameMapper = (block -> block.getRegistryName().getPath());
        private Function<Item, String> itemNameMapper = (item -> item.getRegistryName().getPath());

        public Builder(String modid) {
            this.modid = modid;
            this.baseKey = "guide." + modid;
        }

        public BookHelper build() {
            return new BookHelper(modid, baseKey, recipeRendererSupplier, localizer, blockNameMapper, itemNameMapper);
        }

        /**
         * Set a base key from which the item/block description translation keys are derived
         *
         * @return this
         */
        public BookHelper.Builder setBaseKey(String baseKey) {
            this.baseKey = baseKey;
            return this;
        }

        /**
         * Set a custom block -> name mapper here. The name is used as part of the translations key.
         * By default the registry name path is used.
         * Can be interesting if you for example have a lot of blocks that exist in different variants that should be treated as one in the guide, but implement a common interface.
         * Otherwise you can always use {@link ItemInfoBuilder#setKeyName(String)}
         *
         * @return this
         */
        public Builder setBlockNameMapper(Function<Block, String> blockNameMapper) {
            this.blockNameMapper = blockNameMapper;
            return this;
        }

        /**
         * Set a custom block -> name mapper here. The name is used as part of the translations key.
         * By default the registry name path is used.
         * Can be interesting if you for example have a lot of items that exist in different variants that should be treated as one in the guide, but implement a common interface.
         * Otherwise you can always use {@link ItemInfoBuilder#setKeyName(String)}
         *
         * @return this
         */
        public Builder setItemNameMapper(Function<Item, String> itemNameMapper) {
            this.itemNameMapper = itemNameMapper;
            return this;
        }

        /**
         * Set a custom method used to localize strings instead of {@link ForgeI18n#parseMessage(String, Object...)}
         *
         * @param localizer Accept translation key and formats
         * @return this
         */
        public BookHelper.Builder setLocalizer(BiFunction<String, Object[], ITextComponent> localizer) {
            this.localizer = localizer;
            return this;
        }

        /**
         * If you have custom recipe renderers, register a render supplier here
         *
         * @param rendererSupplier Should provide a recipe renderer for any used recipe
         * @return this
         */
        public BookHelper.Builder setRecipeRendererSupplier(Function<IRecipe<?>, IRecipeRenderer> rendererSupplier) {
            this.recipeRendererSupplier = rendererSupplier;
            return this;
        }

    }


}
