package de.maxanier.guideapi.item;

import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.BookEvent;
import de.maxanier.guideapi.api.IGuideItem;
import de.maxanier.guideapi.api.IGuideLinked;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.TooltipFlag.Default;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemGuideBook extends Item implements IGuideItem {

    @Nonnull
    private final Book book;
    private String translation_key;


    public ItemGuideBook(Book book) {
        super(new Item.Properties().stacksTo(1).tab(book.getCreativeTab()));
        this.book = book;
        setTranslation_key(GuideMod.ID + ".book." + book.getRegistryName().getNamespace() + "." + book.getRegistryName().getPath());
    }

    @Override
    public void appendHoverText(ItemStack stack, Level playerIn, List<Component> tooltip, TooltipFlag advanced) {
        if (book.getAuthor() != null) {
            tooltip.add(book.getAuthor());
            if (advanced == Default.ADVANCED) {
                tooltip.add(Component.literal(book.getRegistryName().toString()));
            }
        }
    }

    @Override
    public Book getBook(ItemStack stack) {
        return book;
    }

    @Nullable
    @Override
    public String getCreatorModId(ItemStack itemStack) {
        return book.getRegistryName().getNamespace();
    }

    @Nonnull
    @Override
    public Component getName(ItemStack stack) {
        return getBook(stack).getItemName() != null ? getBook(stack).getItemName() : super.getName(stack);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack heldStack = player.getItemInHand(hand);

        //Only handle book client side
        if (!world.isClientSide()) return InteractionResultHolder.success(heldStack);


        BookEvent.Open event = new BookEvent.Open(book, heldStack, player);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            player.displayClientMessage(event.getCanceledText(), true);
            return InteractionResultHolder.fail(heldStack);
        }
        GuideMod.PROXY.openGuidebook(player, world, book, heldStack);
        return InteractionResultHolder.success(heldStack);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide || !context.isSecondaryUseActive())
            return InteractionResult.PASS;

        ItemStack stack = context.getItemInHand();
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());

        if (state.getBlock() instanceof IGuideLinked guideLinked) {
            ResourceLocation entryKey = guideLinked.getLinkedEntry(context.getLevel(), context.getClickedPos(), context.getPlayer(), stack);
            if (entryKey == null)
                return InteractionResult.FAIL;

            for (CategoryAbstract category : book.getCategoryList()) {
                if (category.entries.containsKey(entryKey)) {
                    GuideMod.PROXY.openEntry(book, category, category.entries.get(entryKey), context.getPlayer(), stack);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Nonnull
    @Override
    protected String getOrCreateDescriptionId() {
        if (this.translation_key == null) {
            this.translation_key = Util.makeDescriptionId("item", Registry.ITEM.getKey(this));
        }

        return this.translation_key;
    }

    // IGuideItem

    /**
     * Set a custom translation key
     */
    protected void setTranslation_key(String name) {
        this.translation_key = Util.makeDescriptionId("item", new ResourceLocation(GuideMod.ID, name));
    }
}
