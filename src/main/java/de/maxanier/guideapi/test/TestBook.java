package de.maxanier.guideapi.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.GuideBook;
import de.maxanier.guideapi.api.IGuideBook;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.BookBinder;
import de.maxanier.guideapi.api.impl.Entry;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.PageHelper;
import de.maxanier.guideapi.category.CategoryItemStack;
import de.maxanier.guideapi.entry.EntryItemStack;
import de.maxanier.guideapi.page.*;
import de.maxanier.guideapi.page.reciperenderer.ShapedRecipesRenderer;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Map;

@GuideBook
public class TestBook implements IGuideBook {

    public static Book book;

    @Nullable
    @Override
    public Book buildBook() {
        BookBinder binder = new BookBinder(new ResourceLocation(GuideMod.ID, "test_book"));
        binder.setAuthor("TehNut").setColor(Color.PINK).setItemName("Display Name").setHeader("Hello there").setGuideTitle("Title message").setSpawnWithBook().setContentProvider(this::buildContent);
        return (book = binder.build());
    }

    private void buildContent(List<CategoryAbstract> categories) {

        Map<ResourceLocation, EntryAbstract> entries = Maps.newHashMap();

        List<IPage> pages = Lists.newArrayList();
        pages.add(new PageText(new StringTextComponent("Hello, this is\nsome text with a new line.")));
        pages.add(new PageText(new StringTextComponent("Hello, this is some text without a new line. It is long so it should probably be automatically wrapped")));
        pages.addAll(PageHelper.pagesForLongText(new StringTextComponent("Hello, this is some text. It is very long so it should be split across multiple pages. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.")));
        pages.addAll(PageHelper.pagesForLongText(new StringTextComponent("Hello, this is some text. It is very long so it should be split across multiple pages. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua."), Items.COAL_BLOCK));

        pages.add(new PageJsonRecipe(new ResourceLocation("minecraft", "stone")));
        pages.add(new PageJsonRecipe(new ResourceLocation("minecraft", "charcoal")));

        pages.add(new PageJsonRecipe(new ResourceLocation("minecraft", "stick"), recipe -> recipe instanceof ShapedRecipe ? new ShapedRecipesRenderer((ShapedRecipe) recipe) : null)); //Probably want to use your own method as render supplier and print proper logs

        pages.add(new PageIRecipe(new ShapedRecipe(new ResourceLocation(GuideMod.ID, "test11"), "test", 1, 1, NonNullList.from(Ingredient.EMPTY, Ingredient.fromStacks(new ItemStack(Items.PUMPKIN))), new ItemStack(Blocks.OAK_LOG))));
        pages.add(new PageJsonRecipe(new ResourceLocation("minecraft", "acacia_fence")));
        pages.add(new PageItemStack(new StringTextComponent("These are all logs"), Ingredient.fromTag(ItemTags.LOGS)));
        pages.add(new PageTextImage(new TranslationTextComponent("guideapi.test.string"), new ResourceLocation(GuideMod.ID, "textures/gui/testimage.png"), true));
        pages.add(new PageTextImage(new TranslationTextComponent("guideapi.test.string"), new ResourceLocation(GuideMod.ID, "textures/gui/testimage.png"), false));
        pages.add(new PageImage(new ResourceLocation(GuideMod.ID, "textures/gui/testimage.png")));


        Entry entry = new EntryItemStack(pages, new TranslationTextComponent("guideapi.test.entry"), new ItemStack(Items.POTATO));
        entries.put(new ResourceLocation(GuideMod.ID, "entry"), entry);
        categories.add(new CategoryItemStack(entries, "guideapi.test.category", new ItemStack(Items.ACACIA_DOOR)));
        categories.add(new CategoryItemStack(entries, "guideapi.test.category", new ItemStack(Items.PUMPKIN)));
        categories.add(new CategoryItemStack(entries, "guideapi.test.category", new ItemStack(Items.WOODEN_AXE)));
        categories.add(new CategoryItemStack(entries, "guideapi.test.category", new ItemStack(Items.SPRUCE_WOOD)));
        categories.add(new CategoryItemStack(entries, "guideapi.test.category", new ItemStack(Items.BONE_MEAL)));
        categories.add(new CategoryItemStack(entries, "guideapi.test.category", new ItemStack(Items.WHEAT)));

    }
}
