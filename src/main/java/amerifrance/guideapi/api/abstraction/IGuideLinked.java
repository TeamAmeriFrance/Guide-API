package amerifrance.guideapi.api.abstraction;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface IGuideLinked {

    /**
     * @param world  - The world where the block is
     * @param pos    - This position in the world the block is at
     * @param player - The player that triggered the method
     * @param stack  - The ingame book item
     * @return the unlocalized name of the entry to open
     */
    public String getLinkedEntryUnlocName(World world, BlockPos pos, EntityPlayer player, ItemStack stack);
}
