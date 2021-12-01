package de.maxanier.guideapi.network;

import de.maxanier.guideapi.api.IGuideItem;
import de.maxanier.guideapi.api.util.NBTBookTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

public class PacketSyncHome {

    static void encode(PacketSyncHome msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.page);
    }

    static PacketSyncHome decode(FriendlyByteBuf buf) {
        PacketSyncHome msg = new PacketSyncHome();
        msg.page = buf.readInt();
        return msg;
    }

    public static void handle(final PacketSyncHome msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        ServerPlayer player = ctx.getSender();
        Validate.notNull(player);
        ctx.enqueueWork(() -> {
            ItemStack book = player.getOffhandItem();
            if (book.isEmpty() || !(book.getItem() instanceof IGuideItem))
                book = player.getMainHandItem();

            if (!book.isEmpty() && book.getItem() instanceof IGuideItem && msg.page != -1) {
                if (!book.hasTag())
                    book.setTag(new CompoundTag());
                book.getTag().putInt(NBTBookTags.CATEGORY_PAGE_TAG, msg.page);
                book.getTag().remove(NBTBookTags.CATEGORY_TAG);
                book.getTag().remove(NBTBookTags.ENTRY_TAG);
            }
        });
        ctx.setPacketHandled(true);
    }

    public int page;

    public PacketSyncHome() {
        this.page = -1;
    }

    public PacketSyncHome(int page) {
        this.page = page;
    }
}
