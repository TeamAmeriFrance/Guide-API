package de.maxanier.guideapi.util;

import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.GuideConfig;
import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.GuideAPI;
import de.maxanier.guideapi.api.IGuideItem;
import de.maxanier.guideapi.api.IGuideLinked;
import de.maxanier.guideapi.api.IInfoRenderer;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
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
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
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
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            CompoundNBT tag = getModTag(player, GuideMod.ID);
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
        MatrixStack stack = event.getMatrixStack();

        if (book == null)
            return;
        BlockPos rayTracePos = ((BlockRayTraceResult) rayTrace).getPos();
        BlockState state = world.getBlockState(rayTracePos);
        @Nullable
        ITextComponent linkedEntry = null;
        if (state.getBlock() instanceof IGuideLinked) {
            IGuideLinked linked = (IGuideLinked) state.getBlock();
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
            FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

            int drawX = Minecraft.getInstance().getMainWindow().getScaledWidth() / 2 + 10;
            int drawY = Minecraft.getInstance().getMainWindow().getScaledHeight() / 2 - 8;

            Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(held, drawX, drawY);

            drawY -= 2;
            drawX += 20;
            fontRenderer.drawTextWithShadow(stack, linkedEntry instanceof IFormattableTextComponent ? ((IFormattableTextComponent) linkedEntry).mergeStyle(TextFormatting.WHITE) : linkedEntry, drawX, drawY, 0);
            fontRenderer.drawTextWithShadow(stack, new TranslationTextComponent("guideapi.text.linked.open").mergeStyle(TextFormatting.WHITE, TextFormatting.ITALIC), drawX, drawY + 12, 0);
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
