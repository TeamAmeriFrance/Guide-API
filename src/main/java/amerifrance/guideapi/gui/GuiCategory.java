package amerifrance.guideapi.gui;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.Category;
import amerifrance.guideapi.objects.Entry;
import amerifrance.guideapi.wrappers.EntryWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiCategory extends GuiScreen {

    public ResourceLocation texture;
    public Book book;
    public Category category;
    public List<EntryWrapper> entryWrapperList = new ArrayList<EntryWrapper>();
    public int guiLeft, guiTop;
    public int xSize = 192;
    public int ySize = 192;
    public EntityPlayer player;

    public GuiCategory(Book book, Category category, EntityPlayer player) {
        this.texture = new ResourceLocation(ModInformation.TEXTUREPATH + ":textures/gui/default_home");
        this.category = category;
        this.player = player;
        this.book = book;
    }

    public GuiCategory(ResourceLocation texture, Book book, Category category, EntityPlayer player) {
        this.texture = texture;
        this.category = category;
        this.player = player;
        this.book = book;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        int cX = guiLeft + 0;
        int cY = guiTop + 5;
        for (Entry entry : category.entries()) {
            entryWrapperList.add(new EntryWrapper(entry, cX, cY, xSize, 10, player, this.fontRendererObj));
            cY += 10;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        super.drawScreen(mouseX, mouseY, renderPartialTicks);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        for (EntryWrapper wrapper : this.entryWrapperList) {
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
        for (EntryWrapper wrapper : this.entryWrapperList) {
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
        if (keyCode == 14) {
            this.mc.displayGuiScreen(new GuiHome(book, player));
        }
    }
}
