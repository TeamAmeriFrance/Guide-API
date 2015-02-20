package amerifrance.guideapi.items;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.GuideRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemGuideBook extends Item {

    public ItemGuideBook() {
        this.setCreativeTab(GuideAPI.tabGuide);
        this.setUnlocalizedName("GuideBook");
        this.setHasSubtypes(true);
        this.setTextureName("book_normal");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (!GuideRegistry.isEmpty()) {
            String name = GuideRegistry.getBook(stack.getItemDamage()).unlocBookTitle;
            return getUnlocalizedName() + "." + name;
        } else {
            return super.getUnlocalizedName(stack);
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
        if (!GuideRegistry.isEmpty()) {
            for (int i = 0; i < GuideRegistry.getSize(); i++) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (!GuideRegistry.isEmpty()) return GuideRegistry.getBook(stack.getItemDamage()).bookColor.getRGB();
        else return super.getColorFromItemStack(stack, pass);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!GuideRegistry.isEmpty()) {
            player.openGui(GuideAPI.instance, stack.getItemDamage(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return stack;
    }
}
