package amerifrance.guideapi;

import amerifrance.guideapi.items.ItemTestBook;
import amerifrance.guideapi.proxies.CommonProxy;
import amerifrance.guideapi.test.TestBooks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, dependencies = ModInformation.DEPEND)
public class GuideAPI {

    public static Logger logger = LogManager.getLogger(ModInformation.NAME);
    public static final String commonProxy = "amerifrance.guideapi.proxies.CommonProxy";
    public static final String clientProxy = "amerifrance.guideapi.proxies.ClientProxy";

    @Mod.Instance
    public static GuideAPI instance;
    @SidedProxy(clientSide = clientProxy, serverSide = commonProxy)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        ConfigHandler.init(new File(event.getModConfigurationDirectory() + "/Guide-API" + ".cfg"));
        GameRegistry.registerItem(new ItemTestBook(), "ItemTestBook");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);

        TestBooks.setTestBook1();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
