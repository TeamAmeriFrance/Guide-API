package amerifrance.guideapi.gui;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import amerifrance.guideapi.wrappers.CategoryWrapper;
import amerifrance.guideapi.wrappers.EntryWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class GuiCategory extends GuiBase {

    public GuiHome homeGui;
    public ResourceLocation outlineTexture;
    public ResourceLocation pageTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_colored.png");
    public Book book;
    public AbstractCategory category;
    public List<CategoryWrapper> categoryWrappers = new ArrayList<CategoryWrapper>();
    public List<EntryWrapper> entryWrapperList = new ArrayList<EntryWrapper>();

    public GuiCategory(GuiHome homeGui, Book book, AbstractCategory category, EntityPlayer player, ItemStack bookStack) {
        super(player, bookStack);
        this.homeGui = homeGui;
        this.outlineTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_greyscale.png");
        this.category = category;
        this.book = book;
    }

    public GuiCategory(GuiHome homeGui, ResourceLocation texture, Book book, AbstractCategory category, EntityPlayer player, ItemStack bookStack) {
        super(player, bookStack);
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
        for (AbstractCategory category : book.categories()) {
            if (drawOnLeft) {
                categoryWrappers.add(new CategoryWrapper(homeGui, book, category, cX, cY, 15, 15, player, this.fontRendererObj, this.itemRender, drawOnLeft, bookStack));
                cX = guiLeft + 180;
                drawOnLeft = false;
            } else {
                categoryWrappers.add(new CategoryWrapper(homeGui, book, category, cX, cY, 15, 15, player, this.fontRendererObj, this.itemRender, drawOnLeft, bookStack));
                cY += 25;
                cX = guiLeft;
                drawOnLeft = true;
            }
        }

        int eX = guiLeft + 37;
        int eY = guiTop + 12;
        for (AbstractEntry entry : category.entries()) {
            entryWrapperList.add(new EntryWrapper(this, book, category, entry, eX, eY, xSize, 10, player, this.fontRendererObj, bookStack));
            eY += 10;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        super.drawScreen(mouseX, mouseY, renderPartialTicks);

        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.canPlayerSee()) wrapper.draw(mouseX, mouseY, this);
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(pageTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        Minecraft.getMinecraft().getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(guiLeft, guiTop, 0, 0, xSize, ySize, book.color());

        for (EntryWrapper wrapper : this.entryWrapperList) {
            if (wrapper.canPlayerSee()) {
                wrapper.draw(mouseX, mouseY, this);
                wrapper.drawExtras(mouseX, mouseY, this);
            }
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                wrapper.onHoverOver(mouseX, mouseY);
            }
        }

        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.canPlayerSee()) wrapper.drawExtras(mouseX, mouseY, this);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int typeofClick) {
        super.mouseClicked(mouseX, mouseY, typeofClick);

        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                if (typeofClick == 0) wrapper.category.onLeftClicked(book, mouseX, mouseY, player, homeGui);
                else if (typeofClick == 1) wrapper.category.onRightClicked(book, mouseX, mouseY, player, homeGui);
            }
        }

        for (EntryWrapper wrapper : this.entryWrapperList) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                if (typeofClick == 0) wrapper.entry.onLeftClicked(book, category, mouseX, mouseY, player, this);
                else if (typeofClick == 1) wrapper.entry.onRightClicked(book, category, mouseX, mouseY, player, this);
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
