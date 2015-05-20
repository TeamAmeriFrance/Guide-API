Creating a Basic Book
==========================

##Book Structure
The structure is relatively simple:

* [Books](https://github.com/TeamAmeriFrance/Guide-API/blob/master/src/main/java/amerifrance/guideapi/api/base/Book.java) are made up of lists of [Categories](https://github.com/TeamAmeriFrance/Guide-API/blob/master/src/main/java/amerifrance/guideapi/api/base/CategoryBase.java).
* [Categories](https://github.com/TeamAmeriFrance/Guide-API/blob/master/src/main/java/amerifrance/guideapi/api/base/CategoryBase.java) are made up of lists of [Entries](https://github.com/TeamAmeriFrance/Guide-API/blob/master/src/main/java/amerifrance/guideapi/api/base/EntryBase.java).
* [Entries](https://github.com/TeamAmeriFrance/Guide-API/blob/master/src/main/java/amerifrance/guideapi/api/base/EntryBase.java) are made up of lists of [Pages](https://github.com/TeamAmeriFrance/Guide-API/blob/master/src/main/java/amerifrance/guideapi/api/base/PageBase.java).

Now, let's create a single Book with a single Category containing a single Entry that contains two pages. The class will be named `GuideMyMod.java` and will be located in `com.example.mymod`.

**Note**: Normally you would use unlocalized strings for this, but for the sake of readability, I opted to use localized strings.

```java
package com.example.mymod;

import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.categories.CategoryItemStack;
import amerifrance.guideapi.entries.EntryText;
import amerifrance.guideapi.pages.PageFurnaceRecipe;
import amerifrance.guideapi.pages.PageIRecipe;
import amerifrance.guideapi.pages.PageLocText;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GuideMyMod {

    public static Book myBook;

    public static void buildGuide() {
        List<EntryAbstract> entries = new ArrayList<EntryAbstract>(); // Create the list for this categories entries.

        ArrayList<IPage> pages1 = new ArrayList<IPage>(); // Create the list for this entries pages.
        pages1.add(new PageLocText("This is a page in my guide!")); // Create a page with text and add it to your pages1 list.
        entries.add(new EntryText(pages1, "My entry 1")); // Add your pages1 list to the entry list.

        ArrayList<IPage> pages2 = new ArrayList<IPage>(); // Create the list for this entries pages.
        pages2.add(new PageIRecipe(new ShapedOreRecipe(Items.apple, "AAA", "BBB", "CCC", 'A', "ingotIron", 'B', Blocks.anvil, 'C', Items.potato))); // Create a recipe page and add it to your pages2 list.
        pages2.add(new PageFurnaceRecipe("oreGold")); // Create a furnace recipe page and add it to your pages2 list.
        entries.add(new EntryText(pages2, "My entry 2")); // Add your pages2 list to the entry list.

        ArrayList<CategoryAbstract> categories = new ArrayList<CategoryAbstract>(); // Create the list for this book's categories
        categories.add(new CategoryItemStack(entries, "My category", new ItemStack(Items.painting))); // Add your entry list to the category list.

        BookBuilder builder =  new BookBuilder(); // Create a new instance of the book builder
        builder.setCategories(categories); // Set the category list of the book
        builder.setUnlocBookTitle("My book title"); // Set the unlocalized book title
        builder.setUnlocWelcomeMessage("My welcome message"); // Set the unlocalized welcome message
        builder.setUnlocDisplayName("My book name"); // Set the unlocalized item display name
        builder.setBookColor(Color.GREEN); // Set the book color
        myBook = builder.build(); // Create your book from the information provided with your BookBuilder
        
        GuideRegistry.registerBook(myBook); // Register your book with Guide-API
    }
}
```

Once you have your book setup how you want and wish to check it out in-game, call `GuideMyMod.buildGuide()` during `FMLPreinitializationEvent` or `FMLInitializationEvent`.

You should end up with something similar to [this](http://tehnut.info/files/examplebook.mp4).