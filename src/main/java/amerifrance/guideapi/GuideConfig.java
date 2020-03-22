package amerifrance.guideapi;


import api.GuideAPI;
import api.impl.Book;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

public class GuideConfig {
    /**
     * Synced to clients.
     * Only loaded on world load
     */
    public static final Server SERVER;
    /**
     * For side independent configuration. Not synced.
     * Loaded after registry events but before setup
     */
    public static final Common COMMON;

    private static final ForgeConfigSpec serverSpec;
    private static final ForgeConfigSpec commonSpec;


    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static void registerConfigs() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverSpec);
        FMLJavaModLoadingContext.get().getModEventBus().register(GuideConfig.class);
    }

    public static class Server {

        public final ForgeConfigSpec.BooleanValue canSpawnWithBook;

        Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Server configuration settings").push("server");
            canSpawnWithBook = builder.comment("Allows books to spawn with new players.\nThis is a global override for all books.").define("canSpawnWithBook",true);
            builder.pop();
        }

//        for ( Book book : GuideAPI.getBooks().values()) TODO
//                SPAWN_BOOKS.put(book, config.getBoolean(book.getRegistryName().toString(), "Books.Spawn", book.shouldSpawnWithBook(), ""));
//
//        config.setCategoryComment("Books.Spawn", "If true, the user will spawn with the book.\nThis defaults to the value the book owner has set and is overridden by this config.");
    }

    public static class Common {

        public final ForgeConfigSpec.BooleanValue enableLogging;


        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common configurations settings").push("common");
            enableLogging = builder.comment("Enables extra information being printed to the console.").define("enableLogging",true);
            builder.pop();
        }
    }

}
