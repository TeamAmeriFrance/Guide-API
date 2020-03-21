package amerifrance.guideapi.proxy;

import api.GuideAPI;
import api.IGuideItem;
import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import api.util.NBTBookTags;
import amerifrance.guideapi.gui.CategoryScreen;
import amerifrance.guideapi.gui.EntryScreen;
import amerifrance.guideapi.gui.HomeScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
        ItemStack bookStack = player.getHeldItem(Hand.values()[x]);

        if (!bookStack.isEmpty() && bookStack.getItem() instanceof IGuideItem) {
            Book book = GuideAPI.getIndexedBooks().get(ID);
            try {
                if (bookStack.hasTag()) {
                    CompoundNBT tagCompound = bookStack.getTag();
                    if (tagCompound.contains(NBTBookTags.ENTRY_TAG) && tagCompound.contains(NBTBookTags.CATEGORY_TAG)) {
                        CategoryAbstract category = book.getCategoryList().get(tagCompound.getInt(NBTBookTags.CATEGORY_TAG));
                        EntryAbstract entry = category.entries.get(new ResourceLocation(tagCompound.getString(NBTBookTags.ENTRY_TAG)));
                        int pageNumber = tagCompound.getInt(NBTBookTags.PAGE_TAG);
                        EntryScreen guiEntry = new EntryScreen(book, category, entry, player, bookStack);
                        guiEntry.pageNumber = pageNumber;
                        return guiEntry;
                    } else if (tagCompound.contains(NBTBookTags.CATEGORY_TAG)) {
                        CategoryAbstract category = book.getCategoryList().get(tagCompound.getInt(NBTBookTags.CATEGORY_TAG));
                        int entryPage = tagCompound.getInt(NBTBookTags.ENTRY_PAGE_TAG);
                        CategoryScreen guiCategory = new CategoryScreen(book, category, player, bookStack, null);
                        guiCategory.entryPage = entryPage;
                        return guiCategory;
                    } else {
                        int categoryNumber = tagCompound.getInt(NBTBookTags.CATEGORY_PAGE_TAG);
                        HomeScreen guiHome = new HomeScreen(book, player, bookStack);
                        guiHome.categoryPage = categoryNumber;
                        return guiHome;
                    }
                }
            } catch (Exception e) {
                // No-op: If the linked content doesn't exist anymore
            }

            return new HomeScreen(book, player, bookStack);
        }

        return null;
    }

    public void playSound(SoundEvent sound) {
    }

    public void openEntry(Book book, CategoryAbstract categoryAbstract, EntryAbstract entryAbstract, PlayerEntity player, ItemStack stack) {
    }

    public void initColors() {
    }
}
