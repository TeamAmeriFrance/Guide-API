package test;

import amerifrance.guideapi.GuideMod;
import api.BookEvent;
import api.GuideBook;
import api.IGuideBook;
import api.impl.Book;
import api.impl.BookBinder;
import api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potions;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
                .setCreativeTab(ItemGroup.COMBAT)
                .setGuideTitle("some.guide.title")
                .setHeader("some.header.text")
                .setSpawnWithBook();

        CategoryAbstract testCategory = new CategoryItemStack("test.category.name", new ItemStack(Items.BLACK_BANNER)).withKeyBase("guideapi");
        testCategory.addEntry("entry", new EntryItemStack("test.entry.name", new ItemStack(Items.POTATO)));
        testCategory.getEntry("entry").addPage(new PageText("Hello, this is\nsome text"));
        //testCategory.getEntry("entry").addPage(new PageFurnaceRecipe(Blocks.COBBLESTONE));
        //testCategory.getEntry("entry").addPage(PageIRecipe.newShaped(new ItemStack(Items.ACACIA_BOAT), "X X", "XXX", 'X', new ItemStack(Blocks.ACACIA_PLANKS, 1, 4)));
        testCategory.getEntry("entry").addPage(new PageBrewingRecipe(new BrewingRecipe(Ingredient.fromStacks( PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.fromStacks( new ItemStack(Items.GLISTERING_MELON_SLICE)),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.HEALING)))
        );
        testCategory.getEntry("entry").addPage(new PageJsonRecipe(new ResourceLocation("bread")));
        binder.addCategory(testCategory);

        return book = binder.build();
    }

    @SubscribeEvent
    public void onBookOpen(BookEvent.Open event) {
        if (event.getBook() == book && event.getPlayer().isSneaking()) {
            event.setCanceledText(new StringTextComponent("No snek allowed"));
            event.setCanceled(true);
        }
    }
}
