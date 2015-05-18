package amerifrance.guideapi.items;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IGuideLinked;
import amerifrance.guideapi.api.base.Book;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemGuideBook extends Item {

    public ItemGuideBook() {
        setCreativeTab(GuideAPI.tabGuide);
        setUnlocalizedName("GuideBook");
        setMaxDamage(0);
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() > stack.getItemDamage()) {
            if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            player.openGui(GuideAPI.instance, stack.getItemDamage(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() > stack.getItemDamage() && world.getBlockState(new BlockPos(pos)) instanceof IGuideLinked) {
            IGuideLinked guideLinked = (IGuideLinked) world.getBlockState(pos);
            Book book = GuideRegistry.getBook(stack.getItemDamage());
            String entryName = guideLinked.getLinkedEntryUnlocName(world, pos, player, stack);
            for (CategoryAbstract category : book.categoryList) {
                for (EntryAbstract entry : category.entryList) {
                    if (entry.unlocEntryName.equals(entryName)) {
                        GuideAPI.proxy.openEntry(book, category, entry, player, stack);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() > stack.getItemDamage()) {
            String name = String.valueOf(stack.getItemDamage());
            return getUnlocalizedName() + "." + name;
        } else {
            return super.getUnlocalizedName(stack);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() > stack.getItemDamage())
            return GuideRegistry.getBook(stack.getItemDamage()).getLocalizedDisplayName();
        else
            return super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() > stack.getItemDamage()) {
            if (pass == 0)
                return GuideRegistry.getBook(stack.getItemDamage()).bookColor.getRGB();
            else
                return super.getColorFromItemStack(stack, pass);
        } else {
            return super.getColorFromItemStack(stack, pass);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
        if (!GuideRegistry.isEmpty())
            for (int i = 0; i < GuideRegistry.getSize(); i++)
                list.add(new ItemStack(this, 1, i));
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() < stack.getItemDamage())
            list.add(StatCollector.translateToLocal("text.book.warning"));
    }
}
