package amerifrance.guideapi.item;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IGuideLinked;
import amerifrance.guideapi.api.base.Book;
import com.google.common.base.Strings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemGuideBook extends Item {

    public ItemGuideBook() {
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName(ModInformation.ID + ".book");
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() > stack.getItemDamage()) {
            if (!stack.hasTagCompound())
                stack.setTagCompound(new NBTTagCompound());
            player.openGui(GuideAPI.instance, stack.getItemDamage(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() > stack.getItemDamage() && world.getBlockState(pos) instanceof IGuideLinked) {
            IGuideLinked guideLinked = (IGuideLinked) world.getBlockState(pos);
            Book book = GuideRegistry.getBook(stack.getItemDamage());
            String entryName = guideLinked.getLinkedEntryUnlocName(world, pos, player, stack);
            for (CategoryAbstract category : book.getCategoryList()) {
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
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem() || oldStack.getItemDamage() != newStack.getItemDamage();
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
        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() > stack.getItemDamage() && !GuideRegistry.getBook(stack.getItemDamage()).isCustomModel())
            return pass == 0 ? GuideRegistry.getBook(stack.getItemDamage()).getBookColor().getRGB() : super.getColorFromItemStack(stack, pass);
        else
            return super.getColorFromItemStack(stack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
        if (!GuideRegistry.isEmpty())
            for (Book book : GuideRegistry.getBookList())
                list.add(new ItemStack(this, 1, GuideRegistry.getIndexOf(book)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean bool) {

        if (!GuideRegistry.isEmpty() && GuideRegistry.getSize() < stack.getItemDamage())
            list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("text.book.warning"));

        if (!GuideRegistry.isEmpty() && !(GuideRegistry.getSize() < stack.getItemDamage()) && !Strings.isNullOrEmpty(GuideRegistry.getBook(stack.getItemDamage()).getAuthor()))
            list.add(StatCollector.translateToLocal(GuideRegistry.getBook(stack.getItemDamage()).getAuthor()));

        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());
    }
}
