package de.maxanier.guideapi.api;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * When right-clicking a block with this interface holding the respective guide will open the supplied entry
 * You can implement this in your block. However, this of course creates a hard dependency on GuideAPI.
 */
public interface IGuideLinked {

    /**
     * @param world  - The world where the block is
     * @param pos    - The block's location in the world
     * @param player - The player that triggered the method
     * @param stack  - The ingame book item
     * @return the key of the entry to open or null if no entry should be opened
     */
    @Nullable
    ResourceLocation getLinkedEntry(Level world, BlockPos pos, Player player, ItemStack stack);
}
