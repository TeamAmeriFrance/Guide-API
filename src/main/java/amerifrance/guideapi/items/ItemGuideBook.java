package amerifrance.guideapi.items;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.GuideRegistry;
import amerifrance.guideapi.ModInformation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemGuideBook extends Item {

    public IIcon pagesIcon;

    public ItemGuideBook() {
        this.setCreativeTab(GuideAPI.tabGuide);
        this.setUnlocalizedName("GuideBook");
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!GuideRegistry.isEmpty()) {
            player.openGui(GuideAPI.instance, stack.getItemDamage(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return stack;
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

    @Override
    public int getRenderPasses(int metadata) {
        return requiresMultipleRenderPasses() ? 2 : 1;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (pass == 0) return itemIcon;
        else return pagesIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        itemIcon = ir.registerIcon(ModInformation.TEXLOC + "book_cover");
        pagesIcon = ir.registerIcon(ModInformation.TEXLOC + "book_pages");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (!GuideRegistry.isEmpty()) {
            if (pass == 0) return GuideRegistry.getBook(stack.getItemDamage()).bookColor.getRGB();
            else return super.getColorFromItemStack(stack, pass);
        } else {
            return super.getColorFromItemStack(stack, pass);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
        if (!GuideRegistry.isEmpty()) {
            for (int i = 0; i < GuideRegistry.getSize(); i++) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }
}
