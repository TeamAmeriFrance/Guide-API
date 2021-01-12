package de.maxanier.guideapi;

import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.item.ItemGuideBook;
import de.maxanier.guideapi.util.APISetter;
import de.maxanier.guideapi.util.AnnotationHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = GuideMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistrarGuideAPI {


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        AnnotationHandler.gatherBooks();
        //Don't build book content here as items/blocks are not available and translation is only possible in game
        GuideConfig.buildConfiguration();//Build configuration now that we know all added books
        for (Book book : GuideAPI.getBooks().values()) {
            Item guideBook = new ItemGuideBook(book);
            guideBook.setRegistryName(book.getRegistryName().toString().replace(":", "-"));
            ForgeRegistries.ITEMS.register(guideBook);
            APISetter.setBookForStack(book, new ItemStack(guideBook));
        }
    }
}
