package amerifrance.guideapi.util;

import amerifrance.guideapi.ConfigHandler;
import amerifrance.guideapi.GuideMod;
import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IGuideItem;
import amerifrance.guideapi.api.IInfoRenderer;
import amerifrance.guideapi.api.impl.Book;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;

public class EventHandler {

    @SubscribeEvent
    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            NBTTagCompound tag = getModTag(player, GuideMod.ID);
            if (ConfigHandler.canSpawnWithBooks) {
                for (Book book : GuideAPI.BOOKS) {
                    if (book.isSpawnWithBook() && !tag.getBoolean("hasInitial" + book.getTitle())) {
                        player.inventory.addItemStackToInventory(GuideAPI.getStackFromBook(book));
                        player.inventoryContainer.detectAndSendChanges();
                        tag.setBoolean("hasInitial" + book.getTitle(), true);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.CROSSHAIRS)
            return;

        RayTraceResult rayTrace = Minecraft.getMinecraft().objectMouseOver;
        if (rayTrace.typeOfHit != RayTraceResult.Type.BLOCK)
            return;

        EntityPlayer player = Minecraft.getMinecraft().player;
        World world = Minecraft.getMinecraft().world;
        Book book = null;
        for (EnumHand hand : EnumHand.values()) {
            ItemStack heldStack = player.getHeldItem(hand);
            if (heldStack.getItem() instanceof IGuideItem) {
                book = ((IGuideItem) heldStack.getItem()).getBook(heldStack);
                break;
            }
        }

        if (book == null)
            return;

        IBlockState state = world.getBlockState(rayTrace.getBlockPos());
        if (state.getBlock() instanceof IInfoRenderer.Block) {
            IInfoRenderer infoRenderer = ((IInfoRenderer.Block) state.getBlock()).getInfoRenderer(book, world, rayTrace.getBlockPos(), state, rayTrace, player);
            if (book == ((IInfoRenderer.Block) state.getBlock()).getBook() && infoRenderer != null)
                infoRenderer.drawInformation(book, world, rayTrace.getBlockPos(), state, rayTrace, player);
        }

        Multimap<Class<? extends Block>, IInfoRenderer> bookRenderers = GuideAPI.INFO_RENDERERS.get(book);
        if (bookRenderers == null)
            return;

        Collection<IInfoRenderer> renderers = bookRenderers.get(state.getBlock().getClass());
        for (IInfoRenderer renderer : renderers)
            renderer.drawInformation(book, world, rayTrace.getBlockPos(), state, rayTrace, player);
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
