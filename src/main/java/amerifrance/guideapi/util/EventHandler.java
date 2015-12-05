package amerifrance.guideapi.util;

import amerifrance.guideapi.ConfigHandler;
import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.GuideAPIItems;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.NBTBookTags;
import amerifrance.guideapi.items.ItemLostPage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

    @SubscribeEvent
    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            NBTTagCompound tag = getModTag(player, ModInformation.ID);
            if (ConfigHandler.canSpawnWithBooks) {
                for (Book book : GuideRegistry.getBookList()) {
                    if (book.isSpawnWithBook() && !tag.getBoolean("hasInitial" + book.getUnlocBookTitle())) {
                        player.inventory.addItemStackToInventory(GuideRegistry.getItemStackForBook(book));
                        player.inventoryContainer.detectAndSendChanges();
                        tag.setBoolean("hasInitial" + book.getUnlocBookTitle(), true);
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

    @SubscribeEvent
    public void onAnvil(AnvilUpdateEvent event) {
        ItemStack left = event.left;
        ItemStack right = event.right;

        if (left == null || right == null || !right.hasTagCompound())
            return;

        if (left.getItem() == GuideAPIItems.guideBook && right.getItem() == GuideAPIItems.lostPage) {
            if (ItemLostPage.getPageCharacteristics(right) != null) {
                if (left.getItemDamage() == GuideRegistry.getIndexOf((Book) ItemLostPage.getPageCharacteristics(right)[0])) {
                    ItemStack output = left.copy();

                    if (!output.hasTagCompound())
                        output.setTagCompound(new NBTTagCompound());

                    output.getTagCompound().setBoolean(right.getTagCompound().getString(NBTBookTags.KEY_TAG), true);
                    event.output = output;
                    event.cost = 5;
                }
            }
        } else if (left.getItem() == GuideAPIItems.lostPage && right.getItem() == Items.paper) {
            ItemStack output = left.copy();
            output.stackSize = 2;
            event.output = output;
            event.cost = 5;
        }
    }
}
