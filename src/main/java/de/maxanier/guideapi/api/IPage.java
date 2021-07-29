package de.maxanier.guideapi.api;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.EntryScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IPage {

    @OnlyIn(Dist.CLIENT)
    void draw(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj);

    @OnlyIn(Dist.CLIENT)
    void drawExtras(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj);

    boolean canSee(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack, EntryScreen guiEntry);

    @OnlyIn(Dist.CLIENT)
    void onLeftClicked(Book book, CategoryAbstract category, EntryAbstract entry, double mouseX, double mouseY, Player player, EntryScreen guiEntry);

    @OnlyIn(Dist.CLIENT)
    void onRightClicked(Book book, CategoryAbstract category, EntryAbstract entry, double mouseX, double mouseY, Player player, EntryScreen guiEntry);

    @OnlyIn(Dist.CLIENT)
    void onInit(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack, EntryScreen guiEntry);

    @OnlyIn(Dist.CLIENT)
    default void onClose() {

    }
}
