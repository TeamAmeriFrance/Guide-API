package amerifrance.guideapi.proxy;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IGuideItem;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.NBTBookTags;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.gui.GuiHome;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        ItemStack stack = player.getHeldItemOffhand();
        if (stack == null || !(stack.getItem() instanceof IGuideItem))
            stack = player.getHeldItemMainhand();

        if (stack != null && stack.getItem() instanceof IGuideItem) {
            Book book = GuideAPI.BOOKS.getValues().get(ID);
            try {
                if (stack.hasTagCompound()) {
                    NBTTagCompound tagCompound = stack.getTagCompound();
                    if (tagCompound.hasKey(NBTBookTags.ENTRY_TAG) && tagCompound.hasKey(NBTBookTags.CATEGORY_TAG)) {
                        CategoryAbstract category = book.getCategoryList().get(tagCompound.getInteger(NBTBookTags.CATEGORY_TAG));
                        EntryAbstract entry = category.entries.get(new ResourceLocation(tagCompound.getString(NBTBookTags.ENTRY_TAG)));
                        int pageNumber = tagCompound.getInteger(NBTBookTags.PAGE_TAG);
                        GuiEntry guiEntry = new GuiEntry(book, category, entry, player, stack);
                        guiEntry.pageNumber = pageNumber;
                        return guiEntry;
                    } else if (tagCompound.hasKey(NBTBookTags.CATEGORY_TAG)) {
                        CategoryAbstract category = book.getCategoryList().get(tagCompound.getInteger(NBTBookTags.CATEGORY_TAG));
                        int entryPage = tagCompound.getInteger(NBTBookTags.ENTRY_PAGE_TAG);
                        GuiCategory guiCategory = new GuiCategory(book, category, player, stack);
                        guiCategory.entryPage = entryPage;
                        return guiCategory;
                    } else {
                        int categoryNumber = tagCompound.getInteger(NBTBookTags.CATEGORY_PAGE_TAG);
                        GuiHome guiHome = new GuiHome(book, player, stack);
                        guiHome.categoryPage = categoryNumber;
                        return guiHome;
                    }
                }
            } catch (Exception e) {
                // No-op: Catching if the saved page/entry/category doesn't exist anymore
            }

            return new GuiHome(book, player, stack);
        }

        return null;
    }

    public void playSound(ResourceLocation sound) {
    }

    public void openEntry(Book book, CategoryAbstract categoryAbstract, EntryAbstract entryAbstract, EntityPlayer player, ItemStack stack) {
    }

    public void initColors() {
    }
}
