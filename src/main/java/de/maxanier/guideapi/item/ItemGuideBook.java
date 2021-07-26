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
        super(new Item.Properties().stacksTo(1).tab(book.getCreativeTab()));
        this.book = book;
        setTranslation_key(GuideMod.ID + ".book." + book.getRegistryName().getNamespace() + "." + book.getRegistryName().getPath());
    }

    @Nullable
    @Override
    public String getCreatorModId(ItemStack itemStack) {
        return book.getRegistryName().getNamespace();
    }

    @Override
    public void appendHoverText(ItemStack stack, World playerIn, List<ITextComponent> tooltip, ITooltipFlag advanced) {
        if (book.getAuthor() != null) {
            tooltip.add(book.getAuthor());
            if (advanced == TooltipFlags.ADVANCED) {
                tooltip.add(new StringTextComponent(book.getRegistryName().toString()));
            }
        }
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        return getBook(stack).getItemName() != null ? getBook(stack).getItemName() : super.getName(stack);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack heldStack = player.getItemInHand(hand);

        //Only handle book client side
        if (!world.isClientSide()) return ActionResult.success(heldStack);


        BookEvent.Open event = new BookEvent.Open(book, heldStack, player);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            player.displayClientMessage(event.getCanceledText(), true);
            return ActionResult.fail(heldStack);
        }
        GuideMod.PROXY.openGuidebook(player, world, book, heldStack);
        return ActionResult.success(heldStack);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (!context.getLevel().isClientSide || !context.isSecondaryUseActive())
            return ActionResultType.PASS;

        ItemStack stack = context.getItemInHand();
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());

        if (state.getBlock() instanceof IGuideLinked) {
            IGuideLinked guideLinked = (IGuideLinked) state.getBlock();
            ResourceLocation entryKey = guideLinked.getLinkedEntry(context.getLevel(), context.getClickedPos(), context.getPlayer(), stack);
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
    protected String getOrCreateDescriptionId() {
        if (this.translation_key == null) {
            this.translation_key = Util.makeDescriptionId("item", Registry.ITEM.getKey(this));
        }

        return this.translation_key;
    }

    /**
     * Set a custom translation key
     */
    protected void setTranslation_key(String name) {
        this.translation_key = Util.makeDescriptionId("item", new ResourceLocation(GuideMod.ID, name));
    }

    // IGuideItem

    @Override
    public Book getBook(ItemStack stack) {
        return book;
    }
}
