package amerifrance.guideapi.gui;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.Category;
import amerifrance.guideapi.wrappers.CategoryWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiHome extends GuiScreen {

    public ResourceLocation texture;
    public Book book;
    public List<CategoryWrapper> categoryWrappers = new ArrayList<CategoryWrapper>();
    public int guiLeft, guiTop;
    public int xSize = 192;
    public int ySize = 192;
    public EntityPlayer player;

    public GuiHome(Book book, EntityPlayer player) {
        this.texture = new ResourceLocation(ModInformation.GUITEXLOC + "default_home");
        this.book = book;
        this.player = player;
    }

    public GuiHome(ResourceLocation texture, Book book, EntityPlayer player) {
        this.texture = texture;
        this.book = book;
        this.player = player;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.categoryWrappers.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        int cX = guiLeft + 0;
        int cY = guiTop + 5;
        for (Category category : book.categories()) {
            categoryWrappers.add(new CategoryWrapper(book, category, cX, cY, 15, 15, player, this.fontRendererObj, this.itemRender));
            cY += 15;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        super.drawScreen(mouseX, mouseY, renderPartialTicks);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.canPlayerSee()) {
                wrapper.draw();
            }

            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                wrapper.onHoverOver(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int typeOfHit) {
        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee(player)) {
                wrapper.onClicked();
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.displayGuiScreen((GuiScreen) null);
            this.mc.setIngameFocus();
        }
    }
}
