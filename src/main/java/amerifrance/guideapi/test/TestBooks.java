package amerifrance.guideapi.test;

import amerifrance.guideapi.GuideRegistry;
import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.categories.CategoryItemStack;
import amerifrance.guideapi.entries.EntryText;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.CategoryBase;
import amerifrance.guideapi.objects.EntryBase;
import amerifrance.guideapi.objects.PageBase;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import amerifrance.guideapi.objects.abstraction.PageAbstract;
import amerifrance.guideapi.pages.*;
import amerifrance.guideapi.util.BookCreator;
import amerifrance.guideapi.util.PageHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.awt.*;
import java.util.ArrayList;

public class TestBooks {

    public static Book testBook1;
    public static Book testBook2;

    public static void setTestBooks() {
        setTestBook1();
        setTestBook2();
        GuideRegistry.registerBook(BookCreator.createBookFromJson("testBook.json"));
    }

    public static void setTestBook1() {
        PageBase page1 = new PageUnlocText("HERE IS SOME TEXT FOR YOU TO DRAW LEWL. I AM VERY LONG FOR NOTHING MATE");
        PageLocText page2 = new PageLocText("HERE IS SOME TEXT FOR YOU TO DRAW LEWL. I AM VERY LONG FOR NOTHING MATE");
        PageImage page3 = new PageImage(new ResourceLocation(ModInformation.GUITEXLOC + "testimage.png"));
        PageIRecipe page4 = new PageIRecipe(GameRegistry.addShapedRecipe(new ItemStack(Items.diamond), "XXX", "YYY", "ZZZ", 'X', Items.apple, 'Y', Blocks.beacon, 'Z', Items.beef));
        ShapedOreRecipe shapedOreRecipe = new ShapedOreRecipe(Items.beef, new Object[]{"XXX", "YYY", "ZZZ", 'X', "stairWood", 'Y', "stone", 'Z', "ingotIron"});
        PageIRecipe page5 = new PageIRecipe(shapedOreRecipe);
        ArrayList<ItemStack> shapelessList = new ArrayList<ItemStack>();
        shapelessList.add(new ItemStack(Items.cauldron));
        shapelessList.add(new ItemStack(Items.golden_carrot));
        ShapelessRecipes shapelessRecipes = new ShapelessRecipes(new ItemStack(Items.blaze_rod), shapelessList);
        PageIRecipe page6 = new PageIRecipe(shapelessRecipes);
        ShapelessOreRecipe shapelessOreRecipe = new ShapelessOreRecipe(new ItemStack(Items.baked_potato), new Object[]{"ingotIron", "stairWood"});
        PageIRecipe page7 = new PageIRecipe(shapelessOreRecipe);
        PageSound page8 = new PageSound(page6, "mob.pig.say");
        PageFurnaceRecipe page9 = new PageFurnaceRecipe(new ItemStack(Items.potato));
        PageFurnaceRecipe page10 = new PageFurnaceRecipe(new ItemStack(Items.diamond_axe));

        ArrayList<PageAbstract> pages = new ArrayList<PageAbstract>();
        pages.add(page1);
        pages.add(page2);
        pages.addAll(PageHelper.pagesForLongText("HERE IS SOME TEXT FOR YOU TO DRAW LEWL. I AM VERY LONG FOR NOTHING MATE", Minecraft.getMinecraft().fontRenderer, new ItemStack(Items.diamond)));
        pages.add(page3);
        pages.add(page4);
        pages.add(page5);
        pages.add(page6);
        pages.add(page7);
        pages.add(page8);
        pages.add(page9);
        pages.add(page10);

        EntryBase entry1 = new EntryText(pages, "TestEntry1");
        EntryBase entry2 = new EntryText(pages, "TestEntry2");
        ArrayList<EntryAbstract> entries = new ArrayList<EntryAbstract>();
        entries.add(entry1);
        entries.add(entry2);

        /*
        for (int i = 6; i <= 25; i++) {
            EntryBase entryBase = new EntryText(pages, "TestEntry" + String.valueOf(i));
            entries.add(entryBase);
        }
        */

        CategoryBase category1 = new CategoryItemStack(entries, "TestCategory1", new ItemStack(Items.reeds));
        CategoryBase category2 = new CategoryItemStack(entries, "TestCategory2", new ItemStack(Blocks.brick_stairs));
        CategoryBase category3 = new CategoryItemStack(entries, "TestCategory3", new ItemStack(Blocks.dragon_egg));
        CategoryBase category4 = new CategoryItemStack(entries, "TestCategory4", new ItemStack(Items.skull, 1, 0));
        CategoryBase category5 = new CategoryItemStack(entries, "TestCategory5", new ItemStack(Blocks.fence_gate));
        ArrayList<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);

        /*
        for (int i = 6; i <= 25; i++) {
            CategoryBase categoryBase = new CategoryItemStack(entries, "TestCategory" + String.valueOf(i), new ItemStack(Items.diamond));
            categories.add(categoryBase);
        }
        */

