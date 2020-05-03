package de.maxanier.guideapi;


import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.impl.Book;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class GuideConfig {

    /**
     * For side independent configuration. Not synced.
     * Loaded after registry events but before setup
     */
    public static Common COMMON;

    public static class Common {

        public final ForgeConfigSpec.BooleanValue canSpawnWithBook;

        public final ForgeConfigSpec.BooleanValue enableLogging;
        public final Map<Book, ForgeConfigSpec.BooleanValue> SPAWN_BOOKS = new HashMap<>();


        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common configurations settings").push("common");
            enableLogging = builder.comment("Enables extra information being printed to the console.").define("enableLogging", true);
            canSpawnWithBook = builder.comment("Allows books to spawn with new players.\nThis is a global override for all books if set to false.").define("canSpawnWithBook", true);
            builder.comment("If the player should spawn with this book").push("spawnBook");
            for (Book book : GuideAPI.getBooks().values()) {
                SPAWN_BOOKS.put(book, builder.define(book.getRegistryName().getNamespace() + "-" + book.getRegistryName().getPath(), book.shouldSpawnWithBook()));
            }
            builder.pop();
            builder.pop();
        }
    }

    public static void buildConfiguration() {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        ForgeConfigSpec commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonSpec);
        FMLJavaModLoadingContext.get().getModEventBus().register(GuideConfig.class);
    }

}
