package amerifrance.guideapi.util;

import amerifrance.guideapi.GuideConfig;
import amerifrance.guideapi.GuideMod;
import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IGuideItem;
import amerifrance.guideapi.api.IGuideLinked;
import amerifrance.guideapi.api.IInfoRenderer;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.util.TextHelper;
import com.google.common.base.Strings;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = GuideMod.ID)
public class EventHandler {

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            CompoundNBT tag = getModTag(player, GuideMod.ID);
            if (GuideConfig.SERVER.canSpawnWithBook.get()) {
                for (Book book : GuideAPI.getBooks().values()) {
                    //if (ConfigHandler.SPAWN_BOOKS.getOrDefault(book, false) && !tag.getBoolean("hasInitial" + book.getTitle())) { TODO
                        ItemHandlerHelper.giveItemToPlayer(player, GuideAPI.getStackFromBook(book));
                        tag.putBoolean("hasInitial" + book.getTitle(), true);
                    //}
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.CROSSHAIRS)
            return;

        RayTraceResult rayTrace = Minecraft.getInstance().objectMouseOver;
        if (rayTrace == null || rayTrace.getType() != RayTraceResult.Type.BLOCK)
            return;

        PlayerEntity player = Minecraft.getInstance().player;
        World world = Minecraft.getInstance().world;
        ItemStack held = ItemStack.EMPTY;
        Book book = null;
        for (Hand hand : Hand.values()) {
            ItemStack heldStack = player.getHeldItem(hand);
            if (heldStack.getItem() instanceof IGuideItem) {
                held = heldStack;
                book = ((IGuideItem) heldStack.getItem()).getBook(heldStack);
                break;
            }
        }

        if (book == null)
            return;
        BlockPos rayTracePos=((BlockRayTraceResult)rayTrace).getPos();
        BlockState state = world.getBlockState(rayTracePos);
        String linkedEntry = null;
        if (state.getBlock() instanceof IGuideLinked) {
            IGuideLinked linked = (IGuideLinked) state.getBlock();
            ResourceLocation entryKey = linked.getLinkedEntry(world, rayTracePos, player, held);
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
            FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

            int drawX = Minecraft.getInstance().mainWindow.getScaledWidth() / 2 + 10;
            int drawY = Minecraft.getInstance().mainWindow.getScaledHeight() / 2 - 8;

            Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(held, drawX, drawY);

            drawY -= 2;
            drawX += 20;
            fontRenderer.drawStringWithShadow(TextFormatting.WHITE + linkedEntry, drawX, drawY, 0);
            fontRenderer.drawStringWithShadow(TextFormatting.WHITE.toString() + TextFormatting.ITALIC.toString() + TextHelper.localize("guideapi.text.linked.open"), drawX, drawY + 12, 0);
        }

        if (state.getBlock() instanceof IInfoRenderer.Block) {
            IInfoRenderer infoRenderer = ((IInfoRenderer.Block) state.getBlock()).getInfoRenderer(book, world, rayTracePos, state, rayTrace, player);
            if (book == ((IInfoRenderer.Block) state.getBlock()).getBook() && infoRenderer != null)
                infoRenderer.drawInformation(book, world, rayTracePos, state, rayTrace, player);
        }

        Multimap<Class<? extends Block>, IInfoRenderer> bookRenderers = GuideAPI.getInfoRenderers().get(book);
        if (bookRenderers == null)
            return;

        Collection<IInfoRenderer> renderers = bookRenderers.get(state.getBlock().getClass());
        for (IInfoRenderer renderer : renderers)
            renderer.drawInformation(book, world, rayTracePos, state, rayTrace, player);
    }

    public static CompoundNBT getModTag(PlayerEntity player, String modName) {
        CompoundNBT tag = player.getPersistentData();
        CompoundNBT persistTag;

        if (tag.contains(PlayerEntity.PERSISTED_NBT_TAG))
            persistTag = tag.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
        else {
            persistTag = new CompoundNBT();
            tag.put(PlayerEntity.PERSISTED_NBT_TAG, persistTag);
        }

        CompoundNBT modTag;
        if (persistTag.contains(modName)) {
            modTag = persistTag.getCompound(modName);
        } else {
            modTag = new CompoundNBT();
            persistTag.put(modName, modTag);
        }
        return modTag;
    }
}
