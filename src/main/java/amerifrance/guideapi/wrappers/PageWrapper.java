package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;

public class PageWrapper extends AbstractWrapper {

    public Book book;
    public AbstractCategory category;
    public AbstractEntry entry;
    public AbstractPage page;
    public int guiLeft, guiTop;
    public EntityPlayer player;
    public FontRenderer renderer;

    public PageWrapper(Book book, AbstractCategory category, AbstractEntry entry, AbstractPage page, int guiLeft, int guiTop, EntityPlayer player, FontRenderer renderer) {
        this.book = book;
        this.category = category;
        this.entry = entry;
        this.page = page;
        this.guiLeft = guiLeft;
        this.guiTop = guiTop;
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
        page.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, gui, Minecraft.getMinecraft().fontRenderer);
    }

    @Override
    public void drawExtras(int mouseX, int mouseY, GuiBase gui) {
        page.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, gui, Minecraft.getMinecraft().fontRenderer);
    }

    @Override
    public boolean isMouseOnWrapper(int mouseX, int mouseY) {
        return true;
    }
}
