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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber
public class RegistrarGuideAPI {

    /*
     * Why Register<Biome>? Well, that's an easy one. We need to register our items during the registry event phase so they
     * can be properly used during init and beyond. However, we cannot handle it during Register<Item> because any mods
     * using @ObjectHolder will not have their Item fields populated. Thus, we must use an event that fires *after*
     * Item object holders are populated, but *before* the recipe registry. Since the events are fired in alphabetical order,
     * we just pick one that starts with a letter before "r".
     */
    @SubscribeEvent
    public static void registerItemsInADifferentRegistryEventBecauseLoadOrderingAndObjectHoldersAreImportant(RegistryEvent.Register<Biome> event) {
        AnnotationHandler.gatherBooks(GuideMod.dataTable);

        for (Book book : GuideAPI.getBooks().values()) {
            Item guideBook = new ItemGuideBook(book);
            guideBook.setRegistryName(book.getRegistryName().toString().replace(":", "-"));
            ForgeRegistries.ITEMS.register(guideBook);
            APISetter.setBookForStack(book, new ItemStack(guideBook));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES) {
            IRecipe recipe = guide.getRight().getRecipe(GuideAPI.getStackFromBook(guide.getLeft()));
            if (recipe != null)
                event.getRegistry().register(recipe);
        }

        for (Book book : GuideAPI.getBooks().values())
            for (CategoryAbstract cat : book.getCategoryList())
                for (EntryAbstract entry : cat.entries.values())
                    for (IPage page : entry.pageList)
                        if (page instanceof PageJsonRecipe)
                            ((PageJsonRecipe) page).init();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES)
            guide.getRight().handleModel(GuideAPI.getStackFromBook(guide.getLeft()));
    }
}
