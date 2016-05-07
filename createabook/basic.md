Creating a Basic Book
==========================

##Book Structure
The structure is relatively simple:

* [Books](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Book.java) are made up of lists of [Categories](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Category.java).
* [Categories](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Category.java) are made up of lists of [Entries](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Entry.java).
* [Entries](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Entry.java) are made up of lists of [Pages](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Page.java).

Now, let's create a single Book with a single Category containing a single Entry that contains two pages. The class will be named `GuideMyMod.java` and will be located in `com.example.mymod`.

**Note**: Normally you would use unlocalized strings for this, but for the sake of readability, I opted to use localized strings.

```java
package com.example.mymod;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageFurnaceRecipe;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageText;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExampleBook {

    public static Book myGuide;

    public static void buildGuide() {
        Map<ResourceLocation, EntryAbstract> entries = new HashMap<ResourceLocation, EntryAbstract>();

        List<IPage> pages1 = new ArrayList<IPage>();
        pages1.add(new PageText("This is a page in my guide!"));
        entries.put(new ResourceLocation("example", "entry_one"), new EntryItemStack(pages1, "Entry One", new ItemStack(Blocks.BEACON)));

        List<IPage> pages2 = new ArrayList<IPage>();
        pages2.add(new PageIRecipe(new ShapedOreRecipe(Items.DIAMOND_SWORD, "D", "D", "S", 'D', Items.DIAMOND, 'S', Items.STICK)));
        pages2.add(new PageFurnaceRecipe("oreGold"));
        entries.put(new ResourceLocation("example", "entry_two"), new EntryItemStack(pages2, "Entry Two", new ItemStack(Items.DIAMOND_SWORD)));

        List<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();
        categories.add(new CategoryItemStack(entries, "My Category", new ItemStack(Blocks.COMMAND_BLOCK)));

        myGuide = new Book();
        myGuide.setTitle("My Guide");
        myGuide.setDisplayName("My Guide");
        myGuide.setAuthor("ExampleDude");
        myGuide.setColor(Color.CYAN);
        myGuide.setCategoryList(categories);
        myGuide.setRegistryName("FirstGuide");
        
        GameRegistry.register(myGuide);
    }
}

```

Once you have your book setup how you want and wish to check it out in-game, call `GuideMyMod.buildGuide()` during `FMLPreinitializationEvent`.

To set a model for your book, use `GuideAPI.setModel(myGuide);` or one of the overloads in your `ClientProxy` during `FMLPreInitializationEvent`.

You should end up with something similar to [this](http://tehnut.info/files/examplebook.mp4).