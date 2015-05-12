package amerifrance.guideapi;

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
        canSpawnWithBooks = config.getBoolean("canSpawnWithBooks", category, true, "Allows books to spawn with new players.\nOnly affects books where the author enables the spawning feature.");

        category = "General";
        config.addCustomCategoryComment(category, "Miscellaneous settings.");
        enableLogging = config.getBoolean("enableLogging", category, true, "Enables extra information being printed to the console.");

        config.save();
    }
}
