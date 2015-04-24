package amerifrance.guideapi.proxies;

import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.gui.GuiHome;
import cpw.mods.fml.common.network.IGuiHandler;
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
            NBTTagCompound tagCompound = stack.stackTagCompound;
            if (tagCompound.hasKey(GuiBase.ENTRY_TAG) && tagCompound.hasKey(GuiBase.CATEGORY_TAG)) {
                CategoryAbstract category = book.categoryList.get(tagCompound.getInteger(GuiBase.CATEGORY_TAG));
                EntryAbstract entry = category.entryList.get(tagCompound.getInteger(GuiBase.ENTRY_TAG));
                int pageNumber = tagCompound.getInteger(GuiBase.PAGE_TAG);
                GuiEntry guiEntry = new GuiEntry(book, category, entry, player, stack);
                guiEntry.pageNumber = pageNumber;
                return guiEntry;
            } else if (tagCompound.hasKey(GuiBase.CATEGORY_TAG)) {
                CategoryAbstract category = book.categoryList.get(tagCompound.getInteger(GuiBase.CATEGORY_TAG));
                int entryPage = tagCompound.getInteger(GuiBase.ENTRY_PAGE_TAG);
                GuiCategory guiCategory = new GuiCategory(book, category, player, stack);
                guiCategory.entryPage = entryPage;
                return guiCategory;
            } else {
                int categoryNumber = tagCompound.getInteger(GuiBase.CATEGORY_PAGE_TAG);
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
}
