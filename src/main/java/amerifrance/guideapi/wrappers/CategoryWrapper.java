package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiHome;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.util.GuiHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;

public class CategoryWrapper extends AbstractWrapper {

    public Book book;
    public AbstractCategory category;
    public int x, y, width, height;
    public EntityPlayer player;
    public FontRenderer renderer;
    public RenderItem renderItem;
    public GuiHome homeGui;
    public boolean drawOnLeft;
    public int categoryNumber;

    public CategoryWrapper(GuiHome homeGui, Book book, AbstractCategory category, int x, int y, int width, int height, EntityPlayer player, FontRenderer renderer, RenderItem renderItem, boolean drawOnLeft) {
        this.book = book;
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.player = player;
        this.renderer = renderer;
        this.renderItem = renderItem;
        this.homeGui = homeGui;
        this.drawOnLeft = drawOnLeft;
        this.categoryNumber = categoryNumber;
    }

    @Override
    public void onHoverOver(int mouseX, int mouseY) {
    }

    @Override
    public boolean canPlayerSee() {
        return category.canSee(player);
    }

    @Override
    public void draw(int mouseX, int mouseY, GuiBase gui) {
        category.draw(book, x, y, width, height, mouseX, mouseY, gui, drawOnLeft, renderItem);
    }

    @Override
    public void drawExtras(int mouseX, int mouseY, GuiBase gui) {
        category.drawExtras(book, x, y, width, height, mouseX, mouseY, gui, drawOnLeft, renderItem);
    }

    @Override
    public boolean isMouseOnWrapper(int mouseX, int mouseY) {
        return GuiHelper.isMouseBetween(mouseX, mouseY, x, y, width, height);
    }
}
