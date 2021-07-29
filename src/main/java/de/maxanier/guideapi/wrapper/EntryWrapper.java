package de.maxanier.guideapi.wrapper;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.CategoryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EntryWrapper extends AbstractWrapper {

    public Book book;
    public CategoryAbstract category;
    public EntryAbstract entry;
    public int x, y, width, height;
    public Player player;
    public Font renderer;
    public CategoryScreen categoryGui;
    public ItemStack bookStack;

    public EntryWrapper(CategoryScreen categoryGui, Book book, CategoryAbstract category, EntryAbstract entry, int x, int y, int width, int height, Player player, Font renderer, ItemStack bookStack) {
        this.book = book;
        this.category = category;
        this.entry = entry;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.player = player;
        this.renderer = renderer;
        this.categoryGui = categoryGui;
        this.bookStack = bookStack;
    }

    @Override
    public void onHoverOver(int mouseX, int mouseY) {
    }

    @Override
    public boolean canPlayerSee() {
        return entry.canSee(player, bookStack);
    }

    @Override
    public void draw(PoseStack stack, int mouseX, int mouseY, BaseScreen gui) {
        entry.draw(stack, book, category, x, y, width, height, mouseX, mouseY, gui, Minecraft.getInstance().font);
    }

    @Override
    public void drawExtras(PoseStack stack, int mouseX, int mouseY, BaseScreen gui) {
        entry.drawExtras(stack, book, category, x, y, width, height, mouseX, mouseY, gui, Minecraft.getInstance().font);
    }

    @Override
    public boolean isMouseOnWrapper(double mouseX, double mouseY) {
        return GuiHelper.isMouseBetween(mouseX, mouseY, x, y, width, height);
    }
}
