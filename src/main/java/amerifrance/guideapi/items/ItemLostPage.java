package amerifrance.guideapi.items;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.api.registry.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.NBTBookTags;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class ItemLostPage extends Item {

    public ItemLostPage() {
        setUnlocalizedName("LostPage");
        setCreativeTab(GuideAPI.tabGuide);
        setHasSubtypes(true);
    }

    public static boolean bookHasPage(ItemStack bookStack, int book, int category, int entry, int pageNumber) {
        if (!bookStack.hasTagCompound())
            return false;

        return bookStack.getTagCompound().getBoolean(book + ":" + category + ":" + entry + ":" + pageNumber);
    }

    public static void setPage(ItemStack pageStack, int book, int category, int entry, int pageNumber) {
        if (!pageStack.hasTagCompound())
            pageStack.setTagCompound(new NBTTagCompound());

        pageStack.getTagCompound().setString(NBTBookTags.KEY_TAG, book + ":" + category + ":" + entry + ":" + pageNumber);
    }

    public static Object[] getPageCharacteristics(ItemStack pageStack) {
        if (!pageStack.hasTagCompound() || !pageStack.getTagCompound().hasKey(NBTBookTags.KEY_TAG))
            return null;

        Object[] objects = new Object[4];
        String[] split = pageStack.getTagCompound().getString(NBTBookTags.KEY_TAG).split(":");

        try {
            objects[0] = GuideRegistry.getBook(NumberUtils.toInt(split[0]));
            objects[1] = GuideRegistry.getBook(NumberUtils.toInt(split[0])).categoryList.get(NumberUtils.toInt(split[1]));
            objects[2] = GuideRegistry.getBook(NumberUtils.toInt(split[0])).categoryList.get(NumberUtils.toInt(split[1])).entryList.get(NumberUtils.toInt(split[2]));
            objects[3] = NumberUtils.toInt(split[3]) + 1;
        } catch (IndexOutOfBoundsException e) {
            // Swallow
        }

        return objects;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (!GuideRegistry.isEmpty() && getPageCharacteristics(stack) != null) {
            Object[] objects = getPageCharacteristics(stack);
            list.add(String.format(StatCollector.translateToLocal("text.book"), objects[0] != null ? ((Book) objects[0]).getLocalizedBookTitle() : "null"));
            list.add(String.format(StatCollector.translateToLocal("text.category"), objects[1] != null ? ((CategoryAbstract) objects[1]).getLocalizedName() : "null"));
            list.add(String.format(StatCollector.translateToLocal("text.entry"), objects[2] != null ? ((EntryAbstract) objects[2]).getLocalizedName() : "null"));
            list.add(String.format(StatCollector.translateToLocal("text.page"), objects[3] != null ? objects[3] : "null"));
        }
    }
}
