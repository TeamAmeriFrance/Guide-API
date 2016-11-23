package amerifrance.guideapi.api.impl.abstraction;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiHome;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CategoryAbstract {

    public Map<ResourceLocation, EntryAbstract> entries = new HashMap<ResourceLocation, EntryAbstract>();
    public String name;

    public CategoryAbstract(Map<ResourceLocation, EntryAbstract> entries, String name) {
        this.entries = entries;
        this.name = name;
    }

    public void addEntry(ResourceLocation key, EntryAbstract entry) {
        entries.put(key, entry);
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

    public String getLocalizedName() {
        return TextHelper.localizeEffect(name);
    }

    public List<String> getTooltip() {
        List<String> list = new ArrayList<String>();
        list.add(getLocalizedName());
        return list;
    }

    @SideOnly(Side.CLIENT)
    public abstract void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem);

    @SideOnly(Side.CLIENT)
    public abstract void drawExtras(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem);

    public abstract boolean canSee(EntityPlayer player, ItemStack bookStack);

    @SideOnly(Side.CLIENT)
    public abstract void onLeftClicked(Book book, int mouseX, int mouseY, EntityPlayer player, ItemStack bookStack);

    @SideOnly(Side.CLIENT)
    public abstract void onRightClicked(Book book, int mouseX, int mouseY, EntityPlayer player, ItemStack bookStack);

    @SideOnly(Side.CLIENT)
    public abstract void onInit(Book book, GuiHome guiHome, EntityPlayer player, ItemStack bookStack);

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
