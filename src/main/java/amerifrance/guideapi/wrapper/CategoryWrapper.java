package amerifrance.guideapi.wrapper;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CategoryWrapper extends AbstractWrapper {

    public Book book;
    public CategoryAbstract category;
    public int x, y, width, height;
    public EntityPlayer player;
    public FontRenderer renderer;
    public RenderItem renderItem;
    public boolean drawOnLeft;
    public ItemStack bookStack;

    public CategoryWrapper(Book book, CategoryAbstract category, int x, int y, int width, int height, EntityPlayer player, FontRenderer renderer, RenderItem renderItem, boolean drawOnLeft, ItemStack bookStack) {
        this.book = book;
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.player = player;
        this.renderer = renderer;
        this.renderItem = renderItem;
        this.drawOnLeft = drawOnLeft;
        this.bookStack = bookStack;
    }

    @Override
    public void onHoverOver(int mouseX, int mouseY) {
    }

    @Override
    public boolean canPlayerSee() {
        return category.canSee(player, bookStack);
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
