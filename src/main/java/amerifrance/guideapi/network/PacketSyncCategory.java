package amerifrance.guideapi.network;

import api.IGuideItem;
import api.util.NBTBookTags;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

public class PacketSyncCategory {

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

    static void encode(PacketSyncCategory msg, PacketBuffer buf){
        buf.writeInt(msg.category);
        buf.writeInt(msg.page);
    }

    static PacketSyncCategory decode(PacketBuffer buf){
        PacketSyncCategory msg=new PacketSyncCategory();
        msg.category=buf.readInt();
        msg.page = buf.readInt();
        return msg;
    }

    public static void handle(final PacketSyncCategory msg, Supplier<NetworkEvent.Context> contextSupplier){
        final NetworkEvent.Context ctx=contextSupplier.get();
        ServerPlayerEntity player = ctx.getSender();
        Validate.notNull(player);
        ctx.enqueueWork(()->{
            ItemStack book = player.getHeldItemOffhand();
            if (book.isEmpty() || !(book.getItem() instanceof IGuideItem))
                book = player.getHeldItemMainhand();

            if (!book.isEmpty() && book.getItem() instanceof IGuideItem) {
                if (msg.category != -1 && msg.page != -1) {
                    if (!book.hasTag())
                        book.setTag(new CompoundNBT());

                    book.getTag().putInt(NBTBookTags.CATEGORY_TAG, msg.category);
                    book.getTag().putInt(NBTBookTags.ENTRY_PAGE_TAG, msg.page);
                    book.getTag().remove(NBTBookTags.ENTRY_TAG);
                }
            }
        });
        ctx.setPacketHandled(true);
    }

}
