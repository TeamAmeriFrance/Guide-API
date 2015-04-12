package amerifrance.guideapi;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    // Categories
    public static String general = "General";

    // Settings
    public static boolean enableLogging;
    public static boolean canSpawnWithBooks;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {
        enableLogging = config.get(general, "enableLogging", true).getBoolean();
        canSpawnWithBooks = config.get(general, "canSpawnWithBooks", true).getBoolean();

        config.save();
    }
}
