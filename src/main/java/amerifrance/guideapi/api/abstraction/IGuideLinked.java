package amerifrance.guideapi.api.abstraction;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IGuideLinked {
    public String getLinkedEntryUnlocName(World world, int x, int y, int z, EntityPlayer player, ItemStack stack);
}
