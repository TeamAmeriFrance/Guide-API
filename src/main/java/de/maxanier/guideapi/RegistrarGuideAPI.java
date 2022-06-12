package de.maxanier.guideapi;

import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.GuideBook;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.item.ItemGuideBook;
import de.maxanier.guideapi.util.APISetter;
import de.maxanier.guideapi.util.AnnotationHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = GuideMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistrarGuideAPI {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GuideMod.ID);


    @SubscribeEvent
    public static void registerItems(RegisterEvent event) {
        if(!event.getRegistryKey().equals(ForgeRegistries.ITEMS.getRegistryKey()))return;

        AnnotationHandler.gatherBooks();
        //Don't build book content here as items/blocks are not available and translation is only possible in game
        GuideConfig.buildConfiguration();//Build configuration now that we know all added books
        for (Book book : GuideAPI.getBooks().values()) {
            ResourceLocation id = new ResourceLocation(GuideMod.ID, book.getRegistryName().toString().replace(":", "-"));
            event.register(ForgeRegistries.ITEMS.getRegistryKey(),id, () -> new ItemGuideBook(book));
            APISetter.setBookForStack(book, () -> new ItemStack(ForgeRegistries.ITEMS.getValue(id)));
        }
    }
}
