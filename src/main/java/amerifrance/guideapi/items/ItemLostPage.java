package amerifrance.guideapi.items;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.NBTBookTags;
import amerifrance.guideapi.util.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class ItemLostPage extends Item {

    public ItemLostPage() {
        this.setCreativeTab(GuideAPI.tabGuide);
        this.setUnlocalizedName("LostPage");
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public static boolean bookHasPage(ItemStack bookStack, int book, int category, int entry, int pageNumber) {
        if (!bookStack.hasTagCompound())
            return false;

        return bookStack.stackTagCompound.getBoolean(book + ":" + category + ":" + entry + ":" + pageNumber);
    }

    public static void setPage(ItemStack pageStack, int book, int category, int entry, int pageNumber) {
        if (!pageStack.hasTagCompound())
            pageStack.setTagCompound(new NBTTagCompound());

        pageStack.stackTagCompound.setString(NBTBookTags.KEY_TAG, book + ":" + category + ":" + entry + ":" + pageNumber);
    }

    public static Object[] getPageCharacteristics(ItemStack pageStack) {
        if (!pageStack.hasTagCompound() || !pageStack.stackTagCompound.hasKey(NBTBookTags.KEY_TAG))
            return null;

        Object[] objects = new Object[4];
        String[] split = pageStack.stackTagCompound.getString(NBTBookTags.KEY_TAG).split(":");

        objects[0] = GuideRegistry.getBook(NumberUtils.toInt(split[0]));
        objects[1] = GuideRegistry.getBook(NumberUtils.toInt(split[0])).categoryList.get(NumberUtils.toInt(split[1]));
        objects[2] = GuideRegistry.getBook(NumberUtils.toInt(split[0])).categoryList.get(NumberUtils.toInt(split[1])).entryList.get(NumberUtils.toInt(split[2]));
        objects[3] = NumberUtils.toInt(split[3]);

        return objects;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() < stack.getItemDamage()) {
            Object[] objects = getPageCharacteristics(stack);
            list.add(Utils.localizeFormatted("text.book", ((Book) objects[0]).getLocalizedBookTitle()));
            list.add(Utils.localizeFormatted("text.category", ((CategoryAbstract) objects[1]).getLocalizedName()));
            list.add(Utils.localizeFormatted("text.entry", ((EntryAbstract) objects[3]).getLocalizedName()));
            list.add(Utils.localizeFormatted("text.page", objects[3]));
        }
    }
}
