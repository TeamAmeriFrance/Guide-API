package de.maxanier.guideapi.network;

import de.maxanier.guideapi.GuideMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;


public class PacketHandler {
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static byte packetId = 0;


    public static SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(GuideMod.ID, "main")).clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();


    public static void registerPackets() {
        INSTANCE.registerMessage(nextID(), PacketSyncEntry.class, PacketSyncEntry::encode, PacketSyncEntry::decode, PacketSyncEntry::handle);
        INSTANCE.registerMessage(nextID(), PacketSyncCategory.class, PacketSyncCategory::encode, PacketSyncCategory::decode, PacketSyncCategory::handle);
        INSTANCE.registerMessage(nextID(), PacketSyncHome.class, PacketSyncHome::encode, PacketSyncHome::decode, PacketSyncHome::handle);
    }

    protected static int nextID() {
        return packetId++;
    }
}
