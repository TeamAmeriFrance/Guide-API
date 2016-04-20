package amerifrance.guideapi.api.abstraction;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IGuideLinked {

    /**
     * @param world  - The world where the block is
     * @param pos    - The block's location in the world
     * @param player - The player that triggered the method
     * @param stack  - The ingame book item
     *
     * @return the unlocalized name of the entry to open
     */
    String getLinkedEntryUnlocName(World world, BlockPos pos, EntityPlayer player, ItemStack stack);
}
