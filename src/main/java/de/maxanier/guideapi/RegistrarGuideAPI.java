package de.maxanier.guideapi;

import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.IGuideBook;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.item.ItemGuideBook;
import de.maxanier.guideapi.util.APISetter;
import de.maxanier.guideapi.util.AnnotationHandler;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

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


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES) {
            ResourceLocation loc = guide.getRight().getModel();
            if (loc != null) {
                ModelLoader.addSpecialModel(new ModelResourceLocation(loc, "inventory"));
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void bakeModel(ModelBakeEvent event) {
        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES) {
            ResourceLocation loc = guide.getRight().getModel();
            if (loc != null) {
                ModelResourceLocation newMrl = new ModelResourceLocation(loc, "inventory");
                Item bookItem = GuideAPI.getStackFromBook(guide.getLeft()).getItem();
                ModelResourceLocation oldMrl = new ModelResourceLocation(bookItem.getRegistryName(), "inventory");
                IBakedModel model = event.getModelRegistry().get(newMrl);

                event.getModelRegistry().put(oldMrl, model);

            }
        }

    }
}
