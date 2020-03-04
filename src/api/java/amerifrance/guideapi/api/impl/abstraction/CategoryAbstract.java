package amerifrance.guideapi.api.impl.abstraction;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiHome;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

public abstract class CategoryAbstract {

    public final Map<ResourceLocation, EntryAbstract> entries;
    public final String name;
    private String keyBase;

    public CategoryAbstract(Map<ResourceLocation, EntryAbstract> entries, String name) {
        this.entries = entries;
        this.name = name;
    }

    public CategoryAbstract(String name) {
        this(Maps.<ResourceLocation, EntryAbstract>newLinkedHashMap(), name);
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
            if (entries.containsKey(key))
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
            throw new RuntimeException("keyBase in category with name '" + name + "' must be set.");

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

    /**
     * Obtains a localized copy of this category's name.
     *
     * @return a localized copy of this category's name.
     */
    public String getLocalizedName() {
        return TextHelper.localizeEffect(name);
    }

    public List<String> getTooltip() {
        return Lists.newArrayList(getLocalizedName());
    }

    @SideOnly(Side.CLIENT)
    public abstract void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem);

    @SideOnly(Side.CLIENT)
    public abstract void drawExtras(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem);

    public abstract boolean canSee(PlayerEntity player, ItemStack bookStack);

    @SideOnly(Side.CLIENT)
    public abstract void onLeftClicked(Book book, int mouseX, int mouseY, PlayerEntity player, ItemStack bookStack);

    @SideOnly(Side.CLIENT)
    public abstract void onRightClicked(Book book, int mouseX, int mouseY, PlayerEntity player, ItemStack bookStack);

    @SideOnly(Side.CLIENT)
    public abstract void onInit(Book book, GuiHome guiHome, PlayerEntity player, ItemStack bookStack);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryAbstract that = (CategoryAbstract) o;
        if (entries != null ? !entries.equals(that.entries) : that.entries != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = entries != null ? entries.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
