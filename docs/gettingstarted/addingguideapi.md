Adding Guide-API
==========================

In order to use Guide-API, you need to add it to your project. You can do this (easily) in 3 different ways:

##Method 1 - Gradle
*This is the preferred method*

Add the following code block to your `build.gradle`:

```
repositories {
  maven { url 'http://tehnut.info/maven/' }
}

dependencies {
  compile "info.amerifrance.guideapi:Guide-API:1.7.10-1.0-15:deobf"
}
```

This will tell the buildscript to grab Guide-API version `1.0` build `15` for Minecraft `1.7.10` that is de-obfuscated from our [Maven](http://tehnut.info/maven/) repository.

Once that is done, run `gradlew [eclipse|idea]` to pull the dependency and add it to your project's build path. 

##Method 2 - Libs folder
Downloading the `deobf` version of the mod from either [Jenkins](http://tehnut.info/jenkins/job/Guide-API/) or [CurseForge](http://minecraft.curseforge.com/mc-mods/228832-guide-api) and putting it in `../libs/` in the root of your project. This is the same folder that `../gradle/` and `../src/` are in. You may need to create the folder yourself. Once you have done that, run `gradlew [eclipse|idea]` to add the dependency to your project.

Don't forget to add `/libs/*` to your `.gitignore`. It is bad practice to upload them to the repository.

##Method 3 - Just API
*This method is only for those who want only the very basic features.*

Download the `amerifrance.guideapi.api` package from the repository and add it at `../src/api/java/`. Add that folder to your build-path as you would any other API package.