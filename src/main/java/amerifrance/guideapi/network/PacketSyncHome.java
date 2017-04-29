package amerifrance.guideapi.network;

import amerifrance.guideapi.api.IGuideItem;
import amerifrance.guideapi.api.util.NBTBookTags;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncHome implements IMessage, IMessageHandler<PacketSyncHome, IMessage> {

    public int page;

    public PacketSyncHome() {
        this.page = -1;
    }

    public PacketSyncHome(int page) {
        this.page = page;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.page = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(page);
    }

    @Override
    public IMessage onMessage(PacketSyncHome message, MessageContext ctx) {
        ItemStack book = ctx.getServerHandler().player.getHeldItemOffhand();
        if (book.isEmpty() || !(book.getItem() instanceof IGuideItem))
            book = ctx.getServerHandler().player.getHeldItemMainhand();

        if (!book.isEmpty() && book.getItem() instanceof IGuideItem && message.page != -1) {
            if (!book.hasTagCompound())
                book.setTagCompound(new NBTTagCompound());
            book.getTagCompound().setInteger(NBTBookTags.CATEGORY_PAGE_TAG, message.page);
            book.getTagCompound().removeTag(NBTBookTags.CATEGORY_TAG);
            book.getTagCompound().removeTag(NBTBookTags.ENTRY_TAG);
        }
        return null;
    }
}
