package amerifrance.guideapi;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.category.CategoryResourceLocation;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.entry.EntryResourceLocation;
import amerifrance.guideapi.page.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.awt.*;
import java.util.ArrayList;

public class TestBook {

    public static void registerTests(int amountOfBooks) {
        for (int i = 0; i < amountOfBooks; i++)
            testBook(new Book(), "TestBook" + i);
    }

    public static void testBook(Book book, String title) {
        PageText page1 = new PageText("HERE IS SOME TEXT FOR YOU TO DRAW LEWL. I AM VERY LONG FOR NOTHING MATE");
        PageText page2 = new PageText("HERE IS SOME TEXT FOR YOU TO DRAW LEWL. I AM VERY LONG FOR NOTHING MATE.\n\nNew paragraph!");
        PageImage page3 = new PageImage(new ResourceLocation(ModInformation.GUITEXLOC + "testimage.png"));
        PageIRecipe page4 = new PageIRecipe(GameRegistry.addShapedRecipe(new ItemStack(Items.DIAMOND), "XXX", "YYY", "ZZZ", 'X', Items.APPLE, 'Y', Blocks.BEACON, 'Z', Items.BEEF));
        ShapedOreRecipe shapedOreRecipe = new ShapedOreRecipe(Items.BEEF, "XXX", "YYY", "ZZZ", 'X', "stairWood", 'Y', "stone", 'Z', "ingotIron");
        PageIRecipe page5 = new PageIRecipe(shapedOreRecipe);
        ArrayList<ItemStack> shapelessList = new ArrayList<ItemStack>();
        shapelessList.add(new ItemStack(Items.CAULDRON));
        shapelessList.add(new ItemStack(Items.GOLDEN_CARROT));
        ShapelessRecipes shapelessRecipes = new ShapelessRecipes(new ItemStack(Items.BLAZE_ROD), shapelessList);
        PageIRecipe page6 = new PageIRecipe(shapelessRecipes);
        ShapelessOreRecipe shapelessOreRecipe = new ShapelessOreRecipe(new ItemStack(Items.BAKED_POTATO), "ingotIron", "stairWood");
        PageIRecipe page7 = new PageIRecipe(shapelessOreRecipe);
        PageSound page8 = new PageSound(page6, "mob.pig.say");
        PageFurnaceRecipe page9 = new PageFurnaceRecipe(new ItemStack(Items.POTATO));
        PageFurnaceRecipe page10 = new PageFurnaceRecipe(new ItemStack(Items.DIAMOND_AXE));

        ArrayList<IPage> pages = new ArrayList<IPage>();
        pages.add(page1);
        pages.add(page2);
        pages.addAll(PageHelper.pagesForLongText("HERE IS SOME TEXT FOR YOU TO DRAW LEWL. I AM VERY LONG FOR NOTHING MATE", new ItemStack(Items.DIAMOND)));
        pages.add(page3);
        pages.add(page4);
        pages.add(page5);
        pages.add(page6);
        pages.add(page7);
        pages.add(page8);
        pages.add(page9);
        pages.add(page10);

        EntryItemStack entry1 = new EntryItemStack(pages, "TestEntry1", new ItemStack(Items.POTATO));
        EntryItemStack entry2 = new EntryItemStack(pages, "TestEntry2", new ItemStack(Blocks.DIRT));
        Entry entry3 = new Entry(pages, "TestEntry3");
        EntryResourceLocation entry4 = new EntryResourceLocation(pages, "TestEntry4", new ResourceLocation(ModInformation.GUITEXLOC + "testimage.png"));
        ArrayList<EntryAbstract> entries = new ArrayList<EntryAbstract>();
        entries.add(entry1);
        entries.add(entry2);
        entries.add(entry3);
        entries.add(entry4);

        CategoryResourceLocation category1 = new CategoryResourceLocation(entries, "TestCategory1", new ResourceLocation(ModInformation.GUITEXLOC + "testimage.png"));
        CategoryItemStack category2 = new CategoryItemStack(entries, "TestCategory2", new ItemStack(Blocks.BRICK_STAIRS));
        CategoryItemStack category3 = new CategoryItemStack(entries, "TestCategory3", new ItemStack(Blocks.DRAGON_EGG));
        CategoryItemStack category4 = new CategoryItemStack(entries, "TestCategory4", new ItemStack(Items.DIAMOND));
        CategoryItemStack category5 = new CategoryItemStack(entries, "TestCategory5", new ItemStack(Blocks.OAK_FENCE));
        ArrayList<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);

        book.setCategoryList(categories);
        book.setTitle(title);
        book.setWelcomeMessage(title);
        book.setDisplayName(title);
        book.setColor(new Color(171, 80, 30));
        book.setRegistryName(title);

        GuideAPI.BOOKS.register(book);

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            GuideAPI.setModel(book);
    }
}
