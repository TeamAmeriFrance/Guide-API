package amerifrance.guideapi;

import api.GuideAPI;
import api.IGuideBook;
import api.IPage;
import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.item.ItemGuideBook;
import amerifrance.guideapi.page.PageJsonRecipe;
import amerifrance.guideapi.util.APISetter;
import amerifrance.guideapi.util.AnnotationHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = "guideapi",bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistrarGuideAPI {


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        AnnotationHandler.gatherBooks();

        for (Book book : GuideAPI.getBooks().values()) {
            Item guideBook = new ItemGuideBook(book);
            guideBook.setRegistryName(book.getRegistryName().toString().replace(":", "-"));
            ForgeRegistries.ITEMS.register(guideBook);
            APISetter.setBookForStack(book, new ItemStack(guideBook));
        }
    }
//      TODO
//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
//        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES) {
//            IRecipe recipe = guide.getRight().getRecipe(GuideAPI.getStackFromBook(guide.getLeft()));
//            if (recipe != null)
//                event.getRegistry().register(recipe);
//        }
//
//        for (Book book : GuideAPI.getBooks().values())
//            for (CategoryAbstract cat : book.getCategoryList())
//                for (EntryAbstract entry : cat.entries.values())
//                    for (IPage page : entry.pageList)
//                        if (page instanceof PageJsonRecipe)
//                            ((PageJsonRecipe) page).init();
//    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES)
            guide.getRight().handleModel(GuideAPI.getStackFromBook(guide.getLeft()));
    }
}
