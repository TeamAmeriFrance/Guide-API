Adding Guide-API
==========================

In order to use Guide-API, you need to add it to your project. You can do this (easily) in 3 different ways:

## Gradle
*This is the preferred method*

Add the following code block to your `build.gradle`:

```
repositories {
  maven { url 'http://tehnut.info/maven/' }
}

dependencies {
  deobfCompile "info.amerifrance.guideapi:Guide-API:1.9-2.0.0-31"
}
```

This will tell the buildscript to grab Guide-API version `2.0.0` build `31` for Minecraft `1.9` that is de-obfuscated at compile time from our [Maven](http://tehnut.info/maven/) repository.

If you want to use just the API instead of including the full mod in your workspace, append the `deobfCompile` line with `:api`.

Once that is done, run `gradlew [eclipse|idea]` to pull the dependency and add it to your project's build path. 
