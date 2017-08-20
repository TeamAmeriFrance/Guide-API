package amerifrance.guideapi.util;

import amerifrance.guideapi.ConfigHandler;
import amerifrance.guideapi.GuideMod;
import amerifrance.guideapi.api.*;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.page.PageJsonRecipe;
import com.google.common.base.Strings;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Collection;

public class EventHandler {

    @SubscribeEvent
    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            NBTTagCompound tag = getModTag(player, GuideMod.ID);
            if (ConfigHandler.canSpawnWithBooks) {
                for (Book book : GuideAPI.getBooks().values()) {
                    if (book.isSpawnWithBook() && !tag.getBoolean("hasInitial" + book.getTitle())) {
                        ItemHandlerHelper.giveItemToPlayer(player, GuideAPI.getStackFromBook(book));
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
        if (rayTrace == null || rayTrace.typeOfHit != RayTraceResult.Type.BLOCK)
            return;

        EntityPlayer player = Minecraft.getMinecraft().player;
        World world = Minecraft.getMinecraft().world;
        ItemStack held = ItemStack.EMPTY;
        Book book = null;
        for (EnumHand hand : EnumHand.values()) {
            ItemStack heldStack = player.getHeldItem(hand);
            if (heldStack.getItem() instanceof IGuideItem) {
                held = heldStack;
                book = ((IGuideItem) heldStack.getItem()).getBook(heldStack);
                break;
            }
        }

        if (book == null)
            return;

        IBlockState state = world.getBlockState(rayTrace.getBlockPos());
        String linkedEntry = null;
        if (state.getBlock() instanceof IGuideLinked) {
            IGuideLinked linked = (IGuideLinked) state.getBlock();
            ResourceLocation entryKey = linked.getLinkedEntry(world, rayTrace.getBlockPos(), player, held);
            if (entryKey != null) {
                for (CategoryAbstract category : book.getCategoryList()) {
                    if (category.entries.containsKey(entryKey)) {
                        linkedEntry = category.getEntry(entryKey).getLocalizedName();
                        break;
                    }
                }
            }
        }

        if (!Strings.isNullOrEmpty(linkedEntry)) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            int drawX = scaledResolution.getScaledWidth() / 2 + 10;
            int drawY = scaledResolution.getScaledHeight() / 2 - 8;

            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(held, drawX, drawY);

            drawY -= 2;
            drawX += 20;
            fontRenderer.drawStringWithShadow(TextFormatting.WHITE + linkedEntry, drawX, drawY, 0);
            fontRenderer.drawStringWithShadow(TextFormatting.WHITE.toString() + TextFormatting.ITALIC.toString() + TextHelper.localize("text.linked.open"), drawX, drawY + 12, 0);
        }

        if (state.getBlock() instanceof IInfoRenderer.Block) {
            IInfoRenderer infoRenderer = ((IInfoRenderer.Block) state.getBlock()).getInfoRenderer(book, world, rayTrace.getBlockPos(), state, rayTrace, player);
            if (book == ((IInfoRenderer.Block) state.getBlock()).getBook() && infoRenderer != null)
                infoRenderer.drawInformation(book, world, rayTrace.getBlockPos(), state, rayTrace, player);
        }

        Multimap<Class<? extends Block>, IInfoRenderer> bookRenderers = GuideAPI.getInfoRenderers().get(book);
        if (bookRenderers == null)
            return;

        Collection<IInfoRenderer> renderers = bookRenderers.get(state.getBlock().getClass());
        for (IInfoRenderer renderer : renderers)
            renderer.drawInformation(book, world, rayTrace.getBlockPos(), state, rayTrace, player);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)//This should be called after all recipes are registered
    public void recipes(RegistryEvent.Register<IRecipe> event) {
        for(Book book:GuideAPI.getBooks().values())
            for(CategoryAbstract cat : book.getCategoryList())
                for(EntryAbstract entry:cat.entries.values())
                    for(IPage page : entry.pageList)
                        if(page instanceof PageJsonRecipe)
                            ((PageJsonRecipe) page).init();
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
