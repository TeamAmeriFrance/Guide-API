package amerifrance.guideapi.network;

import amerifrance.guideapi.GuideMod;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(GuideMod.CHANNEL);

    public static void registerPackets() {
        INSTANCE.registerMessage(PacketSyncHome.class, PacketSyncHome.class, 0, Side.SERVER);
        INSTANCE.registerMessage(PacketSyncCategory.class, PacketSyncCategory.class, 1, Side.SERVER);
        INSTANCE.registerMessage(PacketSyncEntry.class, PacketSyncEntry.class, 2, Side.SERVER);
    }
}
