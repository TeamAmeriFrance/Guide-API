package amerifrance.guideapi.network;

import amerifrance.guideapi.api.util.NBTBookTags;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncEntry implements IMessage, IMessageHandler<PacketSyncEntry, IMessage> {

    public int category;
    public int entry;
    public int page;

    public PacketSyncEntry() {
        this.category = -1;
        this.entry = -1;
        this.page = -1;
    }

    public PacketSyncEntry(int category, int entry, int page) {
        this.category = category;
        this.entry = entry;
        this.page = page;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.category = buf.readInt();
        this.entry = buf.readInt();
        this.page = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(category);
        buf.writeInt(entry);
        buf.writeInt(page);
    }

    @Override
    public IMessage onMessage(PacketSyncEntry message, MessageContext ctx) {
        ItemStack book = ctx.getServerHandler().playerEntity.getActiveItemStack();
        if (book != null && message.category != -1 && message.entry != -1 && message.page != -1) {
            book.getTagCompound().setInteger(NBTBookTags.CATEGORY_TAG, message.category);
            book.getTagCompound().setInteger(NBTBookTags.ENTRY_TAG, message.entry);
            book.getTagCompound().setInteger(NBTBookTags.PAGE_TAG, message.page);
        }
        return null;
    }
}
