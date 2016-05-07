package amerifrance.guideapi;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.item.ItemGuideBook;
import amerifrance.guideapi.network.PacketHandler;
import amerifrance.guideapi.proxy.CommonProxy;
import amerifrance.guideapi.util.EventHandler;
import amerifrance.guideapi.util.serialization.BookCreator;
import amerifrance.guideapi.util.serialization.TypeReaders;
import lombok.Getter;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod(modid = GuideMod.ID, name = GuideMod.NAME, version = GuideMod.VERSION, dependencies = GuideMod.DEPEND)
public class GuideMod {

    public static final String NAME = "Guide-API";
    public static final String ID = "guideapi";
    public static final String CHANNEL = "GuideAPI";
    public static final String DEPEND = "";
    public static final String VERSION = "@VERSION@";
    public static final String DOMAIN = "guideapi:";
    public static final String GUITEXLOC = DOMAIN + "textures/gui/";

    @Mod.Instance
    public static GuideMod instance;
    public static final String CLIENTPROXY = "amerifrance.guideapi.proxy.ClientProxy";
    public static final String COMMONPROXY = "amerifrance.guideapi.proxy.CommonProxy";
    @SidedProxy(clientSide = GuideMod.CLIENTPROXY, serverSide = GuideMod.COMMONPROXY)
    public static CommonProxy proxy;

    @Getter
    private static File configDir;
    @Getter
    private static boolean isDev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        configDir = new File(event.getModConfigurationDirectory(), NAME);
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir.getPath(), NAME + ".cfg"));

        GuideAPI.guideBook = new ItemGuideBook();
        GameRegistry.register(GuideAPI.guideBook.setRegistryName("ItemGuideBook"));

        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
        PacketHandler.registerPackets();

        TypeReaders.init();

        if (isDev())
            TestBook.registerTests(5);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());

        proxy.initColors();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        BookCreator.registerJsonBooks();
    }
}
