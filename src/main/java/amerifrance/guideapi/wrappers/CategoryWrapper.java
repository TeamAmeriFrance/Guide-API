package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiHome;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.Category;
import amerifrance.guideapi.util.GuiHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class CategoryWrapper extends AbstractWrapper {

    public Book book;
    public Category category;
    public int x, y, width, height;
    public EntityPlayer player;
    public FontRenderer renderer;
    public RenderItem renderItem;
    public GuiHome homeGui;

    public CategoryWrapper(GuiHome homeGui, Book book, Category category, int x, int y, int width, int height, EntityPlayer player, FontRenderer renderer, RenderItem renderItem) {
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
    }

    @Override
    public void onHoverOver(int mouseX, int mouseY) {
    }

    @Override
    public boolean canPlayerSee() {
        return category.canSee(player);
    }

    @Override
    public void draw() {
        GuiHelper.drawItemStack(category.stack(), x, y, this.renderItem);
    }

    @Override
    public void drawExtras(int mouseX, int mouseY, GuiBase gui) {
        category.drawExtras(mouseX, mouseY, gui);
    }

    @Override
    public boolean isMouseOnWrapper(int mouseX, int mouseY) {
        return GuiHelper.isMouseBetween(mouseX, mouseY, x, y, width, height);
    }

    public List<String> getTooltip() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(category.getLocalizedName());
        return list;
    }
}
