package amerifrance.guideapi.test;

import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageJsonRecipe;
import amerifrance.guideapi.page.PageText;
import amerifrance.guideapi.page.reciperenderer.ShapedRecipesRenderer;
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
        pages.add(new PageJsonRecipe(new ResourceLocation("minecraft","stone")));
        pages.add(new PageJsonRecipe(new ResourceLocation("minecraft","stick"),recipe -> recipe instanceof ShapedRecipe ? new ShapedRecipesRenderer((ShapedRecipe) recipe) : null)); //Probably want to use your own method as render supplier and print proper logs

        pages.add(new PageIRecipe(new ShapedRecipe(new ResourceLocation("guideapi","test11"), "test",1,1,NonNullList.from(Ingredient.EMPTY,Ingredient.fromStacks(new ItemStack(Items.PUMPKIN))),new ItemStack(Blocks.OAK_LOG))));
        pages.add(new PageJsonRecipe(new ResourceLocation("minecraft", "acacia_fence")));
        Entry entry = new EntryItemStack(pages, "test.entry.name", new ItemStack(Items.POTATO));
        entries.put(new ResourceLocation("guideapi", "entry"), entry);
        categories.add(new CategoryItemStack(entries, "test.category.name", new ItemStack(Items.BLUE_BANNER)));
    }
}
