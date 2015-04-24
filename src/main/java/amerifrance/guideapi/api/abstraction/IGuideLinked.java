package amerifrance.guideapi.api.abstraction;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IGuideLinked {

    /**
     * @param world  - The world where the block is
     * @param x      - The block's position on the x-axis
     * @param y      - The block's position on the y-axis
     * @param z      -The block's position on the z-axis
     * @param player - The player that triggered the method
     * @param stack  - The ingame book item
     * @return the unlocalized name of the entry to open
     */
    public String getLinkedEntryUnlocName(World world, int x, int y, int z, EntityPlayer player, ItemStack stack);
}