        testBook1 = new Book(categories, "ItemTestBook", "Hello, I am a welcome message that's " +
                "just way too long and that says nothing other that I'm there for test purposes", "Test Book Number 1", new Color(171, 80, 30));

        GuideRegistry.registerBook(testBook1);
    }

    public static void setTestBook2() {
        PageBase page1 = new PageUnlocText("HERE IS SOME TEXT FOR YOU TO DRAW LEWL. I AM VERY LONG FOR NOTHING MATE");
        PageLocText page2 = new PageLocText("HERE IS SOME TEXT FOR YOU TO DRAW LEWL. I AM VERY LONG FOR NOTHING MATE");
        PageImage page3 = new PageImage(new ResourceLocation(ModInformation.GUITEXLOC + "testimage.png"));
        PageIRecipe page4 = new PageIRecipe(GameRegistry.addShapedRecipe(new ItemStack(Items.diamond), "XXX", "YYY", "ZZZ", 'X', Items.apple, 'Y', Blocks.beacon, 'Z', Items.beef));
        ShapedOreRecipe shapedOreRecipe = new ShapedOreRecipe(Items.beef, new Object[]{"XXX", "YYY", "ZZZ", 'X', "stairWood", 'Y', "stone", 'Z', "ingotIron"});
        PageIRecipe page5 = new PageIRecipe(shapedOreRecipe);
        ArrayList<ItemStack> shapelessList = new ArrayList<ItemStack>();
        shapelessList.add(new ItemStack(Items.cauldron));
        shapelessList.add(new ItemStack(Items.golden_carrot));
        ShapelessRecipes shapelessRecipes = new ShapelessRecipes(new ItemStack(Items.blaze_rod), shapelessList);
        PageIRecipe page6 = new PageIRecipe(shapelessRecipes);
        ShapelessOreRecipe shapelessOreRecipe = new ShapelessOreRecipe(new ItemStack(Items.baked_potato), new Object[]{"ingotIron", "stairWood"});
        PageIRecipe page7 = new PageIRecipe(shapelessOreRecipe);
        PageSound page8 = new PageSound(page6, "mob.pig.say");
        PageFurnaceRecipe page9 = new PageFurnaceRecipe(new ItemStack(Items.potato));
        PageFurnaceRecipe page10 = new PageFurnaceRecipe(new ItemStack(Items.diamond_axe));

        ArrayList<PageAbstract> pages = new ArrayList<PageAbstract>();
        pages.add(page1);
        pages.add(page2);
        pages.addAll(PageHelper.pagesForLongText("HERE IS SOME TEXT FOR YOU TO DRAW LEWL. I AM VERY LONG FOR NOTHING MATE", Minecraft.getMinecraft().fontRenderer, new ItemStack(Items.diamond)));
        pages.add(page3);
        pages.add(page4);
        pages.add(page5);
        pages.add(page6);
        pages.add(page7);
        pages.add(page8);
        pages.add(page9);
        pages.add(page10);

        EntryBase entry1 = new EntryText(pages, "TestEntry1");
        EntryBase entry2 = new EntryText(pages, "TestEntry2");
        ArrayList<EntryAbstract> entries = new ArrayList<EntryAbstract>();
        entries.add(entry1);
        entries.add(entry2);

        for (int i = 6; i <= 25; i++) {
            EntryBase entryBase = new EntryText(pages, "TestEntryHAHA" + String.valueOf(i));
            entries.add(entryBase);
        }

        CategoryBase category1 = new CategoryItemStack(entries, "TestCategory1", new ItemStack(Items.reeds));
        CategoryBase category2 = new CategoryItemStack(entries, "TestCategory2", new ItemStack(Blocks.brick_stairs));
        CategoryBase category3 = new CategoryItemStack(entries, "TestCategory3", new ItemStack(Blocks.dragon_egg));
        CategoryBase category4 = new CategoryItemStack(entries, "TestCategory4", new ItemStack(Items.skull, 1, 0));
        CategoryBase category5 = new CategoryItemStack(entries, "TestCategory5", new ItemStack(Blocks.fence_gate));
        ArrayList<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);

        for (int i = 6; i <= 25; i++) {
            CategoryBase categoryBase = new CategoryItemStack(entries, "TestCategory" + String.valueOf(i), new ItemStack(Items.diamond));
            categories.add(categoryBase);
        }

        CategoryBase category26 = new CategoryItemStack(entries, "TestCategory5", new ItemStack(Items.carrot_on_a_stick));
        categories.add(category26);

        testBook2 = new Book(categories, "ItemBookNumber2", "Hello, I am a welcome message that's " +
                "just way too long and that says nothing other that I'm there for test purposes, and that this is the second book", "Teste Bouko Dos", new Color(50, 150, 102));

        GuideRegistry.registerBook(testBook2);
    }
}
