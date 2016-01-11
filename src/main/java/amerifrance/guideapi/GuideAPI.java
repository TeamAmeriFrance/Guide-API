package amerifrance.guideapi;

import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.items.ItemsRegistry;
import amerifrance.guideapi.network.PacketHandler;
import amerifrance.guideapi.proxies.CommonProxy;
import amerifrance.guideapi.util.EventHandler;
import amerifrance.guideapi.util.LootGenerator;
import amerifrance.guideapi.util.serialization.BookCreator;
import com.google.gson.GsonBuilder;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, dependencies = ModInformation.DEPEND, acceptedMinecraftVersions = "[1.8.8,1.8.9]")
public class GuideAPI {

    public static CreativeTabs tabGuide = new CreativeTabs(ModInformation.ID + ".creativeTab") {
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(Items.book);
        }

        @Override
        public Item getTabIconItem() {
            return Items.book;
        }
    };

    @Mod.Instance
    public static GuideAPI instance;
    @SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
    public static CommonProxy proxy;

    private static File configDir;

    public static File getConfigDir() {
        return configDir;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        configDir = new File(event.getModConfigurationDirectory(), ModInformation.NAME);
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir.getPath(), ModInformation.NAME + ".cfg"));
        ItemsRegistry.registerItems();
        proxy.initRenders();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
        PacketHandler.registerPackets();

        GuideRegistry.bookBuilder = new GsonBuilder();
        BookCreator.registerCustomSerializers(GuideRegistry.bookBuilder);

        TestBook.registerTests(ModInformation.VERSION.equals("@VERSION@") ? 5 : 0);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        BookCreator.registerJsonBooks(GuideRegistry.bookBuilder);
        LootGenerator.registerLoot();
    }
}
