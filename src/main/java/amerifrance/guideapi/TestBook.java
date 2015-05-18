package amerifrance.guideapi;

import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.categories.CategoryItemStack;
import amerifrance.guideapi.entries.EntryText;
import amerifrance.guideapi.pages.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.awt.*;
import java.util.ArrayList;

public class TestBook {

    public static void registerTests(int amountOfBooks) {

        Book[] books = new Book[amountOfBooks];
        for (int j = 0; j < books.length; j++)
            testBook(books[j], "TestBook" + j);
    }

    public static void testBook(Book book, String title){

        PageUnlocText page1 = new PageUnlocText("HERE IS SOME TEXT FOR YOU TO DRAW LEWL. I AM VERY LONG FOR NOTHING MATE");
        PageLocText page2 = new PageLocText("HERE IS SOME TEXT FOR YOU TO DRAW LEWL. I AM VERY LONG FOR NOTHING MATE");
        PageImage page3 = new PageImage(new ResourceLocation(ModInformation.GUITEXLOC + "testimage.png"));
        PageIRecipe page4 = new PageIRecipe(GameRegistry.addShapedRecipe(new ItemStack(Items.diamond), "XXX", "YYY", "ZZZ", 'X', Items.apple, 'Y', Blocks.beacon, 'Z', Items.beef));
        ShapedOreRecipe shapedOreRecipe = new ShapedOreRecipe(Items.beef, "XXX", "YYY", "ZZZ", 'X', "stairWood", 'Y', "stone", 'Z', "ingotIron");
        PageIRecipe page5 = new PageIRecipe(shapedOreRecipe);
        ArrayList<ItemStack> shapelessList = new ArrayList<ItemStack>();
        shapelessList.add(new ItemStack(Items.cauldron));
        shapelessList.add(new ItemStack(Items.golden_carrot));
        ShapelessRecipes shapelessRecipes = new ShapelessRecipes(new ItemStack(Items.blaze_rod), shapelessList);
        PageIRecipe page6 = new PageIRecipe(shapelessRecipes);
        ShapelessOreRecipe shapelessOreRecipe = new ShapelessOreRecipe(new ItemStack(Items.baked_potato), "ingotIron", "stairWood");
        PageIRecipe page7 = new PageIRecipe(shapelessOreRecipe);
        PageSound page8 = new PageSound(page6, "mob.pig.say");
        PageFurnaceRecipe page9 = new PageFurnaceRecipe(new ItemStack(Items.potato));
        PageFurnaceRecipe page10 = new PageFurnaceRecipe(new ItemStack(Items.diamond_axe));

        ArrayList<IPage> pages = new ArrayList<IPage>();
        pages.add(page1);
        pages.add(page2);
        pages.add(page3);
        pages.add(page4);
        pages.add(page5);
        pages.add(page6);
        pages.add(page7);
        pages.add(page8);
        pages.add(page9);
        pages.add(page10);

        EntryText entry1 = new EntryText(pages, "TestEntry1");
        EntryText entry2 = new EntryText(pages, "TestEntry2");
        ArrayList<EntryAbstract> entries = new ArrayList<EntryAbstract>();
        entries.add(entry1);
        entries.add(entry2);

        CategoryItemStack category1 = new CategoryItemStack(entries, "TestCategory1", new ItemStack(Items.reeds));
        CategoryItemStack category2 = new CategoryItemStack(entries, "TestCategory2", new ItemStack(Blocks.brick_stairs));
        CategoryItemStack category3 = new CategoryItemStack(entries, "TestCategory3", new ItemStack(Blocks.dragon_egg));
        CategoryItemStack category4 = new CategoryItemStack(entries, "TestCategory4", new ItemStack(Items.skull, 1, 0));
        CategoryItemStack category5 = new CategoryItemStack(entries, "TestCategory5", new ItemStack(Blocks.oak_fence_gate));
        ArrayList<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);

        book = new Book(
                categories, "Item" + title,
                "Hello, I am a welcome message that's just way too long and that says nothing other that I'm there for test purposes",
                title,
                new Color(58, 171, 122)
        );

        GuideRegistry.registerBook(book);
    }
}
