package amerifrance.guideapi.test;

import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.Category;
import amerifrance.guideapi.objects.Entry;
import amerifrance.guideapi.objects.Page;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class TestBooks {

    public static Book testBook1;

    public static void setTestBook1() {
        Page page1 = new Page("I am page 1");
        Page page2 = new Page("I am page 2");
        ArrayList<Page> pages = new ArrayList<Page>();
        pages.add(page1);
        pages.add(page2);

        Entry entry1 = new Entry(pages, "TestEntry1");
        Entry entry2 = new Entry(pages, "TestEntry2");
        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(entry1);
        entries.add(entry2);

        Category category1 = new Category(entries, "TestCategory1", new ItemStack(Items.reeds));
        Category category2 = new Category(entries, "TestCategory2", new ItemStack(Blocks.brick_stairs));
        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(category1);
        categories.add(category2);

        testBook1 = new Book(categories, "ItemTestBook");
    }
}
