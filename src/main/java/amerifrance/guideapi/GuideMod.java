package amerifrance.guideapi;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.item.ItemGuideBook;
import amerifrance.guideapi.network.PacketHandler;
import amerifrance.guideapi.proxy.CommonProxy;
import amerifrance.guideapi.util.APISetter;
import amerifrance.guideapi.util.AnnotationHandler;
import amerifrance.guideapi.util.EventHandler;
import lombok.Getter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;

@Mod(modid = GuideMod.ID, name = GuideMod.NAME, version = GuideMod.VERSION)
public class GuideMod {

    public static final String NAME = "Guide-API";
    public static final String ID = "guideapi";
    public static final String CHANNEL = "GuideAPI";
    public static final String VERSION = "@VERSION@";

    @Mod.Instance(ID)
    public static GuideMod INSTANCE;

    @SidedProxy(clientSide = "amerifrance.guideapi.proxy.ClientProxy", serverSide = "amerifrance.guideapi.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Getter
    private static File configDir;
    @Getter
    private static boolean isDev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory(), NAME);
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir, NAME + ".cfg"));

        GuideAPI.initialize();
        AnnotationHandler.gatherBooks(event.getAsmData());

        for (Book book : GuideAPI.getBooks().values()) {
            Item guideBook = new ItemGuideBook(book);
            guideBook.setRegistryName(book.getRegistryName().toString().replace(":", "-"));
            GameRegistry.register(guideBook);
            APISetter.setBookForStack(book, new ItemStack(guideBook));
        }

        proxy.handleModels();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        PacketHandler.registerPackets();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
//        TypeReaders.init();
//        JsonBookCreator.buildGson();

        proxy.initColors();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
//        JsonBookCreator.buildBooks();
        ConfigHandler.handleBookConfigs();
        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES)
            guide.getRight().handlePost(GuideAPI.getStackFromBook(guide.getLeft()));
    }
}
