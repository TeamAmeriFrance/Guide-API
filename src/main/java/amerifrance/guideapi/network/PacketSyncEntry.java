package amerifrance.guideapi.network;

import amerifrance.guideapi.api.IGuideItem;
import amerifrance.guideapi.api.util.NBTBookTags;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncEntry implements IMessage, IMessageHandler<PacketSyncEntry, IMessage> {

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

    @Override
    public void fromBytes(ByteBuf buf) {
        this.category = buf.readInt();
        this.entry = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
        this.page = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(category);
        ByteBufUtils.writeUTF8String(buf, entry.toString());
        buf.writeInt(page);
    }

    @Override
    public IMessage onMessage(PacketSyncEntry message, MessageContext ctx) {
        ItemStack book = ctx.getServerHandler().player.getHeldItemOffhand();
        if (book.isEmpty() || !(book.getItem() instanceof IGuideItem))
            book = ctx.getServerHandler().player.getHeldItemMainhand();

        if (!book.isEmpty() && book.getItem() instanceof IGuideItem) {
            if (message.category != -1 && !message.entry.equals(new ResourceLocation("guideapi", "none")) && message.page != -1) {
                if (!book.hasTagCompound())
                    book.setTagCompound(new CompoundNBT());

                book.getTagCompound().setInteger(NBTBookTags.CATEGORY_TAG, message.category);
                book.getTagCompound().setString(NBTBookTags.ENTRY_TAG, message.entry.toString());
                book.getTagCompound().setInteger(NBTBookTags.PAGE_TAG, message.page);
            }
        }
        return null;
    }
}
