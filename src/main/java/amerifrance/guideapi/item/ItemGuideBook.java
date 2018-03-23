package amerifrance.guideapi.item;

import amerifrance.guideapi.GuideMod;
import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IGuideItem;
import amerifrance.guideapi.api.IGuideLinked;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.util.TextHelper;
import com.google.common.base.Strings;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemGuideBook extends Item implements IGuideItem {

    @Nonnull
    private final Book book;

    public ItemGuideBook(@Nonnull Book book) {
        this.book = book;

        setMaxStackSize(1);
        setCreativeTab(book.getCreativeTab());
        setUnlocalizedName(GuideMod.ID + ".book." + book.getRegistryName().getResourceDomain() + "." + book.getRegistryName().getResourcePath());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        player.openGui(GuideMod.INSTANCE, GuideAPI.getIndexedBooks().indexOf(book), world, hand.ordinal(), 0, 0);
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote || !player.isSneaking())
            return EnumActionResult.PASS;

        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof IGuideLinked) {
            IGuideLinked guideLinked = (IGuideLinked) state.getBlock();
            ResourceLocation entryKey = guideLinked.getLinkedEntry(world, pos, player, stack);
            if (entryKey == null)
                return EnumActionResult.FAIL;

            for (CategoryAbstract category : book.getCategoryList()) {
                if (category.entries.containsKey(entryKey)) {
                    GuideMod.PROXY.openEntry(book, category, category.entries.get(entryKey), player, stack);
                    return EnumActionResult.SUCCESS;
                }
            }
        }

        return EnumActionResult.PASS;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return !Strings.isNullOrEmpty(book.getItemName()) ? I18n.translateToLocal(getBook(stack).getItemName()) : super.getItemStackDisplayName(stack);
    }

    @Override
    public void addInformation(ItemStack stack, World playerIn, List<String> tooltip, ITooltipFlag advanced) {
        if (!Strings.isNullOrEmpty(book.getAuthor()))
            tooltip.add(TextHelper.localizeEffect(book.getAuthor()));
        if (!Strings.isNullOrEmpty(book.getAuthor()) && (advanced == TooltipFlags.ADVANCED))
            tooltip.add(book.getRegistryName().toString());
    }

    @Nullable
//    @Override TODO - Soft override because this hasn't been merged into Forge yet. https://github.com/MinecraftForge/MinecraftForge/pull/4330
    public String getCreatorModId(ItemStack stack) {
        return book.getRegistryName().getResourceDomain();
    }

    // IGuideItem

    @Override
    public Book getBook(ItemStack stack) {
        return book;
    }
}
