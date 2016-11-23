package amerifrance.guideapi;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.impl.Book;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    // Settings
    public static boolean enableLogging;
    public static boolean canSpawnWithBooks;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {
        String category;

        category = "Books";
        config.addCustomCategoryComment(category, "All settings related to Books.");
        canSpawnWithBooks = config.getBoolean("canSpawnWithBooks", category, true, "Allows books to spawn with new players.\nThis is a global override for all books.");

        category = "General";
        config.addCustomCategoryComment(category, "Miscellaneous settings.");
        enableLogging = config.getBoolean("enableLogging", category, true, "Enables extra information being printed to the console.");

        config.save();
    }

    public static void handleBookConfigs() {
        for (Book book : GuideAPI.BOOKS)
            book.setSpawnWithBook(config.get("Books.Spawn", book.getRegistryName().toString(), book.isSpawnWithBook()).getBoolean());

        config.setCategoryComment("Books.Spawn", "If true, the user will spawn with the book.\nThis defaults to the value the book owner has set and is overridden by this config.");
        config.save();
    }
}
