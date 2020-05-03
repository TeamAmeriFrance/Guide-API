package de.maxanier.guideapi;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.IGuideBook;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.network.PacketHandler;
import de.maxanier.guideapi.proxy.ClientProxy;
import de.maxanier.guideapi.proxy.CommonProxy;
import de.maxanier.guideapi.util.AnnotationHandler;
import de.maxanier.guideapi.util.ReloadCommand;
import net.minecraft.command.CommandSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

@Mod(value = GuideMod.ID)
public class GuideMod {

    public static final String NAME = "Guide-API VP";
    public static final String ID = "guideapi-vp";
    public static boolean inDev = false;

    public static GuideMod INSTANCE;

    public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public GuideMod() {
        INSTANCE = this;
        checkDevEnv();
        GuideAPI.initialize();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
    }

    private void setup(final FMLCommonSetupEvent event) {
        if (GuideConfig.COMMON == null) {
            throw new IllegalStateException("Did not build configuration, before configuration load. Make sure to call GuideConfig#buildConfiguration during one of the registry events");
        }
        PacketHandler.registerPackets();
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
        PROXY.initColors();

        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES)
            guide.getRight().handlePost(GuideAPI.getStackFromBook(guide.getLeft()));
    }

    private void checkDevEnv() {
        String launchTarget = System.getenv().get("target");
        if (launchTarget != null && launchTarget.contains("dev")) {
            inDev = true;
        }
    }

    private void onServerStarting(FMLServerStartingEvent event) {
        if (inDev) {
            event.getCommandDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("guide-api-vp").then(ReloadCommand.register()));
        }
    }
}
