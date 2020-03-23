package test;

import api.GuideBook;
import api.IGuideBook;
import api.IPage;
import api.impl.Book;
import api.impl.BookBinder;
import api.impl.Entry;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageJsonRecipe;
import amerifrance.guideapi.page.PageText;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@GuideBook
public class TestBook implements IGuideBook {

    public static Book book;

    @Nullable
    @Override
    public Book buildBook() {
        BookBinder binder = new BookBinder(new ResourceLocation("guideapi","test_book"));
        binder.setAuthor("TehNut").setColor(Color.GRAY).setItemName("Display Name").setHeader("Hello there").setGuideTitle("Title message").setContentProvider(this::buildContent);
        return (book = binder.build());
    }

    @Override
    public IRecipe<?> getRecipe(@Nonnull ItemStack bookStack) {
        return new ShapedRecipe(book.getRegistryName(), "", 3,1,NonNullList.from(Ingredient.EMPTY,Ingredient.EMPTY,Ingredient.fromTag(Tags.Items.INGOTS_IRON),Ingredient.EMPTY),bookStack);
    }

    private void buildContent(List<CategoryAbstract> categories){

        Map<ResourceLocation, EntryAbstract> entries = Maps.newHashMap();

        List<IPage> pages = Lists.newArrayList();
        pages.add(new PageText("Hello, this is\nsome text"));
        //pages.add(new PageFurnaceRecipe(Blocks.COBBLESTONE));
        pages.add(new PageIRecipe(new ShapedRecipe(new ResourceLocation("guideapi","test11"), "test",1,1,NonNullList.from(Ingredient.EMPTY,Ingredient.fromStacks(new ItemStack(Items.PUMPKIN))),new ItemStack(Blocks.OAK_LOG))));
        //pages.add(PageIRecipe.newShaped(new ItemStack(Items.ACACIA_BOAT), "X X", "XXX", 'X', "plankWood"));
        pages.add(new PageJsonRecipe(new ResourceLocation("minecraft", "acacia_fence")));
        Entry entry = new EntryItemStack(pages, "test.entry.name", new ItemStack(Items.POTATO));
        entries.put(new ResourceLocation("guideapi", "entry"), entry);

        //pages.add(PageIRecipe.newShapeless(new ItemStack(Blocks.IRON_BLOCK), "ingotIron", "ingotIron", "ingotIron", "ingotIron", "ingotIron", "ingotIron", "ingotIron", "ingotIron", "ingotIron"));
        //pages.add(PageIRecipe.newShapeless(new ItemStack(Blocks.OAK_PLANKS, 4), new ItemStack(Blocks.OAK_LOG)));
        categories.add(new CategoryItemStack(entries, "test.category.name", new ItemStack(Items.BLUE_BANNER)));
    }
}
