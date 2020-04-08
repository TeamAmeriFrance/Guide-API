package amerifrance.guideapi.gui;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.button.ButtonNext;
import amerifrance.guideapi.button.ButtonPrev;
import amerifrance.guideapi.button.ButtonSearch;
import amerifrance.guideapi.network.PacketHandler;
import amerifrance.guideapi.network.PacketSyncHome;
import amerifrance.guideapi.wrapper.CategoryWrapper;
import com.google.common.collect.HashMultimap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class HomeScreen extends BaseScreen {

    public ResourceLocation outlineTexture;
    public ResourceLocation pageTexture;
    public Book book;
    public HashMultimap<Integer, CategoryWrapper> categoryWrapperMap = HashMultimap.create();
    public ButtonNext buttonNext;
    public ButtonPrev buttonPrev;
    public ButtonSearch buttonSearch;
    public int categoryPage;

    public HomeScreen(Book book, PlayerEntity player, ItemStack bookStack) {
        super(new StringTextComponent(book.getTitle()), player, bookStack);
        this.book = book;
        this.pageTexture = book.getPageTexture();
        this.outlineTexture = book.getOutlineTexture();
        this.categoryPage = 0;
    }

    @Override
    public void init() {
        this.categoryWrapperMap.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        addButton(buttonNext = new ButtonNext(guiLeft + 4 * xSize / 6, guiTop + 5 * ySize / 6, (btn) -> {
            if (categoryPage + 1 < categoryWrapperMap.asMap().size()) {
                nextPage();
            }
        }, this));
        addButton(buttonPrev = new ButtonPrev(guiLeft + xSize / 5, guiTop + 5 * ySize / 6, (btn) -> {
            if (categoryPage > 0) {
                prevPage();
            }
        }, this));
        addButton(buttonSearch = new ButtonSearch((guiLeft + xSize / 6) - 25, guiTop + 5, (btn) -> {
            minecraft.displayGuiScreen(new SearchScreen(book, player, bookStack, this));
        }, this));

        int cX = guiLeft + 45;
        int cY = guiTop + 40;
        int drawLoc = 0;
        int i = 0;
        int pageNumber = 0;

        for (CategoryAbstract category : book.getCategoryList()) {
            if (category.entries.isEmpty())
                continue;

            category.onInit(book, this, player, bookStack);
            switch (drawLoc) {
                case 0: {
                    categoryWrapperMap.put(pageNumber, new CategoryWrapper(book, category, cX, cY, 23, 23, player, this.font, itemRenderer, false, bookStack));
                    cX += 27;
                    drawLoc = 1;
                    break;
                }
                case 1: {
                    categoryWrapperMap.put(pageNumber, new CategoryWrapper(book, category, cX, cY, 23, 23, player, this.font, itemRenderer, false, bookStack));
                    cX += 27;
                    drawLoc = 2;
                    break;
                }
                case 2: {
                    categoryWrapperMap.put(pageNumber, new CategoryWrapper(book, category, cX, cY, 23, 23, player, this.font, itemRenderer, false, bookStack));
                    cX += 27;
                    drawLoc = 3;
                    break;
                }
                case 3: {
                    categoryWrapperMap.put(pageNumber, new CategoryWrapper(book, category, cX, cY, 23, 23, player, this.font, itemRenderer, false, bookStack));
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
    public void render(int mouseX, int mouseY, float renderPartialTicks) {
        minecraft.getTextureManager().bindTexture(pageTexture);
        blit(guiLeft, guiTop, 0, 0, xSize, ySize);
        minecraft.getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(guiLeft, guiTop, 0, 0, xSize, ySize, book.getColor());
        drawCenteredString(font, TextHelper.localize(book.getHeader()).replace("\\n", "\n").replace("&", "\u00a7"), guiLeft + xSize / 2 + 1, guiTop + 15, 0);

        categoryPage = MathHelper.clamp(categoryPage, 0, categoryWrapperMap.size() - 1);

        for (CategoryWrapper wrapper : this.categoryWrapperMap.get(categoryPage))
            if (wrapper.canPlayerSee())
                wrapper.draw(mouseX, mouseY, this);

        for (CategoryWrapper wrapper : this.categoryWrapperMap.get(categoryPage))
            if (wrapper.canPlayerSee())
                wrapper.drawExtras(mouseX, mouseY, this);

        drawCenteredString(font, String.format("%d/%d", categoryPage + 1, categoryWrapperMap.asMap().size()), guiLeft + xSize / 2, guiTop + 5 * ySize / 6, 0);
        drawCenteredStringWithShadow(font, TextHelper.localize(book.getTitle()), guiLeft + xSize / 2, guiTop - 10, Color.WHITE.getRGB());

        buttonPrev.visible = categoryPage != 0;
        buttonNext.visible = categoryPage != categoryWrapperMap.asMap().size() - 1 && !categoryWrapperMap.asMap().isEmpty();

        super.render(mouseX, mouseY, renderPartialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int typeofClick) {
        if (!super.mouseClicked(mouseX, mouseY, typeofClick)) {
            for (CategoryWrapper wrapper : this.categoryWrapperMap.get(categoryPage)) {
                if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                    if (typeofClick == 0)
                        wrapper.category.onLeftClicked(book, mouseX, mouseY, player, bookStack);


                    else if (typeofClick == 1)
                        wrapper.category.onRightClicked(book, mouseX, mouseY, player, bookStack);

                    return true;
                }
            }
            return false;
        }
        return true;

    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double movement) {

        if (movement < 0)
            nextPage();
        else if (movement > 0)
            prevPage();

        return movement != 0 || super.mouseScrolled(p_mouseScrolled_1_, p_mouseScrolled_3_, movement);
    }

    @Override
    public boolean keyPressed(int keyCode, int p_keyPressed_2_, int p_keyPressed_3_) {
        if ((keyCode == GLFW.GLFW_KEY_UP || keyCode == GLFW.GLFW_KEY_RIGHT) && categoryPage + 1 < categoryWrapperMap.asMap().size()) {
            nextPage();
        } else if ((keyCode == GLFW.GLFW_KEY_DOWN || keyCode == GLFW.GLFW_KEY_LEFT) && categoryPage > 0) {
            prevPage();
        }

        return super.keyPressed(keyCode, p_keyPressed_2_, p_keyPressed_3_);
    }


    @Override
    public void onClose() {
        super.onClose();

        PacketHandler.INSTANCE.sendToServer(new PacketSyncHome(categoryPage));
    }

    public void nextPage() {
        if (categoryPage != categoryWrapperMap.asMap().size() - 1 && !categoryWrapperMap.asMap().isEmpty())
            categoryPage++;
    }

    public void prevPage() {
        if (categoryPage != 0)
            categoryPage--;
    }
}
