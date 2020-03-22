package amerifrance.guideapi.proxy;

import amerifrance.guideapi.gui.CategoryScreen;
import amerifrance.guideapi.gui.HomeScreen;
import api.BookEvent;
import api.GuideAPI;
import api.IGuideItem;
import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.gui.EntryScreen;
import api.util.NBTBookTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void playSound(SoundEvent sound) {
        Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(sound,1));
    }

    @Override
    public void openEntry(Book book, CategoryAbstract categoryAbstract, EntryAbstract entryAbstract, PlayerEntity player, ItemStack stack) {
        BookEvent.Open event = new BookEvent.Open(book, stack, player);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            player.sendStatusMessage(event.getCanceledText(), true);
            return;
        }

        Minecraft.getInstance().displayGuiScreen(new EntryScreen(book, categoryAbstract, entryAbstract, player, stack));
    }

    @Override
    public void initColors() {
        for (ItemStack bookStack : GuideAPI.getBookToStack().values()) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                IGuideItem guideItem = (IGuideItem) stack.getItem();
                if (guideItem.getBook(stack) != null && tintIndex == 0)
                    return guideItem.getBook(stack).getColor().getRGB();

                return -1;
            }, bookStack.getItem());
        }
    }

    @Override
    public void openGuidebook(PlayerEntity player, World world, Book book, ItemStack bookStack) {
        if (!bookStack.isEmpty() && bookStack.getItem() instanceof IGuideItem) {
            try {
                if (bookStack.hasTag()) {
                    CompoundNBT tagCompound = bookStack.getTag();
                    if (tagCompound.contains(NBTBookTags.ENTRY_TAG) && tagCompound.contains(NBTBookTags.CATEGORY_TAG)) {
                        CategoryAbstract category = book.getCategoryList().get(tagCompound.getInt(NBTBookTags.CATEGORY_TAG));
                        EntryAbstract entry = category.entries.get(new ResourceLocation(tagCompound.getString(NBTBookTags.ENTRY_TAG)));
                        int pageNumber = tagCompound.getInt(NBTBookTags.PAGE_TAG);
                        EntryScreen guiEntry = new EntryScreen(book, category, entry, player, bookStack);
                        guiEntry.pageNumber = pageNumber;
                        Minecraft.getInstance().displayGuiScreen( guiEntry);
                    } else if (tagCompound.contains(NBTBookTags.CATEGORY_TAG)) {
                        CategoryAbstract category = book.getCategoryList().get(tagCompound.getInt(NBTBookTags.CATEGORY_TAG));
                        int entryPage = tagCompound.getInt(NBTBookTags.ENTRY_PAGE_TAG);
                        CategoryScreen guiCategory = new CategoryScreen(book, category, player, bookStack, null);
                        guiCategory.entryPage = entryPage;
                        Minecraft.getInstance().displayGuiScreen( guiCategory);
                    } else {
                        int categoryNumber = tagCompound.getInt(NBTBookTags.CATEGORY_PAGE_TAG);
                        HomeScreen guiHome = new HomeScreen(book, player, bookStack);
                        guiHome.categoryPage = categoryNumber;
                        Minecraft.getInstance().displayGuiScreen( guiHome);
                    }
                }
            } catch (Exception e) {
                // No-op: If the linked content doesn't exist anymore
            }

            Minecraft.getInstance().displayGuiScreen(new HomeScreen(book, player, bookStack));
        }
    }
}
