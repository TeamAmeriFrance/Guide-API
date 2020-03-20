package amerifrance.guideapi.test;

import api.GuideBook;
import api.IGuideBook;
import api.IPage;
import api.impl.Book;
import api.impl.Entry;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageFurnaceRecipe;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageJsonRecipe;
import amerifrance.guideapi.page.PageText;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

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
        book = new Book();
        book.setAuthor("TehNut");
        book.setColor(Color.GRAY);
        book.setDisplayName("Display Name");
        book.setTitle("Title message");
        book.setWelcomeMessage("Is this still a thing?");

        List<CategoryAbstract> categories = Lists.newArrayList();
        Map<ResourceLocation, EntryAbstract> entries = Maps.newHashMap();

        List<IPage> pages = Lists.newArrayList();
        pages.add(new PageText("Hello, this is\nsome text"));
        pages.add(new PageFurnaceRecipe(Blocks.COBBLESTONE));
        pages.add(PageIRecipe.newShaped(new ItemStack(Items.ACACIA_BOAT), "X X", "XXX", 'X', "plankWood"));
        pages.add(new PageJsonRecipe(new ResourceLocation("minecraft", "acacia_fence")));
        Entry entry = new EntryItemStack(pages, "test.entry.name", new ItemStack(Items.POTATO));
        entries.put(new ResourceLocation("guideapi", "entry"), entry);

        pages.add(PageIRecipe.newShapeless(new ItemStack(Blocks.IRON_BLOCK), "ingotIron", "ingotIron", "ingotIron", "ingotIron", "ingotIron", "ingotIron", "ingotIron", "ingotIron", "ingotIron"));
        pages.add(PageIRecipe.newShapeless(new ItemStack(Blocks.PLANKS, 4), new ItemStack(Blocks.LOG)));

        categories.add(new CategoryItemStack(entries, "test.category.name", new ItemStack(Items.BANNER)));
        book.setCategoryList(categories);
        book.setRegistryName(new ResourceLocation("guideapi", "test_book"));
        return book;
    }

    @Override
    public IRecipe getRecipe(@Nonnull ItemStack bookStack) {
        return new ShapedOreRecipe(null, bookStack, " X ", "X X", " X ", 'X', "ingotIron").setRegistryName(book.getRegistryName());
    }
}
