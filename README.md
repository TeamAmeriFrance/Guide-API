# Guide-API - Village and Pillage [![](http://cf.way2muchnoise.eu/versions/guide-api-village-and-pillage.svg)](https://www.curseforge.com/minecraft/mc-mods/guide-api-village-and-pillage)
** 1.18 version not used in any mod yet, therefore breaking changes still possible, let me know if you already use
this **
Library mod for easy creation of guide books.

Fork by maxanier of Guide-API for Minecraft 1.14+ (Village and Pillage)  
Original mod by TehNut and Tombenpotter. https://github.com/TeamAmeriFrance/Guide-API

Allows easy creation of a guide book for your mod.
Books are mostly maintained by Guide-API (it registers them, it puts them in its own creative tab, etc).

The guide book is created mostly in code whereas e.g. Patchouli is mostly JSON based. This means:
- Add or change content based on the configuration of your mod.
- Wrap lines and pages based automatically, so localized strings don't overflow
- Refer to the set keybindings
- Refer to internal constants, balancing values etc so they are automatically changed in the book if you change them in the mod
- Add custom page types, recipe types, etc.
- Use helper methods to generate parts of the book automatically

What else?
- No hard dependency, if Guide-API is not present at runtime the book won't be there, but everything else works
- Use TextFormatting and manual \n
- Several ready-to-use page types like text, item/block focused text, recipe, and image pages 


## Useful Links
* [CurseForge](http://minecraft.curseforge.com/mc-mods/228832-guide-api)
* [Modrinth](https://modrinth.com/mod/guide-api)

## Original Links - Mostly still correct
* [ReadTheDocs](http://guide-api.readthedocs.org/en/latest/)

## Mods that make use of Guide-API
The ones we know of at least

* [Vampirism](https://www.curseforge.com/minecraft/mc-mods/vampirism-become-a-vampire)


## Issue Reporting

Please include the following:

* Minecraft version
* Guide-API version
* Forge version/build
* Versions of any mods potentially related to the issue
* Any relevant screenshots are greatly appreciated
* For crashes:
 * Steps to reproduce
 * Latest Forge log or crash log

## Developer Information
The original information from Guide-API can be found [here](http://guide-api.readthedocs.org/en/latest/).
The fork is still very similar.

If you need any assistance adding your own guide book, or if you are missing a feature, create an issue here.


## Setup
#### Setup Gradle build script
You should be able to include it with the following in your `build.gradle`:
```gradle
repositories {
    //Maven repo for Guide-API
    maven {
        url = "https://maven.maxanier.de"
    }
}
dependencies {
    //Compile against and provide deobf version of Guide-API
    compile fg.deobf("de.maxanier.guideapi:Guide-API-VP:${project.guideapi_version}")

}
```

#### Choose a version


For a list of available Vampirism version, see [CurseForge](https://www.curseforge.com/minecraft/mc-mods/guide-api-village-and-pillage) or the [maven listing](https://maven.maxanier.de/de/maxanier/guideapi/Guide-API-VP/) .

These properties can be set in a file named `gradle.properties`, placed in the same directory as your `build.gradle` file.
Example `gradle.properties`:
```
guideapi_version=1.14.4-2.2.1
```

#### Rerun Gradle setup commands
Please run the commands that you used to setup your development environment again.
E.g. `gradlew` or `gradlew --refresh-dependencies`
Refresh/Restart your IDE afterwards.

#### Examples
Checkout the test books [here](https://github.com/maxanier/Guide-API/tree/1.14.4_latest/src/main/java/de/maxanier/guideapi/test)  

Checkout Vampirism which adds an extensive guide book [here](https://github.com/TeamLapen/Vampirism/blob/1.14/src/main/java/de/teamlapen/vampirism/modcompat/guide/GuideBook.java)

#### Crafting recipe
Add a crafting recipe for your book like this
```
{
  "result": {
    "item": "guideapi_vp:vampirism-guidebook"
  },
  "ingredients": [
    {
      "item": "vampirism:vampire_fang"
    },
    {
      "item": "minecraft:book"
    }
  ],
  "conditions": [
    {
      "type": "forge:mod_loaded",
      "modid": "guideapi_vp"
    }
  ],
  "type": "minecraft:crafting_shapeless"
}
```
#### API stability
There isn't a dedicated API package since mods can/have to make use of most of GuideAPI's classes.  
Binary breaking changes are only introduced with new main versions `*.0.0-beta.1` or new MC versions.
New features are introduced with major versions `*.*.0` (possibly with alpha and beta stages) and bugfixes are introduced with minor versions (without alpha and beta phase).


## Modpack Permissions
For full details, view our license. Distributor rights are automatically given to any user who wishes to include the mod in their modpack if the intent is not malicious and/or commercial. We reserve the right to change this section as needed.
