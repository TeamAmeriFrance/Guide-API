package test;

import api.GuideBook;
import api.IGuideBook;
import api.impl.Book;
import api.impl.BookBinder;
import api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.potion.Potions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.brewing.BrewingRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.Color;
import java.util.List;

@GuideBook
public class TestBook2 implements IGuideBook {

    public static Book book;

    @Nullable
    @Override
    public Book buildBook() {
        BookBinder binder = new BookBinder(new ResourceLocation("guideapi","test_book2"));
        binder.setAuthor("TehNut").setColor(Color.GRAY).setItemName("Display Name").setHeader("Hello there").setGuideTitle("Title message").setContentProvider(this::buildContent);



        return (book = binder.build());
    }

    @Nullable
    @Override
    public IRecipe getRecipe(@Nonnull ItemStack bookStack) {
        return new ShapedRecipe(book.getRegistryName(), "", 3,1, NonNullList.from(Ingredient.EMPTY,Ingredient.EMPTY,Ingredient.fromTag(Tags.Items.INGOTS_GOLD),Ingredient.EMPTY),bookStack);
    }

    private void buildContent(List<CategoryAbstract> categories) {
        CategoryAbstract testCategory = new CategoryItemStack("test.category.name", new ItemStack(Items.BLUE_BANNER)).withKeyBase("guideapi");
        testCategory.addEntry("entry", new EntryItemStack("test.entry.name", new ItemStack(Items.POTATO)));
        testCategory.getEntry("entry").addPage(new PageText("Hello, this is\nsome text"));
        //testCategory.getEntry("entry").addPage(new PageFurnaceRecipe(Blocks.COBBLESTONE));
        //testCategory.getEntry("entry").addPage(PageIRecipe.newShaped(new ItemStack(Items.ACACIA_BOAT), "X X", "XXX", 'X', new ItemStack(Blocks.ACACIA_PLANKS, 1, 4)));
        testCategory.getEntry("entry").addPage(new PageBrewingRecipe(new BrewingRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.fromStacks( new ItemStack(Items.GLISTERING_MELON_SLICE)), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.HEALING)))
        );
        testCategory.getEntry("entry").addPage(new PageJsonRecipe(new ResourceLocation("bread")));
        categories.add(testCategory);
    }
}
