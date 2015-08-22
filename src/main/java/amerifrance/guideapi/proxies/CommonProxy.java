package amerifrance.guideapi.proxies;

import amerifrance.guideapi.api.registry.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.NBTBookTags;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.gui.GuiHome;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        ItemStack stack = player.getHeldItem();
        Book book = GuideRegistry.getBook(ID);
        if (stack != null && stack.hasTagCompound()) {
            NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound.hasKey(NBTBookTags.ENTRY_TAG) && tagCompound.hasKey(NBTBookTags.CATEGORY_TAG)) {
                CategoryAbstract category = book.categoryList.get(tagCompound.getInteger(NBTBookTags.CATEGORY_TAG));
                EntryAbstract entry = category.entryList.get(tagCompound.getInteger(NBTBookTags.ENTRY_TAG));
                int pageNumber = tagCompound.getInteger(NBTBookTags.PAGE_TAG);
                GuiEntry guiEntry = new GuiEntry(book, category, entry, player, stack);
                guiEntry.pageNumber = pageNumber;
                return guiEntry;
            } else if (tagCompound.hasKey(NBTBookTags.CATEGORY_TAG)) {
                CategoryAbstract category = book.categoryList.get(tagCompound.getInteger(NBTBookTags.CATEGORY_TAG));
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
        return new GuiHome(book, player, stack);
    }

    public void playSound(ResourceLocation sound) {

    }

    public void openEntry(Book book, CategoryAbstract categoryAbstract, EntryAbstract entryAbstract, EntityPlayer player, ItemStack stack) {

    }

    public void registerRenders() {

    }
}
