package amerifrance.guideapi.test;

import amerifrance.guideapi.api.*;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageFurnaceRecipe;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageText;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
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
        book.setColor(Color.BLUE);
        book.setDisplayName("Display Name");
        book.setTitle("Title message");
        book.setWelcomeMessage("Is this still a thing?");

        List<CategoryAbstract> categories = Lists.newArrayList();
        Map<ResourceLocation, EntryAbstract> entries = Maps.newHashMap();

        List<IPage> pages = Lists.newArrayList();
        pages.add(new PageText("Hello, this is\nsome text"));
        pages.add(new PageFurnaceRecipe(Blocks.COBBLESTONE));
        pages.add(PageIRecipe.newShaped(new ItemStack(Items.ACACIA_BOAT), "X X", "XXX", 'X', "plankWood"));
        Entry entry = new EntryItemStack(pages, "test.entry.name", new ItemStack(Items.POTATO));
        entries.put(new ResourceLocation("guideapi", "entry"), entry);
        
        pages.add(PageIRecipe.newShapeless(new ItemStack(Blocks.IRON_BLOCK), 
                        "ingotIron", "ingotIron", "ingotIron",
                        "ingotIron", "ingotIron", "ingotIron",
                       "ingotIron", "ingotIron", "ingotIron"));

        pages.add(PageIRecipe.newShapeless(new ItemStack(Blocks.PLANKS, 4), 
                        new ItemStack(Blocks.LOG)));

        categories.add(new CategoryItemStack(entries, "test.category.name", new ItemStack(Items.BANNER)));
        book.setCategoryList(categories);
        book.setRegistryName(new ResourceLocation("guideapi", "test_book"));
        return book;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleModel(ItemStack bookStack) {
        GuideAPI.setModel(book);
    }

    @Override
    public void handlePost(ItemStack bookStack) {
        ForgeRegistries.RECIPES.register(new ShapedOreRecipe(null, bookStack, " X ", "X X", " X ", 'X', "ingotIron").setRegistryName(book.getRegistryName()));
    }
}
