package de.maxanier.guideapi.gui;

import com.google.common.collect.HashMultimap;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.button.ButtonNext;
import de.maxanier.guideapi.button.ButtonPrev;
import de.maxanier.guideapi.button.ButtonSearch;
import de.maxanier.guideapi.network.PacketHandler;
import de.maxanier.guideapi.network.PacketSyncHome;
import de.maxanier.guideapi.wrapper.CategoryWrapper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
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

    public HomeScreen(Book book, Player player, ItemStack bookStack) {
        super(book.getTitle(), player, bookStack);
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

        addRenderableWidget(buttonNext = new ButtonNext(guiLeft + 4 * xSize / 6, guiTop + 5 * ySize / 6, (btn) -> {
            if (categoryPage + 1 < categoryWrapperMap.asMap().size()) {
                nextPage();
            }
        }, this));
        addRenderableWidget(buttonPrev = new ButtonPrev(guiLeft + xSize / 5, guiTop + 5 * ySize / 6, (btn) -> {
            if (categoryPage > 0) {
                prevPage();
            }
        }, this));
        addRenderableWidget(buttonSearch = new ButtonSearch((guiLeft + xSize / 6) - 25, guiTop + 5, (btn) -> {
            minecraft.setScreen(new SearchScreen(book, player, bookStack, this));
        }, this));

        int cX = guiLeft + 55;
        int cY = guiTop + 40;
        int i = 0;
        int pageNumber = 0;

        for (CategoryAbstract category : book.getCategoryList()) {
            if (category.entries.isEmpty())
                continue;

            category.onInit(book, this, player, bookStack);
            int x = i % 5;
            int y = i / 5;
            categoryWrapperMap.put(pageNumber, new CategoryWrapper(book, category, cX + x * 27, cY + y * 30, 23, 23, player, this.font, itemRenderer, false, bookStack));
            i++;

            if (i >= 20) {
                i = 0;
                pageNumber++;
            }
        }
    }

    @Override
    public void onClose() {
        super.onClose();

        PacketHandler.INSTANCE.sendToServer(new PacketSyncHome(categoryPage));
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
    public void render(PoseStack stack, int mouseX, int mouseY, float renderPartialTicks) {
        RenderSystem.setShaderTexture(0,pageTexture);
        blit(stack, guiLeft, guiTop, 0, 0, xSize, ySize);
        RenderSystem.setShaderTexture(0,outlineTexture);
        drawTexturedModalRectWithColor(stack, guiLeft, guiTop, 0, 0, xSize, ySize, book.getColor());
        drawCenteredStringWithoutShadow(stack, font, book.getHeader().getVisualOrderText(), guiLeft + xSize / 2 + 1, guiTop + 15, 0);

        categoryPage = Mth.clamp(categoryPage, 0, categoryWrapperMap.size() - 1);

        for (CategoryWrapper wrapper : this.categoryWrapperMap.get(categoryPage))
            if (wrapper.canPlayerSee())
                wrapper.draw(stack, mouseX, mouseY, this);

        for (CategoryWrapper wrapper : this.categoryWrapperMap.get(categoryPage))
            if (wrapper.canPlayerSee())
                wrapper.drawExtras(stack, mouseX, mouseY, this);

        drawCenteredStringWithoutShadow(stack, font, String.format("%d/%d", categoryPage + 1, categoryWrapperMap.asMap().size()), guiLeft + xSize / 2, guiTop + 5 * ySize / 6, 0);
        drawCenteredString(stack, font, book.getTitle(), guiLeft + xSize / 2, guiTop - 10, Color.WHITE.getRGB());

        buttonPrev.visible = categoryPage != 0;
        buttonNext.visible = categoryPage != categoryWrapperMap.asMap().size() - 1 && !categoryWrapperMap.asMap().isEmpty();

        super.render(stack, mouseX, mouseY, renderPartialTicks);
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
