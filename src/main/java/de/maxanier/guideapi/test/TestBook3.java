package de.maxanier.guideapi.test;

import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.GuideBook;
import de.maxanier.guideapi.api.IGuideBook;
import de.maxanier.guideapi.api.IInfoRenderer;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.BookBinder;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.BookHelper;
import de.maxanier.guideapi.api.util.ItemInfoBuilder;
import de.maxanier.guideapi.category.CategoryItemStack;
import de.maxanier.guideapi.info.InfoRendererDescription;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Use {@link BookHelper} , {@link ItemInfoBuilder} and {@link IInfoRenderer}
 */
@GuideBook
public class TestBook3 implements IGuideBook {

    public static Book book;

    @Nullable
    @Override
    public Book buildBook() {
        BookBinder binder = new BookBinder(new ResourceLocation(GuideMod.ID, "test_book3"));
        binder.setAuthor("Maxanier").setColor(new Color(80, 50, 5)).setItemName("Display Name").setHeader("Hello there").setSpawnWithBook().setGuideTitle("Title message").setContentProvider(this::buildContent);
        book = binder.build();
        return book;
    }

    @Override
    public void registerInfoRenderer(Book yourBook) {
        GuideAPI.registerInfoRenderer(yourBook, new InfoRendererDescription(new ItemStack(Blocks.COAL_BLOCK), new TranslationTextComponent("guideapi.test.blocks.compressed_blocks.hint")), Blocks.COAL_BLOCK, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK);
    }

    private void buildContent(List<CategoryAbstract> categories) {
        BookHelper helper = new BookHelper.Builder(GuideMod.ID).setBaseKey("guideapi.test").build();

        CategoryAbstract blocks = new CategoryItemStack("Blocks", new ItemStack(Blocks.STONE)).withKeyBase(GuideMod.ID);
        Map<ResourceLocation, EntryAbstract> blockEntries = new LinkedHashMap<>();
        helper.info(Blocks.COAL_BLOCK, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK).recipes(new ResourceLocation("minecraft", "coal_block"), new ResourceLocation("iron_block"), new ResourceLocation("gold_block")).useCustomEntryName().setKeyName("compressed_blocks").setLinks(new ResourceLocation("guideapi.test.items.ingots")).setFormats(9).build(blockEntries);
        blocks.addEntries(blockEntries);
        categories.add(blocks);

        CategoryAbstract items = new CategoryItemStack("Items", new ItemStack(Items.IRON_AXE)).withKeyBase(GuideMod.ID);
        Map<ResourceLocation, EntryAbstract> itemEntries = new LinkedHashMap<>();
        helper.info(Items.APPLE).build(itemEntries);
        helper.info(false, Ingredient.fromTag(Tags.Items.INGOTS), new ItemStack(Items.IRON_INGOT)).useCustomEntryName().recipes(new ResourceLocation("minecraft", "iron_ingot"), new ResourceLocation("gold_ingot")).setKeyName("ingots").setLinks(new ResourceLocation("guideapi.test.blocks.compressed_blocks")).build(itemEntries);
        items.addEntries(itemEntries);
        categories.add(items);

        helper.registerLinkablePages(categories);
    }
}
