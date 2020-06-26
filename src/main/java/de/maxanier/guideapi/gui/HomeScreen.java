package de.maxanier.guideapi.gui;

import com.google.common.collect.HashMultimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.util.TextHelper;
import de.maxanier.guideapi.button.ButtonNext;
import de.maxanier.guideapi.button.ButtonPrev;
import de.maxanier.guideapi.button.ButtonSearch;
import de.maxanier.guideapi.network.PacketHandler;
import de.maxanier.guideapi.network.PacketSyncHome;
import de.maxanier.guideapi.wrapper.CategoryWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
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
        super(book.getTitle(), player, bookStack);
        this.book = book;
        this.pageTexture = book.getPageTexture();
        this.outlineTexture = book.getOutlineTexture();
        this.categoryPage = 0;
    }

    @Override
    public void func_230430_a_(MatrixStack stack, int mouseX, int mouseY, float renderPartialTicks) {
        field_230706_i_.getTextureManager().bindTexture(pageTexture);
        func_238474_b_(stack, guiLeft, guiTop, 0, 0, xSize, ySize);
        field_230706_i_.getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(guiLeft, guiTop, 0, 0, xSize, ySize, book.getColor());
        func_238471_a_(stack, field_230712_o_, TextHelper.localize(book.getHeader()).replace("\\n", "\n").replace("&", "\u00a7"), guiLeft + xSize / 2 + 1, guiTop + 15, 0);

        categoryPage = MathHelper.clamp(categoryPage, 0, categoryWrapperMap.size() - 1);

        for (CategoryWrapper wrapper : this.categoryWrapperMap.get(categoryPage))
            if (wrapper.canPlayerSee())
                wrapper.draw(stack, mouseX, mouseY, this);

        for (CategoryWrapper wrapper : this.categoryWrapperMap.get(categoryPage))
            if (wrapper.canPlayerSee())
                wrapper.drawExtras(stack, mouseX, mouseY, this);

        drawCenteredStringWithoutShadow(stack, field_230712_o_, String.format("%d/%d", categoryPage + 1, categoryWrapperMap.asMap().size()), guiLeft + xSize / 2, guiTop + 5 * ySize / 6, 0);
        func_238472_a_(stack, field_230712_o_, book.getTitle(), guiLeft + xSize / 2, guiTop - 10, Color.WHITE.getRGB());

        buttonPrev.field_230694_p_ = categoryPage != 0;
        buttonNext.field_230694_p_ = categoryPage != categoryWrapperMap.asMap().size() - 1 && !categoryWrapperMap.asMap().isEmpty();

        super.func_230430_a_(stack, mouseX, mouseY, renderPartialTicks);
    }

    @Override
    public boolean func_231043_a_(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double movement) {

        if (movement < 0)
            nextPage();
        else if (movement > 0)
            prevPage();

        return movement != 0 || super.func_231043_a_(p_mouseScrolled_1_, p_mouseScrolled_3_, movement);
    }

    @Override
    public boolean func_231044_a_(double mouseX, double mouseY, int typeofClick) {
        if (!super.func_231044_a_(mouseX, mouseY, typeofClick)) {
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
    public boolean func_231046_a_(int keyCode, int p_keyPressed_2_, int p_keyPressed_3_) {
        if ((keyCode == GLFW.GLFW_KEY_UP || keyCode == GLFW.GLFW_KEY_RIGHT) && categoryPage + 1 < categoryWrapperMap.asMap().size()) {
            nextPage();
        } else if ((keyCode == GLFW.GLFW_KEY_DOWN || keyCode == GLFW.GLFW_KEY_LEFT) && categoryPage > 0) {
            prevPage();
        }

        return super.func_231046_a_(keyCode, p_keyPressed_2_, p_keyPressed_3_);
    }

    @Override
    public void func_231160_c_() {
        this.categoryWrapperMap.clear();

        guiLeft = (this.field_230708_k_ - this.xSize) / 2;
        guiTop = (this.field_230709_l_ - this.ySize) / 2;

        func_230480_a_(buttonNext = new ButtonNext(guiLeft + 4 * xSize / 6, guiTop + 5 * ySize / 6, (btn) -> {
            if (categoryPage + 1 < categoryWrapperMap.asMap().size()) {
                nextPage();
            }
        }, this));
        func_230480_a_(buttonPrev = new ButtonPrev(guiLeft + xSize / 5, guiTop + 5 * ySize / 6, (btn) -> {
            if (categoryPage > 0) {
                prevPage();
            }
        }, this));
        func_230480_a_(buttonSearch = new ButtonSearch((guiLeft + xSize / 6) - 25, guiTop + 5, (btn) -> {
            field_230706_i_.displayGuiScreen(new SearchScreen(book, player, bookStack, this));
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
            categoryWrapperMap.put(pageNumber, new CategoryWrapper(book, category, cX + x * 27, cY + y * 30, 23, 23, player, this.field_230712_o_, field_230707_j_, false, bookStack));
            i++;

            if (i >= 20) {
                i = 0;
                pageNumber++;
            }
        }
    }

    @Override
    public void func_231175_as__() {
        super.func_231175_as__();

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
