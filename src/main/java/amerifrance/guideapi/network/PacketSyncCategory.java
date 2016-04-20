package amerifrance.guideapi.network;

import amerifrance.guideapi.api.util.NBTBookTags;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
        ItemStack book = ctx.getServerHandler().playerEntity.getActiveItemStack();
        if (book != null && message.category != -1 && message.page != -1) {
            book.getTagCompound().setInteger(NBTBookTags.CATEGORY_TAG, message.category);
            book.getTagCompound().setInteger(NBTBookTags.ENTRY_PAGE_TAG, message.page);
            book.getTagCompound().removeTag(NBTBookTags.ENTRY_TAG);
        }
        return null;
    }
}
