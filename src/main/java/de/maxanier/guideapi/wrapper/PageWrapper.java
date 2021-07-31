package de.maxanier.guideapi.wrapper;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PageWrapper extends AbstractWrapper {

    public EntryScreen guiEntry;
    public Book book;
    public CategoryAbstract category;
    public EntryAbstract entry;
    public IPage page;
    public int guiLeft, guiTop;
    public Player player;
    public Font renderer;
    public ItemStack bookStack;

    public PageWrapper(EntryScreen guiEntry, Book book, CategoryAbstract category, EntryAbstract entry, IPage page, int guiLeft, int guiTop, Player player, Font renderer, ItemStack bookStack) {
        this.guiEntry = guiEntry;
        this.book = book;
        this.category = category;
        this.entry = entry;
        this.page = page;
        this.guiLeft = guiLeft;
        this.guiTop = guiTop;
        this.player = player;
        this.renderer = renderer;
        this.bookStack = bookStack;
    }

    @Override
    public boolean canPlayerSee() {
        return page.canSee(book, category, entry, player, bookStack, guiEntry);
    }

    @Override
    public void draw(PoseStack stack, int mouseX, int mouseY, BaseScreen gui) {
        page.draw(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, gui, Minecraft.getInstance().font);
    }

    @Override
    public void drawExtras(PoseStack stack, int mouseX, int mouseY, BaseScreen gui) {
        page.drawExtras(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, gui, Minecraft.getInstance().font);
    }

    @Override
    public boolean isMouseOnWrapper(double mouseX, double mouseY) {
        return true;
    }

    @Override
    public void onHoverOver(int mouseX, int mouseY) {
    }
}
