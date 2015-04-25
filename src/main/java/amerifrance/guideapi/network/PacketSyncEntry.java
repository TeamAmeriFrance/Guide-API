package amerifrance.guideapi.network;

import amerifrance.guideapi.gui.GuiBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

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
        ItemStack book = ctx.getServerHandler().playerEntity.getHeldItem();
        if (book != null && message.category != -1 && message.entry != -1 && message.page != -1) {
            book.stackTagCompound.setInteger(GuiBase.CATEGORY_TAG, message.category);
            book.stackTagCompound.setInteger(GuiBase.ENTRY_TAG, message.entry);
            book.stackTagCompound.setInteger(GuiBase.PAGE_TAG, message.page);
        }
        return null;
    }
}
