package de.maxanier.guideapi.api.impl.abstraction;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.HomeScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;

public abstract class CategoryAbstract {

    public final Map<ResourceLocation, EntryAbstract> entries;
    public final ITextComponent name;
    private String keyBase;

    public CategoryAbstract(Map<ResourceLocation, EntryAbstract> entries, ITextComponent name) {
        this.entries = entries;
        this.name = name;
    }

    public CategoryAbstract(ITextComponent name) {
        this(Maps.newLinkedHashMap(), name);
    }

    /**
     * Adds an entry to this category.
     *
     * @param key   - The key of the entry to add.
     * @param entry - The entry to add.
     */
    public void addEntry(ResourceLocation key, EntryAbstract entry) {
        entries.put(key, entry);
    }

    /**
     * Adds an entry to this category.
     * <p>
     * Shorthand of {@link #addEntry(ResourceLocation, EntryAbstract)}. Requires {@link #withKeyBase(String)} to have been called.
     *
     * @param key   - The key of the entry to add.
     * @param entry - The entry to add.
     */
    public void addEntry(String key, EntryAbstract entry) {
        if (Strings.isNullOrEmpty(keyBase))
            throw new RuntimeException("keyBase in category with name '" + name + "' must be set.");

        addEntry(new ResourceLocation(keyBase, key), entry);
    }

    public void removeEntry(ResourceLocation key) {
        entries.remove(key);
    }

    public void addEntries(Map<ResourceLocation, EntryAbstract> entries) {
        this.entries.putAll(entries);
    }

    public void removeEntries(List<ResourceLocation> keys) {
        for (ResourceLocation key : keys)
            entries.remove(key);
    }

    /**
     * Obtains an entry from this category.
     * <p>
     * This <i>can</i> be null, however it is not marked as nullable to avoid annoying IDE warnings. I am making the
     * assumption that this will only be called while creating the book and thus the caller knows it exists.
     * <p>
     * If you are calling at any other time, make sure to nullcheck this.
     *
     * @param key - The key of the entry to obtain.
     * @return the found entry.
     */
    public EntryAbstract getEntry(ResourceLocation key) {
        return entries.get(key);
    }

    /**
     * Obtains an entry from this category.
     * <p>
     * Shorthand of {@link #getEntry(ResourceLocation)}. Requires {@link #withKeyBase(String)} to have been called.
     * <p>
     * This <i>can</i> be null, however it is not marked as nullable to avoid annoying IDE warnings. I am making the
     * assumption that this will only be called while creating the book and thus the caller knows it exists.
     * <p>
     * If you are calling at any other time, make sure to nullcheck this.
     *
     * @param key - The key of the entry to obtain.
     * @return the found entry.
     */
    public EntryAbstract getEntry(String key) {
        if (Strings.isNullOrEmpty(keyBase))
            throw new RuntimeException("keyBase in category with name '" + name.getString() + "' must be set.");

        return getEntry(new ResourceLocation(keyBase, key));
    }

    /**
     * Sets the domain to use for all ResourceLocation keys passed through {@link #getEntry(String)} and
     * {@link #addEntry(String, EntryAbstract)}
     * <p>
     * Required in order to use those.
     *
     * @param keyBase - The base domain for this entry.
     * @return self for chaining.
     */
    public CategoryAbstract withKeyBase(String keyBase) {
        this.keyBase = keyBase;
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void draw(MatrixStack stack, Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, BaseScreen guiBase, boolean drawOnLeft, ItemRenderer renderItem);

    @OnlyIn(Dist.CLIENT)
    public abstract void drawExtras(MatrixStack stack, Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, BaseScreen guiBase, boolean drawOnLeft, ItemRenderer renderItem);

    /**
     * Obtains a localized copy of this category's name.
     *
     * @return a localized copy of this category's name.
     */
    public ITextComponent getName() {
        return name;
    }

    public List<ITextComponent> getTooltip() {
        return Lists.newArrayList(getName());
    }

    public abstract boolean canSee(PlayerEntity player, ItemStack bookStack);

    @OnlyIn(Dist.CLIENT)
    public abstract void onLeftClicked(Book book, double mouseX, double mouseY, PlayerEntity player, ItemStack bookStack);

    @OnlyIn(Dist.CLIENT)
    public abstract void onRightClicked(Book book, double mouseX, double mouseY, PlayerEntity player, ItemStack bookStack);

    @OnlyIn(Dist.CLIENT)
    public abstract void onInit(Book book, HomeScreen guiHome, PlayerEntity player, ItemStack bookStack);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryAbstract that = (CategoryAbstract) o;
        if (entries != null ? !entries.equals(that.entries) : that.entries != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = entries != null ? entries.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
