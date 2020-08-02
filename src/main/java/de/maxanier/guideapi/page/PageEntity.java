package de.maxanier.guideapi.page;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.Page;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.EntryScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Render an entity in the middle of the page.
 * The entity is created on GUI init with the local MC world and removed (garbage collector) when closing the screen.
 */
public class PageEntity extends Page {

    @Nullable
    private final ITextComponent title;
    private final Function<World, ? extends LivingEntity> supplier;
    @Nullable
    private LivingEntity e;

    public PageEntity(EntityType<? extends LivingEntity> entityType) {
        this(entityType::create, entityType.getName());
    }

    /**
     * @param supplier Supply a (new) entity instance
     * @param title    Title to render below the entity. If null, the name of the entity will be rendered
     */
    public PageEntity(Function<World, ? extends LivingEntity> supplier, @Nullable ITextComponent title) {
        this.supplier = supplier;
        this.title = title;
    }

    public PageEntity(Function<World, ? extends LivingEntity> supplier) {
        this(supplier, null);
    }

    @Override
    public void draw(MatrixStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        if (e != null)
            InventoryScreen.drawEntityOnScreen(guiLeft + 120, guiTop + 130, 50, (float) (guiLeft + 120) - mouseX, (float) (guiTop + 130) - mouseY, this.e);
    }

    @Override
    public void drawExtras(MatrixStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {

        if (e != null)
            guiBase.drawCenteredStringWithoutShadow(stack, fontRendererObj, (title != null ? title : e.getName()), guiLeft + guiBase.xSize / 2, guiTop + 140, 0x050505);
    }

    @Override
    public void onClose() {
        this.e = null;
    }

    @Override
    public void onInit(Book book, CategoryAbstract category, EntryAbstract entry, PlayerEntity player, ItemStack bookStack, EntryScreen guiEntry) {
        if (guiEntry.getMinecraft().world != null) this.e = supplier.apply(guiEntry.getMinecraft().world);
    }
}