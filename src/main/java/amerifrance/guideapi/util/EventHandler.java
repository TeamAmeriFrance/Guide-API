package amerifrance.guideapi.util;

import amerifrance.guideapi.ConfigHandler;
import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.base.Book;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EventHandler {

    @SubscribeEvent
    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            NBTTagCompound tag = getModTag(player, ModInformation.ID);
            if (ConfigHandler.canSpawnWithBooks) {
                for (Book book : GuideRegistry.getBookList()) {
                    if (book.spawnWithBook && !tag.getBoolean("hasInitial" + book.unlocBookTitle)) {
                        player.inventory.addItemStackToInventory(GuideRegistry.getItemStackForBook(book));
                        player.inventoryContainer.detectAndSendChanges();
                        tag.setBoolean("hasInitial" + book.unlocBookTitle, true);
                    }
                }
            }
        }
    }

    public NBTTagCompound getModTag(EntityPlayer player, String modName) {
        NBTTagCompound tag = player.getEntityData();
        NBTTagCompound persistTag;
        if (tag.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
            persistTag = tag.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        else {
            persistTag = new NBTTagCompound();
            tag.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistTag);
        }
        NBTTagCompound modTag;
        if (persistTag.hasKey(modName)) {
            modTag = persistTag.getCompoundTag(modName);
        } else {
            modTag = new NBTTagCompound();
            persistTag.setTag(modName, modTag);
        }
        return modTag;
    }
}
