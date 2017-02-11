Creating a Basic Book
==========================

##Book Structure
The structure is relatively simple:

* [Books](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Book.java) are made up of lists of [Categories](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Category.java).
* [Categories](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Category.java) are made up of lists of [Entries](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Entry.java).
* [Entries](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Entry.java) are made up of lists of [Pages](https://github.com/TeamAmeriFrance/Guide-API/blob/1.9/src/main/java/amerifrance/guideapi/api/impl/Page.java).

Now, let's create a single Book with a single Category containing a single Entry that contains two pages. The class will be named `ExampleBook.java` and will be located in `com.example.mymod`.

**Note**: Normally you would use unlocalized strings for this, but for the sake of readability, I opted to use localized strings.

```java
package com.example.mymod;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.List;

@GuideBook
public class ExampleBook implements IGuideBook {

    public static Book myGuide;

    @Nonnull
    @Override
    public Book buildBook() {
        // Create the map of entries. A LinkedHashMap is used to retain the order of entries.
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();

        // Creation of our first entry.
        List<IPage> pages1 = new ArrayList<IPage>();
        pages1.add(new PageText("This is a page in my guide!"));
        entries.put(new ResourceLocation("example", "entry_one"), new EntryItemStack(pages1, "Entry One", new ItemStack(Blocks.BEACON)));

        // Creation of our second entry.
        List<IPage> pages2 = new ArrayList<IPage>();
        pages2.add(new PageIRecipe(new ShapedOreRecipe(Items.DIAMOND_SWORD, "D", "D", "S", 'D', Items.DIAMOND, 'S', Items.STICK)));
        pages2.add(new PageFurnaceRecipe("oreGold"));
        entries.put(new ResourceLocation("example", "entry_two"), new EntryItemStack(pages2, "Entry Two", new ItemStack(Items.DIAMOND_SWORD)));

        // Setup the list of categories and add our entries to it.
        List<CategoryAbstract> categories = new ArrayList<CategoryAbstract>();
        categories.add(new CategoryItemStack(entries, "My Category", new ItemStack(Blocks.COMMAND_BLOCK)));

        // Setup the book's base information
        myGuide = new Book();
        myGuide.setTitle("My Guide");
        myGuide.setDisplayName("My Guide");
        myGuide.setAuthor("ExampleDude");
        myGuide.setColor(Color.CYAN);
        myGuide.setCategoryList(categories);
        myGuide.setRegistryName(new ResourceLocation("mymod", "first_guide"));
        return myGuide;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleModel(ItemStack bookStack) {
        // Use the default GuideAPI model 
        GuideAPI.setModel(myGuide);
    }

    @Override
    public void handlePost(ItemStack bookStack) {
        // Register a recipe so player's can obtain the book
        GameRegistry.addShapelessRecipe(bookStack, Items.BOOK, Items.PAPER);
    }
}
```

**Note:** Registration is now automatic. The annotation (`@GuideBook`) is searched for during `FMLPreInitializationEvent`. All classes annotated with this will be checked if they implement `IGuideBook` and marked for registration. `handleModel(...)` is called just after registration during `FMLPreInitializationEvent`. `handlePost(...)` is called during `FMLPostInitializationEvent`.

If your Guide references items from your mod, they must be initialized or it will crash. Make sure to add `before:guideapi` to your `@Mod` dependencies field.

You should end up with something similar to [this](http://tehnut.info/files/examplebook.mp4).
