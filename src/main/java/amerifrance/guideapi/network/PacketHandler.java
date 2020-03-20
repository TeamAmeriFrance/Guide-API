package amerifrance.guideapi.network;

import amerifrance.guideapi.GuideMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;


public class PacketHandler {
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static byte packetId = 0;


    public static SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(GuideMod.ID,"main")).clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(()->PROTOCOL_VERSION).simpleChannel();



    public static void registerPackets() {
        INSTANCE.registerMessage(nextID(),PacketSyncEntry.class,PacketSyncEntry::encode,PacketSyncEntry::decode,PacketSyncEntry::handle);
        INSTANCE.registerMessage(PacketSyncHome.class, PacketSyncHome.class, 0, Side.SERVER);
        INSTANCE.registerMessage(PacketSyncCategory.class, PacketSyncCategory.class, 1, Side.SERVER);
    }

    protected static int nextID() {
        return packetId++;
    }
}
