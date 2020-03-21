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

public class PacketSyncHome {

    public int page;

    public PacketSyncHome() {
        this.page = -1;
    }

    public PacketSyncHome(int page) {
        this.page = page;
    }

    static void encode(PacketSyncHome msg, PacketBuffer buf){
        buf.writeInt(msg.page);
    }

    static PacketSyncHome decode(PacketBuffer buf){
        PacketSyncHome msg=new PacketSyncHome();
        msg.page = buf.readInt();
        return msg;
    }

    public static void handle(final PacketSyncHome msg, Supplier<NetworkEvent.Context> contextSupplier){
        final NetworkEvent.Context ctx=contextSupplier.get();
        ServerPlayerEntity player = ctx.getSender();
        Validate.notNull(player);
        ctx.enqueueWork(()->{
            ItemStack book = player.getHeldItemOffhand();
            if (book.isEmpty() || !(book.getItem() instanceof IGuideItem))
                book = player.getHeldItemMainhand();

            if (!book.isEmpty() && book.getItem() instanceof IGuideItem && msg.page != -1) {
                if (!book.hasTag())
                    book.setTag(new CompoundNBT());
                book.getTag().putInt(NBTBookTags.CATEGORY_PAGE_TAG, msg.page);
                book.getTag().remove(NBTBookTags.CATEGORY_TAG);
                book.getTag().remove(NBTBookTags.ENTRY_TAG);
            }
        });
        ctx.setPacketHandled(true);
    }
}
