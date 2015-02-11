package amerifrance.guideapi.gui;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.objects.AbstractCategory;
import amerifrance.guideapi.objects.AbstractEntry;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.Page;
import amerifrance.guideapi.wrappers.CategoryWrapper;
import amerifrance.guideapi.wrappers.PageWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class GuiEntry extends GuiBase {

    public GuiCategory categoryGui;
    public ResourceLocation outlineTexture;
    public ResourceLocation pageTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_colored.png");
    public Book book;
    public AbstractCategory category;
    public AbstractEntry entry;
    public List<CategoryWrapper> categoryWrappers = new ArrayList<CategoryWrapper>();
    public List<PageWrapper> pageWrapperList = new ArrayList<PageWrapper>();
    private int pageNumber;

    public GuiEntry(GuiCategory categoryGui, Book book, AbstractCategory category, AbstractEntry entry, EntityPlayer player) {
        super(player);
        this.categoryGui = categoryGui;
        this.outlineTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_greyscale.png");
        this.category = category;
        this.book = book;
        this.entry = entry;
        this.pageNumber = 0;
    }

    public GuiEntry(GuiCategory categoryGui, ResourceLocation texture, Book book, AbstractCategory category, AbstractEntry entry, EntityPlayer player) {
        super(player);
        this.categoryGui = categoryGui;
        this.outlineTexture = texture;
        this.category = category;
        this.book = book;
        this.entry = entry;
        this.pageNumber = 0;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.categoryWrappers.clear();
        this.pageWrapperList.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        int cX = guiLeft;
        int cY = guiTop + 15;
        boolean drawOnLeft = true;

        for (AbstractCategory category : book.categories()) {
            if (drawOnLeft) {
                categoryWrappers.add(new CategoryWrapper(categoryGui.homeGui, book, category, cX, cY, 15, 15, player, this.fontRendererObj, this.itemRender, drawOnLeft));
                cX = guiLeft + 180;
                drawOnLeft = false;
            } else {
                categoryWrappers.add(new CategoryWrapper(categoryGui.homeGui, book, category, cX, cY, 15, 15, player, this.fontRendererObj, this.itemRender, drawOnLeft));
                cY += 25;
                cX = guiLeft;
                drawOnLeft = true;
            }
        }

        for (Page page : this.entry.pages()) {
            pageWrapperList.add(new PageWrapper(book, category, entry, page, guiLeft, guiTop, player, this.fontRendererObj));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        super.drawScreen(mouseX, mouseY, renderPartialTicks);

        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.canPlayerSee()) {
                wrapper.draw(mouseX, mouseY, this);
            }
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(pageTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        Minecraft.getMinecraft().getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(guiLeft, guiTop, 0, 0, xSize, ySize, book.color());

        if (pageNumber < pageWrapperList.size()) {
            if (pageWrapperList.get(pageNumber).canPlayerSee()) {
                pageWrapperList.get(pageNumber).draw(mouseX, mouseY, this);
                pageWrapperList.get(pageNumber).drawExtras(mouseX, mouseY, this);
            }
        }

        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.canPlayerSee()) {
                wrapper.drawExtras(mouseX, mouseY, this);
            }
        }

        drawCenteredString(fontRendererObj, String.valueOf(pageNumber + 1) + "/" + String.valueOf(pageWrapperList.size()), guiLeft + xSize / 2, guiTop + 5 * ySize / 6, 0);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int typeofClick) {
        super.mouseClicked(mouseX, mouseY, typeofClick);

        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                if (typeofClick == 0) wrapper.category.onLeftClicked(book, mouseX, mouseY, player, categoryGui.homeGui);
                else if (typeofClick == 1)
                    wrapper.category.onRightClicked(book, mouseX, mouseY, player, categoryGui.homeGui);
            }
        }

        for (PageWrapper wrapper : this.pageWrapperList) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                if (typeofClick == 0) pageWrapperList.get(pageNumber).page.onLeftClicked(mouseX, mouseY);
                if (typeofClick == 1) pageWrapperList.get(pageNumber).page.onRightClicked(mouseX, mouseY);
            }
        }

        if (typeofClick == 1) {
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
