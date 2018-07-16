package amerifrance.guideapi.test;

import amerifrance.guideapi.GuideMod;
import amerifrance.guideapi.api.BookEvent;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

@GuideBook
public class TestBook3 implements IGuideBook {

    public static Book book;

    @Nullable
    @Override
    public Book buildBook() {
        MinecraftForge.EVENT_BUS.register(this);

        BookBinder binder = new BookBinder(new ResourceLocation(GuideMod.ID, "test_book3"))
                .setAuthor("TunHet")
                .setColor(0x7EF67F)
                .setCreativeTab(CreativeTabs.COMBAT)
                .setGuideTitle("some.guide.title")
                .setHeader("some.header.text")
                .setSpawnWithBook();

        CategoryAbstract testCategory = new CategoryItemStack("test.category.name", new ItemStack(Items.BANNER)).withKeyBase("guideapi");
        testCategory.addEntry("entry", new EntryItemStack("test.entry.name", new ItemStack(Items.POTATO)));
        testCategory.getEntry("entry").addPage(new PageText("Hello, this is\nsome text"));
        testCategory.getEntry("entry").addPage(new PageFurnaceRecipe(Blocks.COBBLESTONE));
        testCategory.getEntry("entry").addPage(PageIRecipe.newShaped(new ItemStack(Items.ACACIA_BOAT), "X X", "XXX", 'X', new ItemStack(Blocks.PLANKS, 1, 4)));
        testCategory.getEntry("entry").addPage(new PageBrewingRecipe(new BrewingRecipe(
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD),
                new ItemStack(Items.SPECKLED_MELON),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.HEALING)))
        );
        testCategory.getEntry("entry").addPage(new PageJsonRecipe(new ResourceLocation("bread")));
        binder.addCategory(testCategory);

        return book = binder.build();
    }

    @SubscribeEvent
    public void onBookOpen(BookEvent.Open event) {
        if (event.getBook() == book && event.getPlayer().isSneaking()) {
            event.setCanceledText(new TextComponentString("No snek allowed"));
            event.setCanceled(true);
        }
    }
}
