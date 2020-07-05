package de.maxanier.guideapi.api.util;

import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.entry.EntryItemStack;
import de.maxanier.guideapi.page.PageBrewingRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.brewing.BrewingRecipe;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class to build item or block info pages.
 * Create a {@link BookHelper} for your guide to use this.
 */
public class ItemInfoBuilder {


    private final boolean block;
    private final BookHelper bookHelper;
    private final Ingredient ingredient;
    private final ItemStack mainStack;
    private String name;
    private Object[] formats = new Object[0];
    private Object[] links = null;
    private boolean customName;
    @Nonnull
    private List<ResourceLocation> recipes = Collections.emptyList();
    @Nullable
    private ItemStack[] brewingStacks;

    /**
     * @param name       name used for translation keys
     * @param ingredient The relevant item stack. Used for display and strings.
     * @param block      If this entry is a about a block or not
     */
    protected ItemInfoBuilder(BookHelper bookHelper, Ingredient ingredient, ItemStack mainStack, String name, boolean block) {
        this.ingredient = ingredient;
        this.block = block;
        this.mainStack = mainStack;
        this.name = name;
        this.bookHelper = bookHelper;
    }

    /**
     * Add items that can be created in a brewing stand
     *
     * @return this
     */
    public ItemInfoBuilder brewingItems(Item... brewableItems) {
        this.brewingStacks = Arrays.stream(brewableItems).map(ItemStack::new).toArray(ItemStack[]::new);
        return this;
    }

    /**
     * Add stacks that can be created in a brewing stand
     *
     * @return this
     */
    public ItemInfoBuilder brewingStacks(ItemStack... brewableStacks) {
        this.brewingStacks = brewableStacks;
        return this;
    }

    /**
     * Builds the entry and adds it to the given map
     */
    public void build(Map<ResourceLocation, EntryAbstract> entries) {
        ArrayList<IPage> pages = new ArrayList<>();
        String base = bookHelper.getBaseKey() + (block ? ".blocks" : ".items") + "." + name;
        pages.addAll(PageHelper.pagesForLongText(bookHelper.localize(base + ".text", formats), ingredient));
        for (ResourceLocation id : recipes) {
            pages.add(bookHelper.getRecipePage(id));
        }
        if (brewingStacks != null) {
            for (ItemStack brew : brewingStacks) {
                BrewingRecipe r = bookHelper.getBrewingRecipe(brew);
                if (r == null) {
                    LogManager.getLogger().error("Could not find brewing recipe for {}", brew.toString());
                } else {
                    pages.add(new PageBrewingRecipe(r));
                }
            }
        }
        if (links != null) bookHelper.addLinks(pages, links);
        entries.put(new ResourceLocation(base), new EntryItemStack(pages, new TranslationTextComponent(customName ? base : mainStack.getTranslationKey()), mainStack));
    }

    /**
     * Add recipes
     * String ids are prefixed with your modid
     *
     * @param modIDs without namespace prefix
     * @return this
     */
    public ItemInfoBuilder recipes(String... modIDs) {
        this.recipes = Arrays.stream(modIDs).map(id -> new ResourceLocation(bookHelper.getModid(), id)).collect(Collectors.toList());
        return this;
    }

    /**
     * Add recipes
     *
     * @param ids the ids of the recipes to be displayeed
     */
    public ItemInfoBuilder recipes(ResourceLocation... ids) {
        this.recipes = Arrays.asList(ids);
        return this;
    }

    /**
     * Adds format arguments which are used when translating the description
     */
    public ItemInfoBuilder setFormats(Object... formats) {
        this.formats = formats;
        return this;
    }

    /**
     * Set's the name used for unloc strings
     */
    public ItemInfoBuilder setKeyName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets links that are added to the description pages.
     * See {@link BookHelper#addLinks(List, Object...)}
     */
    public ItemInfoBuilder setLinks(Object... links) {
        this.links = links;
        return this;
    }

    /**
     * Use a custom name (basekey.name) instead of the translated Item/Block name for the entry title
     */
    public ItemInfoBuilder useCustomEntryName() {
        customName = true;
        return this;
    }
}
