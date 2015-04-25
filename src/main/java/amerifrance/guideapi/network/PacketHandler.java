package amerifrance.guideapi.network;

import amerifrance.guideapi.ModInformation;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

    public static SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(ModInformation.CHANNEL);

    public static void registerPackets() {
        INSTANCE.registerMessage(PacketSyncHome.class, PacketSyncHome.class, 0, Side.SERVER);
        INSTANCE.registerMessage(PacketSyncCategory.class, PacketSyncCategory.class, 1, Side.SERVER);
        INSTANCE.registerMessage(PacketSyncEntry.class, PacketSyncEntry.class, 2, Side.SERVER);
    }
}
