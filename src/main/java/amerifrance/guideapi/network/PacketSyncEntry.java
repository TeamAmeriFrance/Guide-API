package amerifrance.guideapi.network;

import api.IGuideItem;
import api.util.NBTBookTags;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

public class PacketSyncEntry implements IMessage {

    public int category;
    public ResourceLocation entry;
    public int page;

    public PacketSyncEntry() {
        this.category = -1;
        this.entry = new ResourceLocation("guideapi", "none");
        this.page = -1;
    }

    public PacketSyncEntry(int category, ResourceLocation entry, int page) {
        this.category = category;
        this.entry = entry;
        this.page = page;
    }

    static void encode(PacketSyncEntry msg, PacketBuffer buf){
        buf.writeInt(msg.category);
        buf.writeResourceLocation(msg.entry);
        buf.writeInt(msg.page);
    }

    static PacketSyncEntry decode(PacketBuffer buf){
        PacketSyncEntry msg=new PacketSyncEntry();
        msg.category=buf.readInt();
        msg.entry=buf.readResourceLocation();
        msg.page = buf.readInt();
        return msg;
    }

    public static void handle(final PacketSyncEntry msg, Supplier<NetworkEvent.Context> contextSupplier){
        final NetworkEvent.Context ctx=contextSupplier.get();
        ServerPlayerEntity player = ctx.getSender();
        Validate.notNull(player);
        ctx.enqueueWork(()->{
            ItemStack book = player.getHeldItemOffhand();
            if (book.isEmpty() || !(book.getItem() instanceof IGuideItem))
                book = player.getHeldItemMainhand();

            if (!book.isEmpty() && book.getItem() instanceof IGuideItem) {
                if (msg.category != -1 && !msg.entry.equals(new ResourceLocation("guideapi", "none")) && msg.page != -1) {
                    if (!book.hasTag())
                        book.setTag(new CompoundNBT());

                    book.getTag().putInt(NBTBookTags.CATEGORY_TAG, msg.category);
                    book.getTag().putString(NBTBookTags.ENTRY_TAG, msg.entry.toString());
                    book.getTag().putInt(NBTBookTags.PAGE_TAG, msg.page);
                }
            }
        });
        ctx.setPacketHandled(true);
    }




}
