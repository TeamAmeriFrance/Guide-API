package de.maxanier.guideapi.proxy;

import de.maxanier.guideapi.api.BookEvent;
import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.IGuideItem;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.NBTBookTags;
import de.maxanier.guideapi.gui.CategoryScreen;
import de.maxanier.guideapi.gui.EntryScreen;
import de.maxanier.guideapi.gui.HomeScreen;
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
    public void openEntry(Book book, CategoryAbstract categoryAbstract, EntryAbstract entryAbstract, PlayerEntity player, ItemStack stack) {
        BookEvent.Open event = new BookEvent.Open(book, stack, player);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            player.displayClientMessage(event.getCanceledText(), true);
            return;
        }

        Minecraft.getInstance().setScreen(new EntryScreen(book, categoryAbstract, entryAbstract, player, stack));
    }

    @Override
    public void openGuidebook(PlayerEntity player, World world, Book book, ItemStack bookStack) {
        if (!bookStack.isEmpty() && bookStack.getItem() instanceof IGuideItem) {
            book.initializeContent();
            try {
                if (bookStack.hasTag()) {
                    CompoundNBT tagCompound = bookStack.getTag();
                    if (tagCompound.contains(NBTBookTags.ENTRY_TAG) && tagCompound.contains(NBTBookTags.CATEGORY_TAG)) {
                        CategoryAbstract category = book.getCategoryList().get(tagCompound.getInt(NBTBookTags.CATEGORY_TAG));
                        EntryAbstract entry = category.entries.get(new ResourceLocation(tagCompound.getString(NBTBookTags.ENTRY_TAG)));
                        int pageNumber = tagCompound.getInt(NBTBookTags.PAGE_TAG);
                        EntryScreen guiEntry = new EntryScreen(book, category, entry, player, bookStack);
                        guiEntry.pageNumber = pageNumber;
                        Minecraft.getInstance().setScreen(guiEntry);
                        return;
                    } else if (tagCompound.contains(NBTBookTags.CATEGORY_TAG)) {
                        CategoryAbstract category = book.getCategoryList().get(tagCompound.getInt(NBTBookTags.CATEGORY_TAG));
                        int entryPage = tagCompound.getInt(NBTBookTags.ENTRY_PAGE_TAG);
                        CategoryScreen guiCategory = new CategoryScreen(book, category, player, bookStack, null);
                        guiCategory.entryPage = entryPage;
                        Minecraft.getInstance().setScreen(guiCategory);
                        return;
                    } else {
                        int categoryNumber = tagCompound.getInt(NBTBookTags.CATEGORY_PAGE_TAG);
                        HomeScreen guiHome = new HomeScreen(book, player, bookStack);
                        guiHome.categoryPage = categoryNumber;
                        Minecraft.getInstance().setScreen(guiHome);
                        return;
                    }
                }
            } catch (Exception e) {
                // No-op: If the linked content doesn't exist anymore
            }

            Minecraft.getInstance().setScreen(new HomeScreen(book, player, bookStack));
        }
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
    public void playSound(SoundEvent sound) {
        Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(sound, 1));
    }
}
