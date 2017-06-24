package amerifrance.guideapi;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.item.ItemGuideBook;
import amerifrance.guideapi.util.APISetter;
import amerifrance.guideapi.util.AnnotationHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber
public class RegistrarGuideAPI {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Book book : GuideAPI.getBooks().values()) {
            Item guideBook = new ItemGuideBook(book);
            guideBook.setRegistryName(book.getRegistryName().toString().replace(":", "-"));
            event.getRegistry().register(guideBook);
            APISetter.setBookForStack(book, new ItemStack(guideBook));
        }
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES)
            guide.getRight().handleModel(GuideAPI.getStackFromBook(guide.getLeft()));
    }
}
