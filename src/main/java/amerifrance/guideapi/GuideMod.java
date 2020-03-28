package amerifrance.guideapi;

import amerifrance.guideapi.proxy.ClientProxy;
import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.network.PacketHandler;
import amerifrance.guideapi.proxy.CommonProxy;
import amerifrance.guideapi.util.AnnotationHandler;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

@Mod(value = GuideMod.ID)
public class GuideMod {

    public static final String NAME = "Guide-API";
    public static final String ID = "guideapi";
    public static final String CHANNEL = "GuideAPI";
    public static final String VERSION = "@VERSION@";
    public static boolean inDev = false;

    public static GuideMod INSTANCE;

    public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public GuideMod(){
        INSTANCE=this;
        checkDevEnv();
        GuideAPI.initialize();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::interModMsg);
    }

    private void setup(final FMLCommonSetupEvent event){
        PacketHandler.registerPackets();
    }

    private void loadComplete(final FMLLoadCompleteEvent event){
        PROXY.initColors();

        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES)
            guide.getRight().handlePost(GuideAPI.getStackFromBook(guide.getLeft()));
    }

    private void interModMsg(final InterModProcessEvent event){
        GuideAPI.getBooks().values().parallelStream().forEach(Book::initializeContent);
    }




    private void checkDevEnv() {
        String launchTarget = System.getenv().get("target");
        if (launchTarget != null && launchTarget.contains("dev")) {
            inDev = true;
        }
    }
}
