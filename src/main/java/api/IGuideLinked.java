package api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IGuideLinked {

    /**
     * @param world  - The world where the block is
     * @param pos    - The block's location in the world
     * @param player - The player that triggered the method
     * @param stack  - The ingame book item
     * @return the key of the entry to open or null if no entry should be opened
     */
    @Nullable
    ResourceLocation getLinkedEntry(World world, BlockPos pos, PlayerEntity player, ItemStack stack);
}
