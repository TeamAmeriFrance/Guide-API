package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.AbstractCategory;
import amerifrance.guideapi.objects.AbstractEntry;
import amerifrance.guideapi.objects.Page;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;

public class PageWrapper extends AbstractWrapper {

    public Book book;
    public AbstractCategory category;
    public AbstractEntry entry;
    public Page page;
    public int guiWidth, guiHeight;
    public EntityPlayer player;
    public FontRenderer renderer;

    public PageWrapper(Book book, AbstractCategory category, AbstractEntry entry, Page page, int guiWidth, int guiHeight, EntityPlayer player, FontRenderer renderer) {
        this.book = book;
        this.category = category;
        this.entry = entry;
        this.page = page;
        this.guiWidth = guiWidth;
        this.guiHeight = guiHeight;
        this.player = player;
        this.renderer = renderer;
    }

    @Override
    public void onHoverOver(int mouseX, int mouseY) {
    }

    @Override
    public boolean canPlayerSee() {
        return page.canSee(player);
    }

    @Override
    public void draw(int mouseX, int mouseY, GuiBase gui) {
        page.drawPage(gui, guiWidth, guiHeight);
    }

    @Override
    public void drawExtras(int mouseX, int mouseY, GuiBase gui) {
        page.drawExtras(mouseX, mouseY, gui);
    }

    @Override
    public boolean isMouseOnWrapper(int mouseX, int mouseY) {
        return true;
    }
}
