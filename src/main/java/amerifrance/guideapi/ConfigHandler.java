package amerifrance.guideapi;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    // Categories
    public static String general = "General";

    // Settings
    public static boolean enableLogging;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {
        enableLogging = config.get(general, "enableLogging", true).getBoolean();

        config.save();
    }
}
