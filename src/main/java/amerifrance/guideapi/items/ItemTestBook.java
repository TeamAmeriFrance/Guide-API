package amerifrance.guideapi.items;

import amerifrance.guideapi.GuideAPI;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTestBook extends Item {

    public ItemTestBook(){
        this.setCreativeTab(CreativeTabs.tabFood);
        this.setUnlocalizedName("TestBook");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(GuideAPI.instance, 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }
}
