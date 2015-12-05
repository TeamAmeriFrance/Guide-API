package amerifrance.guideapi.gui;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.buttons.ButtonNext;
import amerifrance.guideapi.buttons.ButtonPrev;
import amerifrance.guideapi.wrappers.CategoryWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class GuiHomeNew extends GuiHome {

    public GuiHomeNew(Book book, EntityPlayer player, ItemStack bookStack) {
        super(book, player, bookStack);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initGui() {
        this.buttonList.clear();
        this.categoryWrapperMap.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        this.buttonList.add(buttonNext = new ButtonNext(0, guiLeft + 4 * xSize / 6, guiTop + 5 * ySize / 6, this));
        this.buttonList.add(buttonPrev = new ButtonPrev(1, guiLeft + xSize / 5, guiTop + 5 * ySize / 6, this));

        int cX = guiLeft + 45;
        int cY = guiTop + 40;
        int drawLoc = 0;
        int i = 0;
        int pageNumber = 0;

        for (CategoryAbstract category : book.getCategoryList()) {
            category.onInit(book, this, player, bookStack);
            switch (drawLoc) {
                case 0: {
                    categoryWrapperMap.put(pageNumber, new CategoryWrapper(book, category, cX, cY, 23, 23, player, this.fontRendererObj, itemRender, false, bookStack));
                    cX += 27;
                    drawLoc = 1;
                    break;
                }
                case 1: {
                    categoryWrapperMap.put(pageNumber, new CategoryWrapper(book, category, cX, cY, 23, 23, player, this.fontRendererObj, itemRender, false, bookStack));
                    cX += 27;
                    drawLoc = 2;
                    break;
                }
                case 2: {
                    categoryWrapperMap.put(pageNumber, new CategoryWrapper(book, category, cX, cY, 23, 23, player, this.fontRendererObj, itemRender, false, bookStack));
                    cX += 27;
                    drawLoc = 3;
                    break;
                }
                case 3: {
                    categoryWrapperMap.put(pageNumber, new CategoryWrapper(book, category, cX, cY, 23, 23, player, this.fontRendererObj, itemRender, false, bookStack));
                    drawLoc = 0;
                    cX = guiLeft + 45;
                    cY += 30;
                    break;
                }
            }
            i++;

            if (i >= 16) {
                i = 0;
                cX = guiLeft + 45;
                cY = guiTop + 40;
                pageNumber++;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(pageTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        Minecraft.getMinecraft().getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(guiLeft, guiTop, 0, 0, xSize, ySize, book.getBookColor());
        drawCenteredString(fontRendererObj, book.getLocalizedWelcomeMessage().replace("\\n", "\n").replace("&", "\u00a7"), guiLeft + xSize / 2 + 1, guiTop + 15, 0);

        for (CategoryWrapper wrapper : this.categoryWrapperMap.get(categoryPage))
            if (wrapper.canPlayerSee())
                wrapper.draw(mouseX, mouseY, this);

        for (CategoryWrapper wrapper : this.categoryWrapperMap.get(categoryPage))
            if (wrapper.canPlayerSee())
                wrapper.drawExtras(mouseX, mouseY, this);

        drawCenteredString(fontRendererObj, String.valueOf(categoryPage + 1) + "/" + String.valueOf(categoryWrapperMap.asMap().size()), guiLeft + xSize / 2, guiTop + 5 * ySize / 6, 0);
        drawCenteredStringWithShadow(fontRendererObj, book.getLocalizedBookTitle(), guiLeft + xSize / 2, guiTop - 10, Color.WHITE.getRGB());

        buttonPrev.visible = categoryPage != 0;
        buttonNext.visible = categoryPage != categoryWrapperMap.asMap().size() - 1;

        for (Object button : this.buttonList)
            ((GuiButton) button).drawButton(this.mc, mouseX, mouseY);
    }
}
