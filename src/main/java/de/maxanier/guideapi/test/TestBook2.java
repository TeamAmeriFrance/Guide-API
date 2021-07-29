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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
        binder.setAuthor(new TextComponent("TehNut")).setColor(new Color(80, 50, 5)).setItemName(new TextComponent("Display Name")).setHeader(new TextComponent("Hello there")).setSpawnWithBook().setGuideTitle(new TextComponent("Title message")).setContentProvider(this::buildContent);


        return (book = binder.build());
    }

    private void buildContent(List<CategoryAbstract> categories) {
        CategoryAbstract testCategory = new CategoryItemStack(new TranslatableComponent("guideapi.test.category"), new ItemStack(Items.BLUE_BANNER)).withKeyBase("guideapi");
        testCategory.addEntry("entry", new EntryItemStack(new TranslatableComponent("guideapi.test.entry"), new ItemStack(Items.POTATO)));
        testCategory.getEntry("entry").addPage(new PageText(new TextComponent("Hello, this is\nsome text")));
        //testCategory.getEntry("entry").addPage(new PageFurnaceRecipe(Blocks.COBBLESTONE));
        //testCategory.getEntry("entry").addPage(PageIRecipe.newShaped(new ItemStack(Items.ACACIA_BOAT), "X X", "XXX", 'X', new ItemStack(Blocks.ACACIA_PLANKS, 1, 4)));
        testCategory.getEntry("entry").addPage(new PageBrewingRecipe(new BrewingRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(new ItemStack(Items.GLISTERING_MELON_SLICE)), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING)))
        );
        testCategory.getEntry("entry").addPage(new PageJsonRecipe(new ResourceLocation("bread")));
        testCategory.getEntry("entry").addPage(new PageJsonRecipe(new ResourceLocation("redstone")));
        testCategory.getEntry("entry").addPageList(PageHelper.pagesForLongText(new TranslatableComponent("guideapi.test.format")));
        testCategory.addEntry("unicode", new EntryItemStack(new TextComponent("Творческая книга"), new ItemStack(Items.BEEF)));
        testCategory.getEntry("unicode").addPage(new PageText(new TextComponent("Творческая книга \u0F06")));
        categories.add(testCategory);
    }
}
