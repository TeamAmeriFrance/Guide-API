package de.maxanier.guideapi.item;

import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.BookEvent;
import de.maxanier.guideapi.api.IGuideItem;
import de.maxanier.guideapi.api.IGuideLinked;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemGuideBook extends Item implements IGuideItem {

    @Nonnull
    private final Book book;
    private String translation_key;


    public ItemGuideBook(Book book) {
        super(new Item.Properties().maxStackSize(1).group(book.getCreativeTab()));
        this.book = book;
        setTranslation_key(GuideMod.ID + ".book." + book.getRegistryName().getNamespace() + "." + book.getRegistryName().getPath());
    }

    @Nullable
    @Override
    public String getCreatorModId(ItemStack itemStack) {
        return book.getRegistryName().getNamespace();
    }

    @Override
    protected String getDefaultTranslationKey() {
        if (this.translation_key == null) {
            this.translation_key = Util.makeTranslationKey("item", Registry.ITEM.getKey(this));
        }

        return this.translation_key;
    }

    /**
     * Set a custom translation key
     */
    protected void setTranslation_key(String name) {
        this.translation_key = Util.makeTranslationKey("item", new ResourceLocation(GuideMod.ID, name));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        ItemStack heldStack = player.getHeldItem(hand);

        //Only handle book client side
        if (!world.isRemote()) return ActionResult.resultSuccess(heldStack);


        BookEvent.Open event = new BookEvent.Open(book, heldStack, player);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            player.sendStatusMessage(event.getCanceledText(), true);
            return ActionResult.resultFail( heldStack);
        }
        GuideMod.PROXY.openGuidebook(player, world, book, heldStack);
        return ActionResult.resultSuccess( heldStack);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote || !context.hasSecondaryUseForPlayer())
            return ActionResultType.PASS;

        ItemStack stack = context.getItem();
        BlockState state = context.getWorld().getBlockState(context.getPos());

        if (state.getBlock() instanceof IGuideLinked) {
            IGuideLinked guideLinked = (IGuideLinked) state.getBlock();
            ResourceLocation entryKey = guideLinked.getLinkedEntry(context.getWorld(), context.getPos(), context.getPlayer(), stack);
            if (entryKey == null)
                return ActionResultType.FAIL;

            for (CategoryAbstract category : book.getCategoryList()) {
                if (category.entries.containsKey(entryKey)) {
                    GuideMod.PROXY.openEntry(book, category, category.entries.get(entryKey), context.getPlayer(), stack);
                    return ActionResultType.SUCCESS;
                }
            }
        }

        return ActionResultType.PASS;
    }

    @Override
    public void addInformation(ItemStack stack, World playerIn, List<ITextComponent> tooltip, ITooltipFlag advanced) {
        if (book.getAuthor() != null) {
            tooltip.add(book.getAuthor());
            if (advanced == TooltipFlags.ADVANCED) {
                tooltip.add(new StringTextComponent(book.getRegistryName().toString()));
            }
        }
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return getBook(stack).getItemName() != null ? getBook(stack).getItemName() : super.getDisplayName(stack);
    }

    // IGuideItem

    @Override
    public Book getBook(ItemStack stack) {
        return book;
    }
}
