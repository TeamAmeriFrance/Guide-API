package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.Category;
import amerifrance.guideapi.objects.Entry;
import amerifrance.guideapi.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;

public class EntryWrapper extends AbstractWrapper {

    public Book book;
    public Category category;
    public Entry entry;
    public int x, y, width, height;
    public EntityPlayer player;
    public FontRenderer renderer;

    public EntryWrapper(Book book, Category category, Entry entry, int x, int y, int width, int height, EntityPlayer player, FontRenderer renderer) {
        this.book = book;
        this.category = category;
        this.entry = entry;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.player = player;
        this.renderer = renderer;
    }

    @Override
    public void onClicked() {
        System.out.println(entry.getLocalizedName());
        Minecraft.getMinecraft().displayGuiScreen(new GuiEntry(book, category, entry, player));
    }

    @Override
    public void onHoverOver(int mouseX, int mouseY) {
    }

    @Override
    public boolean canPlayerSee(EntityPlayer player) {
        return entry.canSee(player);
    }

    public boolean canPlayerSee() {
        return canPlayerSee(player);
    }

    @Override
    public void draw() {
        renderer.drawString(entry.getLocalizedName(), x, y, 0);
    }

    @Override
    public void drawExtras(int mouseX, int mouseY, GuiBase gui) {
        entry.drawExtras(mouseX, mouseY, gui);
    }

    @Override
    public boolean isMouseOnWrapper(int mouseX, int mouseY) {
        return GuiHelper.isMouseBetween(mouseX, mouseY, x, y, width, height);
    }
}
