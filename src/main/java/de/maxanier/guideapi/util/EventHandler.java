package de.maxanier.guideapi.util;

import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.GuideConfig;
import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.IGuideItem;
import de.maxanier.guideapi.api.IGuideLinked;
import de.maxanier.guideapi.api.IInfoRenderer;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.Collection;

@Mod.EventBusSubscriber(modid = GuideMod.ID)
public class EventHandler {

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getEntity().level.isClientSide && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            CompoundTag tag = getModTag(player, GuideMod.ID);
            if (GuideConfig.COMMON.canSpawnWithBook.get()) {
                for (Book book : GuideAPI.getBooks().values()) {
                    ForgeConfigSpec.BooleanValue bookSpawnConfig = GuideConfig.COMMON.SPAWN_BOOKS.get(book);
                    if ((bookSpawnConfig == null || bookSpawnConfig.get()) && !tag.getBoolean("hasInitial" + book.getRegistryName().toString())) {
                        ItemHandlerHelper.giveItemToPlayer(player, GuideAPI.getStackFromBook(book));
                        tag.putBoolean("hasInitial" + book.getRegistryName().toString(), true);
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.PreLayer event) {
        if (event.getOverlay() != ForgeIngameGui.CROSSHAIR_ELEMENT)
            return;

        HitResult rayTrace = Minecraft.getInstance().hitResult;
        if (rayTrace == null || rayTrace.getType() != HitResult.Type.BLOCK)
            return;

        Player player = Minecraft.getInstance().player;
        Level world = Minecraft.getInstance().level;
        ItemStack held = ItemStack.EMPTY;
        Book book = null;
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack heldStack = player.getItemInHand(hand);
            if (heldStack.getItem() instanceof IGuideItem) {
                held = heldStack;
                book = ((IGuideItem) heldStack.getItem()).getBook(heldStack);
                break;
            }
        }
        PoseStack stack = event.getMatrixStack();

        if (book == null)
            return;
        BlockPos rayTracePos = ((BlockHitResult) rayTrace).getBlockPos();
        BlockState state = world.getBlockState(rayTracePos);
        @Nullable
        Component linkedEntry = null;
        if (state.getBlock() instanceof IGuideLinked linked) {
            ResourceLocation entryKey = linked.getLinkedEntry(world, rayTracePos, player, held);
            if (entryKey != null) {
                for (CategoryAbstract category : book.getCategoryList()) {
                    if (category.entries.containsKey(entryKey)) {
                        linkedEntry = category.getEntry(entryKey).getName();
                        break;
                    }
                }
            }
        }

        if (linkedEntry != null) {
            Font fontRenderer = Minecraft.getInstance().font;

            int drawX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 10;
            int drawY = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - 8;

            Minecraft.getInstance().getItemRenderer().renderGuiItem(held, drawX, drawY);

            drawY -= 2;
            drawX += 20;
            fontRenderer.drawShadow(stack, linkedEntry instanceof MutableComponent ? ((MutableComponent) linkedEntry).withStyle(ChatFormatting.WHITE) : linkedEntry, drawX, drawY, 0);
            fontRenderer.drawShadow(stack, new TranslatableComponent("guideapi.text.linked.open").withStyle(ChatFormatting.WHITE, ChatFormatting.ITALIC), drawX, drawY + 12, 0);
        }

        if (state.getBlock() instanceof IInfoRenderer.Block) {
            IInfoRenderer infoRenderer = ((IInfoRenderer.Block) state.getBlock()).getInfoRenderer(book, world, rayTracePos, state, rayTrace, player);
            if (book == ((IInfoRenderer.Block) state.getBlock()).getBook() && infoRenderer != null)
                infoRenderer.drawInformation(stack, book, world, rayTracePos, state, rayTrace, player);
        }

        Multimap<Block, IInfoRenderer> bookRenderers = GuideAPI.getInfoRenderers().get(book);
        if (bookRenderers == null)
            return;

        Collection<IInfoRenderer> renderers = bookRenderers.get(state.getBlock());
        for (IInfoRenderer renderer : renderers)
            renderer.drawInformation(stack, book, world, rayTracePos, state, rayTrace, player);
    }

    public static CompoundTag getModTag(Player player, String modName) {
        CompoundTag tag = player.getPersistentData();
        CompoundTag persistTag;

        if (tag.contains(Player.PERSISTED_NBT_TAG))
            persistTag = tag.getCompound(Player.PERSISTED_NBT_TAG);
        else {
            persistTag = new CompoundTag();
            tag.put(Player.PERSISTED_NBT_TAG, persistTag);
        }

        CompoundTag modTag;
        if (persistTag.contains(modName)) {
            modTag = persistTag.getCompound(modName);
        } else {
            modTag = new CompoundTag();
            persistTag.put(modName, modTag);
        }
        return modTag;
    }
}
