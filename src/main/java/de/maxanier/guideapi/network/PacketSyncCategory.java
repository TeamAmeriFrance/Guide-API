package de.maxanier.guideapi.network;

import de.maxanier.guideapi.api.IGuideItem;
import de.maxanier.guideapi.api.util.NBTBookTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

public class PacketSyncCategory {

    static void encode(PacketSyncCategory msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.category);
        buf.writeInt(msg.page);
    }

    static PacketSyncCategory decode(FriendlyByteBuf buf) {
        PacketSyncCategory msg = new PacketSyncCategory();
        msg.category = buf.readInt();
        msg.page = buf.readInt();
        return msg;
    }

    public static void handle(final PacketSyncCategory msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        ServerPlayer player = ctx.getSender();
        Validate.notNull(player);
        ctx.enqueueWork(() -> {
            ItemStack book = player.getOffhandItem();
            if (book.isEmpty() || !(book.getItem() instanceof IGuideItem))
                book = player.getMainHandItem();

            if (!book.isEmpty() && book.getItem() instanceof IGuideItem) {
                if (msg.category != -1 && msg.page != -1) {
                    if (!book.hasTag())
                        book.setTag(new CompoundTag());

                    book.getTag().putInt(NBTBookTags.CATEGORY_TAG, msg.category);
                    book.getTag().putInt(NBTBookTags.ENTRY_PAGE_TAG, msg.page);
                    book.getTag().remove(NBTBookTags.ENTRY_TAG);
                }
            }
        });
        ctx.setPacketHandled(true);
    }

    public int category;
    public int page;

    public PacketSyncCategory() {
        this.category = -1;
        this.page = -1;
    }

    public PacketSyncCategory(int category, int page) {
        this.category = category;
        this.page = page;
    }

}
