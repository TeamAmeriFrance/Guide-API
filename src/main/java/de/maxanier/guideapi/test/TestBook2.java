package de.maxanier.guideapi.test;

import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.GuideBook;
import de.maxanier.guideapi.api.IGuideBook;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.BookBinder;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.util.PageHelper;
import de.maxanier.guideapi.category.CategoryItemStack;
import de.maxanier.guideapi.entry.EntryItemStack;
import de.maxanier.guideapi.page.PageBrewingRecipe;
import de.maxanier.guideapi.page.PageJsonRecipe;
import de.maxanier.guideapi.page.PageText;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.brewing.BrewingRecipe;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

@GuideBook
public class TestBook2 implements IGuideBook {

    public static Book book;

    @Nullable
    @Override
    public Book buildBook() {
        BookBinder binder = new BookBinder(new ResourceLocation(GuideMod.ID, "test_book2"));
        binder.setAuthor("TehNut").setColor(new Color(80, 50, 5)).setItemName("Display Name").setHeader("Hello there").setSpawnWithBook().setGuideTitle("Title message").setContentProvider(this::buildContent);


        return (book = binder.build());
    }

    private void buildContent(List<CategoryAbstract> categories) {
        CategoryAbstract testCategory = new CategoryItemStack("guideapi.test.category", new ItemStack(Items.BLUE_BANNER)).withKeyBase("guideapi");
        testCategory.addEntry("entry", new EntryItemStack(new TranslationTextComponent("guideapi.test.entry"), new ItemStack(Items.POTATO)));
        testCategory.getEntry("entry").addPage(new PageText(new StringTextComponent("Hello, this is\nsome text")));
        //testCategory.getEntry("entry").addPage(new PageFurnaceRecipe(Blocks.COBBLESTONE));
        //testCategory.getEntry("entry").addPage(PageIRecipe.newShaped(new ItemStack(Items.ACACIA_BOAT), "X X", "XXX", 'X', new ItemStack(Blocks.ACACIA_PLANKS, 1, 4)));
        testCategory.getEntry("entry").addPage(new PageBrewingRecipe(new BrewingRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromStacks(new ItemStack(Items.GLISTERING_MELON_SLICE)), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.HEALING)))
        );
        testCategory.getEntry("entry").addPage(new PageJsonRecipe(new ResourceLocation("bread")));
        testCategory.getEntry("entry").addPage(new PageJsonRecipe(new ResourceLocation("redstone")));
        testCategory.getEntry("entry").addPageList(PageHelper.pagesForLongText(new TranslationTextComponent("guideapi.test.format")));
        testCategory.addEntry("unicode", new EntryItemStack(new StringTextComponent("Творческая книга"), new ItemStack(Items.BEEF)));
        testCategory.getEntry("unicode").addPage(new PageText(new StringTextComponent("Творческая книга \u0F06")));
        categories.add(testCategory);
    }
}
