package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import amerifrance.guideapi.objects.abstraction.PageAbstract;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PageWrapper extends AbstractWrapper {

    public Book book;
    public CategoryAbstract category;
    public EntryAbstract entry;
    public PageAbstract page;
    public int guiLeft, guiTop;
    public EntityPlayer player;
    public FontRenderer renderer;
    public ItemStack bookStack;

    public PageWrapper(Book book, CategoryAbstract category, EntryAbstract entry, PageAbstract page, int guiLeft, int guiTop, EntityPlayer player, FontRenderer renderer, ItemStack bookStack) {
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
    public void onHoverOver(int mouseX, int mouseY) {
    }

    @Override
    public boolean canPlayerSee() {
        return page.canSee(player, bookStack);
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
