package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiHome;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.Category;
import amerifrance.guideapi.util.GuiHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

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
    public boolean drawOnLeft;

    public CategoryWrapper(GuiHome homeGui, Book book, Category category, int x, int y, int width, int height, EntityPlayer player, FontRenderer renderer, RenderItem renderItem, boolean drawOnLeft) {
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
    }

    @Override
    public void onHoverOver(int mouseX, int mouseY) {
    }

    @Override
    public boolean canPlayerSee() {
        return category.canSee(player);
    }

    @Override
    public void draw(GuiBase gui) {
        if (drawOnLeft) {
            GL11.glPushMatrix();
            gui.mc.getTextureManager().bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "category_left.png"));
            GL11.glColor3f(book.color().getRed(), book.color().getGreen(), book.color().getBlue());
            GuiHelper.drawIconWithColor(x - 5, y + 2, 102, 12, 0, book.color());
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            gui.mc.getTextureManager().bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "category_right.png"));
            GL11.glColor3f(book.color().getRed(), book.color().getGreen(), book.color().getBlue());
            GuiHelper.drawIconWithColor(x - 80, y + 2, 102, 12, 0, book.color());
            GL11.glPopMatrix();
        }
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
