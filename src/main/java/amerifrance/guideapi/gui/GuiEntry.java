package amerifrance.guideapi.gui;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.Category;
import amerifrance.guideapi.objects.Entry;
import amerifrance.guideapi.objects.Page;
import amerifrance.guideapi.wrappers.PageWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class GuiEntry extends GuiBase {

    public GuiCategory categoryGui;
    public ResourceLocation texture;
    public Book book;
    public Category category;
    public Entry entry;
    public List<PageWrapper> pageWrapperList = new ArrayList<PageWrapper>();
    private int pageNumber;

    public GuiEntry(GuiCategory categoryGui, Book book, Category category, Entry entry, EntityPlayer player) {
        super(player);
        this.categoryGui = categoryGui;
        this.texture = new ResourceLocation(ModInformation.GUITEXLOC + "default_home");
        this.category = category;
        this.book = book;
        this.entry = entry;
        this.pageNumber = 0;
    }

    public GuiEntry(GuiCategory categoryGui, ResourceLocation texture, Book book, Category category, Entry entry, EntityPlayer player) {
        super(player);
        this.categoryGui = categoryGui;
        this.texture = texture;
        this.category = category;
        this.book = book;
        this.entry = entry;
        this.pageNumber = 0;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.pageWrapperList.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        for (Page page : this.entry.pages()) {
            pageWrapperList.add(new PageWrapper(book, category, entry, page, guiLeft, guiTop, player, this.fontRendererObj));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        super.drawScreen(mouseX, mouseY, renderPartialTicks);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (pageNumber < pageWrapperList.size()) {
            if (pageWrapperList.get(pageNumber).canPlayerSee()) {
                pageWrapperList.get(pageNumber).draw();
                pageWrapperList.get(pageNumber).drawExtras(mouseX, mouseY, this);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int typeOfHit) {
        for (PageWrapper wrapper : this.pageWrapperList) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                wrapper.onClicked();
            }
        }
        if (typeOfHit == 1) {
            this.mc.displayGuiScreen(categoryGui);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_BACK || keyCode == this.mc.gameSettings.keyBindUseItem.getKeyCode()) {
            this.mc.displayGuiScreen(categoryGui);
        }
        if ((keyCode == Keyboard.KEY_UP || keyCode == Keyboard.KEY_RIGHT) && pageNumber + 1 < pageWrapperList.size()) {
            this.pageNumber++;
        }
        if ((keyCode == Keyboard.KEY_DOWN || keyCode == Keyboard.KEY_LEFT) && pageNumber > 0) {
            this.pageNumber--;
        }
    }
}
