package amerifrance.guideapi.gui;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.Category;
import amerifrance.guideapi.objects.Entry;
import amerifrance.guideapi.wrappers.CategoryWrapper;
import amerifrance.guideapi.wrappers.EntryWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class GuiCategory extends GuiBase {

    public GuiHome homeGui;
    public ResourceLocation outlineTexture;
    public ResourceLocation pageTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_colored.png");
    public Book book;
    public Category category;
    public List<CategoryWrapper> categoryWrappers = new ArrayList<CategoryWrapper>();
    public List<EntryWrapper> entryWrapperList = new ArrayList<EntryWrapper>();

    public GuiCategory(GuiHome homeGui, Book book, Category category, EntityPlayer player) {
        super(player);
        this.homeGui = homeGui;
        this.outlineTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_greyscale.png");
        this.category = category;
        this.book = book;
    }

    public GuiCategory(GuiHome homeGui, ResourceLocation texture, Book book, Category category, EntityPlayer player) {
        super(player);
        this.homeGui = homeGui;
        this.outlineTexture = texture;
        this.category = category;
        this.book = book;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.categoryWrappers.clear();
        this.entryWrapperList.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        int cX = guiLeft;
        int cY = guiTop + 15;
        boolean drawOnLeft = true;
        for (Category category : book.categories()) {
            if (drawOnLeft) {
                categoryWrappers.add(new CategoryWrapper(homeGui, book, category, cX, cY, 15, 15, player, this.fontRendererObj, this.itemRender, drawOnLeft));
                cX = guiLeft + 180;
                drawOnLeft = false;
            } else {
                categoryWrappers.add(new CategoryWrapper(homeGui, book, category, cX, cY, 15, 15, player, this.fontRendererObj, this.itemRender, drawOnLeft));
                cY += 25;
                cX = guiLeft;
                drawOnLeft = true;
            }
        }

        int eX = guiLeft + 0;
        int eY = guiTop + 5;
        for (Entry entry : category.entries()) {
            entryWrapperList.add(new EntryWrapper(this, book, category, entry, eX, eY, xSize, 10, player, this.fontRendererObj));
            eY += 10;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        super.drawScreen(mouseX, mouseY, renderPartialTicks);

        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.canPlayerSee()) {
                wrapper.draw(this);
                wrapper.drawExtras(mouseX, mouseY, this);
            }
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(pageTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        Minecraft.getMinecraft().getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(guiLeft, guiTop, 0, 0, xSize, ySize, book.color());

        for (EntryWrapper wrapper : this.entryWrapperList) {
            if (wrapper.canPlayerSee()) {
                wrapper.draw(this);
                wrapper.drawExtras(mouseX, mouseY, this);
            }
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                wrapper.onHoverOver(mouseX, mouseY);
            }
        }

        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                this.drawHoveringText(wrapper.getTooltip(), mouseX, mouseY, this.fontRendererObj);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int typeofClick) {
        super.mouseClicked(mouseX, mouseY, typeofClick);

        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                this.mc.displayGuiScreen(new GuiCategory(homeGui, book, wrapper.category, player));

                if (typeofClick == 0) wrapper.category.onLeftClicked(mouseX, mouseY);
                else if (typeofClick == 1) wrapper.category.onRightClicked(mouseX, mouseY);
            }
        }

        for (EntryWrapper wrapper : this.entryWrapperList) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                this.mc.displayGuiScreen(new GuiEntry(this, book, category, wrapper.entry, player));

                if (typeofClick == 0) wrapper.entry.onLeftClicked(mouseX, mouseY);
                else if (typeofClick == 1) wrapper.entry.onRightClicked(mouseX, mouseY);
            }
        }

        if (typeofClick == 1) {
            this.mc.displayGuiScreen(homeGui);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_BACK || keyCode == this.mc.gameSettings.keyBindUseItem.getKeyCode()) {
            this.mc.displayGuiScreen(homeGui);
        }
    }
}
