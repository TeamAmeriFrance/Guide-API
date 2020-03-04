package amerifrance.guideapi.wrapper;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class EntryWrapper extends AbstractWrapper {

    public Book book;
    public CategoryAbstract category;
    public EntryAbstract entry;
    public int x, y, width, height;
    public PlayerEntity player;
    public FontRenderer renderer;
    public GuiCategory categoryGui;
    public ItemStack bookStack;

    public EntryWrapper(GuiCategory categoryGui, Book book, CategoryAbstract category, EntryAbstract entry, int x, int y, int width, int height, PlayerEntity player, FontRenderer renderer, ItemStack bookStack) {
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
    public void draw(int mouseX, int mouseY, GuiBase gui) {
        entry.draw(book, category, x, y, width, height, mouseX, mouseY, gui, Minecraft.getMinecraft().fontRenderer);
    }

    @Override
    public void drawExtras(int mouseX, int mouseY, GuiBase gui) {
        entry.drawExtras(book, category, x, y, width, height, mouseX, mouseY, gui, Minecraft.getMinecraft().fontRenderer);
    }

    @Override
    public boolean isMouseOnWrapper(int mouseX, int mouseY) {
        return GuiHelper.isMouseBetween(mouseX, mouseY, x, y, width, height);
    }
}
