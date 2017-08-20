package amerifrance.guideapi;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.network.PacketHandler;
import amerifrance.guideapi.proxy.CommonProxy;
import amerifrance.guideapi.util.AnnotationHandler;
import amerifrance.guideapi.util.EventHandler;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;

@Mod(modid = GuideMod.ID, name = GuideMod.NAME, version = GuideMod.VERSION)
public class GuideMod {

    public static final String NAME = "Guide-API";
    public static final String ID = "guideapi";
    public static final String CHANNEL = "GuideAPI";
    public static final String VERSION = "@VERSION@";
    public static final boolean IS_DEV = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    @Mod.Instance(ID)
    public static GuideMod INSTANCE;

    @SidedProxy(clientSide = "amerifrance.guideapi.proxy.ClientProxy", serverSide = "amerifrance.guideapi.proxy.CommonProxy")
    public static CommonProxy PROXY;

    public static File configDir;
    public static ASMDataTable dataTable;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory(), NAME);
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir, NAME + ".cfg"));

        GuideAPI.initialize();
        dataTable = event.getAsmData();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, PROXY);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        PacketHandler.registerPackets();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.initColors();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ConfigHandler.handleBookConfigs();

        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES)
            guide.getRight().handlePost(GuideAPI.getStackFromBook(guide.getLeft()));
    }
}
