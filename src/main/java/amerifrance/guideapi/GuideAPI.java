package amerifrance.guideapi;

import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.item.ItemsRegistry;
import amerifrance.guideapi.network.PacketHandler;
import amerifrance.guideapi.proxy.CommonProxy;
import amerifrance.guideapi.util.EventHandler;
import amerifrance.guideapi.util.serialization.BookCreator;
import com.google.gson.GsonBuilder;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, dependencies = ModInformation.DEPEND)
public class GuideAPI {

    @Mod.Instance
    public static GuideAPI instance;
    @SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
    public static CommonProxy proxy;

    private static File configDir;
    private static boolean isDev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        configDir = new File(event.getModConfigurationDirectory(), ModInformation.NAME);
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir.getPath(), ModInformation.NAME + ".cfg"));
        ItemsRegistry.registerItems();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
        PacketHandler.registerPackets();

        GuideRegistry.bookBuilder = new GsonBuilder();
        BookCreator.registerCustomSerializers(GuideRegistry.bookBuilder);

        if (isDev())
            TestBook.registerTests(5, event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        BookCreator.registerJsonBooks(GuideRegistry.bookBuilder);
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static boolean isDev() {
        return isDev;
    }
}
