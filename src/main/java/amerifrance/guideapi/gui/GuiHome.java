package amerifrance.guideapi.gui;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.wrappers.CategoryWrapper;
import com.google.common.collect.HashMultimap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class GuiHome extends GuiBase {

    public ResourceLocation outlineTexture;
    public ResourceLocation pageTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_colored.png");
    public Book book;
    public int categoryPage;
    public HashMultimap<Integer, CategoryWrapper> categoryWrappers;

    public GuiHome(Book book, EntityPlayer player, ItemStack bookStack) {
        super(player, bookStack);
        this.outlineTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_greyscale.png");
        this.book = book;
        this.categoryPage = 0;
        this.categoryWrappers = this.categoryWrappers.create();
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.categoryWrappers.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        int cX = guiLeft;
        int cY = guiTop + 15;
        boolean drawOnLeft = true;
        int i = 0;
        int pageNumber = 0;

        for (AbstractCategory category : book.categories()) {
            if (drawOnLeft) {
                categoryWrappers.put(pageNumber, new CategoryWrapper(book, category, cX, cY, 15, 15, player, this.fontRendererObj, this.itemRender, drawOnLeft, bookStack));
                cX = guiLeft + 180;
                drawOnLeft = false;
            } else {
                categoryWrappers.put(pageNumber, new CategoryWrapper(book, category, cX, cY, 15, 15, player, this.fontRendererObj, this.itemRender, drawOnLeft, bookStack));
                cY += 25;
                cX = guiLeft;
                drawOnLeft = true;
            }
            i++;

            if (i >= 12) {
                i = 0;
                cX = guiLeft;
                cY = guiTop + 15;
                pageNumber++;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        super.drawScreen(mouseX, mouseY, renderPartialTicks);
        Minecraft.getMinecraft().getTextureManager().bindTexture(pageTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        Minecraft.getMinecraft().getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(guiLeft, guiTop, 0, 0, xSize, ySize, book.color());

        for (CategoryWrapper wrapper : this.categoryWrappers.get(categoryPage)) {
            if (wrapper.canPlayerSee()) {
                wrapper.draw(mouseX, mouseY, this);
                wrapper.drawExtras(mouseX, mouseY, this);
            }
        }

        drawCenteredString(fontRendererObj, String.valueOf(categoryPage + 1) + "/" + String.valueOf(categoryWrappers.asMap().size()), guiLeft + xSize / 2, guiTop + 5 * ySize / 6, 0);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int typeofClick) {
        super.mouseClicked(mouseX, mouseY, typeofClick);

        for (CategoryWrapper wrapper : this.categoryWrappers.get(categoryPage)) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                if (typeofClick == 0) wrapper.category.onLeftClicked(book, mouseX, mouseY, player, bookStack);
                else if (typeofClick == 1) wrapper.category.onRightClicked(book, mouseX, mouseY, player, bookStack);
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_ADD && categoryPage + 1 < categoryWrappers.asMap().size()) {
            this.categoryPage++;
        }
        if (keyCode == Keyboard.KEY_SUBTRACT && categoryPage > 0) {
            this.categoryPage--;
        }
    }
}
