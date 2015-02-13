package amerifrance.guideapi.test;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.CategoryBase;
import amerifrance.guideapi.objects.EntryBase;
import amerifrance.guideapi.objects.PageBase;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import amerifrance.guideapi.objects.abstraction.AbstractPage;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;

public class TestBooks {

    public static Book testBook1;

    public static void setTestBook1() {
        PageBase page1 = new PageBase();
        PageBase page2 = new PageBase();
        ArrayList<AbstractPage> pages = new ArrayList<AbstractPage>();
        pages.add(page1);
        pages.add(page2);

        EntryBase entry1 = new EntryBase(pages, "TestEntry1");
        EntryBase entry2 = new EntryBase(pages, "TestEntry2");
        ArrayList<AbstractEntry> entries = new ArrayList<AbstractEntry>();
        entries.add(entry1);
        entries.add(entry2);

        for (int i = 6; i <= 25; i++) {
            EntryBase entryBase = new EntryBase(pages, "TestEntry" + String.valueOf(i));
            entries.add(entryBase);
        }

        CategoryBase category1 = new CategoryBase(entries, "TestCategory1", new ItemStack(Items.reeds));
        CategoryBase category2 = new CategoryBase(entries, "TestCategory2", new ItemStack(Blocks.brick_stairs));
        CategoryBase category3 = new CategoryBase(entries, "TestCategory3", new ItemStack(Blocks.dragon_egg));
        CategoryBase category4 = new CategoryBase(entries, "TestCategory4", new ItemStack(Items.skull, 1, 0));
        CategoryBase category5 = new CategoryBase(entries, "TestCategory5", new ItemStack(Blocks.fence_gate));
        ArrayList<AbstractCategory> categories = new ArrayList<AbstractCategory>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);

        for (int i = 6; i <= 25; i++) {
            CategoryBase categoryBase = new CategoryBase(entries, "TestCategory" + String.valueOf(i), new ItemStack(Items.diamond));
            categories.add(categoryBase);
        }

        testBook1 = new Book(categories, "ItemTestBook", new Color(171, 80, 30), "Hello, I am a welcome message that's " +
                "just way too long and that says nothing other that I'm there for test purposes",
                new ResourceLocation(ModInformation.GUITEXLOC + "book_colored.png"), new ResourceLocation(ModInformation.GUITEXLOC + "book_greyscale.png"));

    }
}
