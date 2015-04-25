package amerifrance.guideapi.network;

import amerifrance.guideapi.gui.GuiBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

public class PacketSyncCategory implements IMessage, IMessageHandler<PacketSyncCategory, IMessage> {

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

    @Override
    public void fromBytes(ByteBuf buf) {
        this.category = buf.readInt();
        this.page = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(category);
        buf.writeInt(page);
    }

    @Override
    public IMessage onMessage(PacketSyncCategory message, MessageContext ctx) {
        ItemStack book = ctx.getServerHandler().playerEntity.getHeldItem();
        if (book != null && message.category != -1 && message.page != -1) {
            book.stackTagCompound.setInteger(GuiBase.CATEGORY_TAG, message.category);
            book.stackTagCompound.setInteger(GuiBase.ENTRY_PAGE_TAG, message.page);
            book.stackTagCompound.removeTag(GuiBase.ENTRY_TAG);
        }
        return null;
    }
}
