#Guide-API [![Build Status](http://tehnut.info/jenkins/buildStatus/icon?job=Guide-API)](http://tehnut.info/jenkins/job/Guide-API/) [![Documentation Status](https://readthedocs.org/projects/guide-api/badge/?version=latest)](https://readthedocs.org/projects/guide-api/?badge=latest)

Library mod for easy creation of guide books. Also allows book creation via JSON files.

##Useful Links
* [Jenkins](http://tehnut.info/jenkins/job/Guide-API/)
* [CurseForge](http://minecraft.curseforge.com/mc-mods/228832-guide-api)
* [Javadocs](http://tehnut.info/jenkins/job/Guide-API/javadoc/)
* [ReadTheDocs](http://guide-api.readthedocs.org/en/latest/)

##Mods that support Guide-API
The ones we know of at least

* [Sanguimancy](http://minecraft.curseforge.com/mc-mods/223722-sanguimancy)
* [Creative Concoctions](https://github.com/TeamAmeriFrance/CreativeConcoctions)
* [Blood Magic](http://minecraft.curseforge.com/mc-mods/224791-blood-magic) (*In progress*)

##Issue Reporting

Please include the following:

* Minecraft version
* Guide-API version
* Forge version/build
* Versions of any mods potentially related to the issue
* Any relevant screenshots are greatly appreciated
* For crashes:
 * Steps to reproduce
 * Latest Forge log or crash log

##Developer Information
Once we have our [ReadTheDocs](http://guide-api.readthedocs.org/en/latest/) page setup correctly, this information (and more) will be moved there.

###Adding **Guide-API** to your project
In order to use **Guide-API**, you first need to add it to your project. You can do that by one of two ways:

1. Adding the following code block to your `build.gradle`:

  ```
  repositories {
    maven { url 'http://tehnut.info/maven/' } 
  }

  dependencies {
    compile "info.amerifrance.guideapi:Guide-API:1.7.10-1.0-15:deobf"
  }
  ```
  This will grab **Guide-API** version 1.0 build 15 for Minecraft 1.7.10 from our Maven repository.
  
  Once you have done that, run `gradlew [eclipse|idea]` to pull the dependency and add it to your project.

2. Downloading the `deobf` version of **Guide-API** from either Jenkins or CurseForge and putting it in `../libs/` in the root of your project. This is the same folder that `../gradle/` and `../src/` are in. You may need to create the folder yourself. Once you have done that, run `gradlew [eclipse|idea]` to add the library to your project.

###Creating a basic book
Now that you have **Guide-API** in your project, you can begin creating your own guide book. To do so, I recommend creating a package for all your guide related things. Depending on how in-depth you go, the amount of organization you need gets... a bit insane. If you plan to just cover the basics, a single class file will do.

The structure of a book is relatively simple:

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

        myBook = new Book(categories, "My book title", "My welcome message", "My book name", Color.GREEN); // Create your book from the category list
        GuideRegistry.registerBook(myBook); // Register your book with Guide-API
    }
}

```

Once you have your book setup how you want and wish to check it out in-game, call `GuideMyMod.buildGuide()` during `FMLPreinitializationEvent` or `FMLInitializationEvent`.

You should end up with something similar to [this](http://tehnut.info/files/examplebook.mp4).

##Modpack Permissions
For full details, view our license. Distributor rights are automatically given to any user who wishes to include the mod in their modpack if the intent is not malicious and/or commercial. We reserve the right to change this section as needed.
