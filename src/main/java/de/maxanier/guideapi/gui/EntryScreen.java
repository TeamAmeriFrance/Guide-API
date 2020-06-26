package de.maxanier.guideapi.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.button.ButtonBack;
import de.maxanier.guideapi.button.ButtonNext;
import de.maxanier.guideapi.button.ButtonPrev;
import de.maxanier.guideapi.button.ButtonSearch;
import de.maxanier.guideapi.network.PacketHandler;
import de.maxanier.guideapi.network.PacketSyncEntry;
import de.maxanier.guideapi.wrapper.PageWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntryScreen extends BaseScreen {

    public ResourceLocation outlineTexture;
    public ResourceLocation pageTexture;
    public Book book;
    public CategoryAbstract category;
    public EntryAbstract entry;
    public List<PageWrapper> pageWrapperList = new ArrayList<PageWrapper>();
    public ButtonBack buttonBack;
    public ButtonNext buttonNext;
    public ButtonPrev buttonPrev;
    public ButtonSearch buttonSearch;
    public int pageNumber;

    public EntryScreen(Book book, CategoryAbstract category, EntryAbstract entry, PlayerEntity player, ItemStack bookStack) {
        super(new TranslationTextComponent(entry.name), player, bookStack);
        this.book = book;
        this.category = category;
        this.entry = entry;
        this.pageTexture = book.getPageTexture();
        this.outlineTexture = book.getOutlineTexture();
        this.pageNumber = 0;
    }


    @Override
    public void func_230430_a_(MatrixStack stack, int mouseX, int mouseY, float renderPartialTicks) {
        Minecraft.getInstance().getTextureManager().bindTexture(pageTexture);
        func_238474_b_(stack, guiLeft, guiTop, 0, 0, xSize, ySize);
        Minecraft.getInstance().getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(stack, guiLeft, guiTop, 0, 0, xSize, ySize, book.getColor());

        pageNumber = MathHelper.clamp(pageNumber, 0, pageWrapperList.size() - 1);

        if (pageNumber < pageWrapperList.size()) {
            if (pageWrapperList.get(pageNumber).canPlayerSee()) {
                pageWrapperList.get(pageNumber).draw(stack, mouseX, mouseY, this);
                pageWrapperList.get(pageNumber).drawExtras(stack, mouseX, mouseY, this);
            }
        }

        drawCenteredStringWithoutShadow(stack, field_230712_o_, String.format("%d/%d", pageNumber + 1, pageWrapperList.size()), guiLeft + xSize / 2, guiTop + 5 * ySize / 6, 0);
        func_238472_a_(stack, field_230712_o_, entry.getName(), guiLeft + xSize / 2, guiTop - 10, Color.WHITE.getRGB());

        buttonPrev.field_230694_p_ = pageNumber != 0;
        buttonNext.field_230694_p_ = pageNumber != pageWrapperList.size() - 1 && !pageWrapperList.isEmpty();

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
            for (PageWrapper wrapper : this.pageWrapperList) {
                if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                    if (typeofClick == 0) {
                        pageWrapperList.get(pageNumber).page.onLeftClicked(book, category, entry, mouseX, mouseY, player, this);
                        return true;
                    }
                    if (typeofClick == 1) {
                        pageWrapperList.get(pageNumber).page.onRightClicked(book, category, entry, mouseX, mouseY, player, this);
                        return true;
                    }
                }
            }

            if (typeofClick == 1) {
                this.field_230706_i_.displayGuiScreen(new CategoryScreen(book, category, player, bookStack, entry));
                return true;
            }
            return false;
        }
        return true;

    }

    @Override
    public boolean func_231046_a_(int keyCode, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE || keyCode == this.field_230706_i_.gameSettings.keyBindUseItem.getKey().getKeyCode()) {
            this.field_230706_i_.displayGuiScreen(new CategoryScreen(book, category, player, bookStack, entry));
            return true;
        } else if ((keyCode == GLFW.GLFW_KEY_UP || keyCode == GLFW.GLFW_KEY_RIGHT) && pageNumber + 1 < pageWrapperList.size()) {
            nextPage();
            return true;
        } else if ((keyCode == GLFW.GLFW_KEY_DOWN || keyCode == GLFW.GLFW_KEY_LEFT) && pageNumber > 0) {
            prevPage();
            return true;
        }
        return super.func_231046_a_(keyCode, p_keyPressed_2_, p_keyPressed_3_);

    }

    @Override
    public void func_231160_c_() {
        super.func_231160_c_();
        entry.onInit(book, category, null, player, bookStack);
        this.pageWrapperList.clear();

        guiLeft = (this.field_230708_k_ - this.xSize) / 2;
        guiTop = (this.field_230709_l_ - this.ySize) / 2;

        func_230480_a_(buttonBack = new ButtonBack(guiLeft + xSize / 6, guiTop, (btn) -> {
            this.field_230706_i_.displayGuiScreen(new CategoryScreen(book, category, player, bookStack, entry));

        }, this));
        func_230480_a_(buttonNext = new ButtonNext(guiLeft + 4 * xSize / 6, guiTop + 5 * ySize / 6, (btn) -> {
            if (pageNumber + 1 < pageWrapperList.size()) {
                nextPage();
            }
        }, this));
        func_230480_a_(buttonPrev = new ButtonPrev(guiLeft + xSize / 5, guiTop + 5 * ySize / 6, (btn) -> {
            if (pageNumber > 0) {
                prevPage();
            }
        }, this));
        func_230480_a_(buttonSearch = new ButtonSearch((guiLeft + xSize / 6) - 25, guiTop + 5, (btn) -> {
            this.field_230706_i_.displayGuiScreen(new SearchScreen(book, player, bookStack, this));
        }, this));

        for (IPage page : this.entry.pageList) {
            page.onInit(book, category, entry, player, bookStack, this);
            pageWrapperList.add(new PageWrapper(this, book, category, entry, page, guiLeft, guiTop, player, this.field_230712_o_, bookStack));
        }
    }

    @Override
    public void func_231175_as__() {
        super.func_231175_as__();

        ResourceLocation key = null;
        for (Map.Entry<ResourceLocation, EntryAbstract> mapEntry : category.entries.entrySet())
            if (mapEntry.getValue().equals(entry))
                key = mapEntry.getKey();

        if (key != null)
            PacketHandler.INSTANCE.sendToServer(new PacketSyncEntry(book.getCategoryList().indexOf(category), key, pageNumber));
    }

    public void nextPage() {
        if (pageNumber != pageWrapperList.size() - 1 && !pageWrapperList.isEmpty())
            pageNumber++;
    }

    public void prevPage() {
        if (pageNumber != 0)
            pageNumber--;
    }
}
