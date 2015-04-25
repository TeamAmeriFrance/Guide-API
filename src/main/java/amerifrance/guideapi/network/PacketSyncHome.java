package amerifrance.guideapi.network;

import amerifrance.guideapi.gui.GuiBase;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

public class PacketSyncHome implements IMessage, IMessageHandler<PacketSyncHome, IMessage> {

    public ItemStack stack;
    public int page;

    public PacketSyncHome() {
        this.stack = null;
        this.page = -1;
    }

    public PacketSyncHome(ItemStack stack, int page) {
        this.stack = stack;
        this.page = page;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.stack = ByteBufUtils.readItemStack(buf);
        this.page = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeInt(page);
    }

    @Override
    public IMessage onMessage(PacketSyncHome message, MessageContext ctx) {
        ItemStack book = message.stack;
        if (stack != null && message.page != -1) {
            book.stackTagCompound.setInteger(GuiBase.CATEGORY_PAGE_TAG, message.page);
            book.stackTagCompound.removeTag(GuiBase.CATEGORY_TAG);
            book.stackTagCompound.removeTag(GuiBase.ENTRY_TAG);
        }
        return null;
    }
}
