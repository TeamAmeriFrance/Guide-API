package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;

public class EntryWrapper extends AbstractWrapper {

    public Book book;
    public AbstractCategory category;
    public AbstractEntry entry;
    public int x, y, width, height;
    public EntityPlayer player;
    public FontRenderer renderer;
    public GuiCategory categoryGui;

    public EntryWrapper(GuiCategory categoryGui, Book book, AbstractCategory category, AbstractEntry entry, int x, int y, int width, int height, EntityPlayer player, FontRenderer renderer) {
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
    }

    @Override
    public void onHoverOver(int mouseX, int mouseY) {
    }

    @Override
    public boolean canPlayerSee() {
        return entry.canSee(player);
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
