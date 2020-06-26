package de.maxanier.guideapi.gui;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.button.ButtonBack;
import de.maxanier.guideapi.button.ButtonNext;
import de.maxanier.guideapi.button.ButtonPrev;
import de.maxanier.guideapi.button.ButtonSearch;
import de.maxanier.guideapi.network.PacketHandler;
import de.maxanier.guideapi.network.PacketSyncCategory;
import de.maxanier.guideapi.wrapper.EntryWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class CategoryScreen extends BaseScreen {

    public ResourceLocation outlineTexture;
    public ResourceLocation pageTexture;
    public Book book;
    public CategoryAbstract category;
    public HashMultimap<Integer, EntryWrapper> entryWrapperMap = HashMultimap.create();
    public ButtonBack buttonBack;
    public ButtonNext buttonNext;
    public ButtonPrev buttonPrev;
    public ButtonSearch buttonSearch;
    public int entryPage;
    @Nullable
    public EntryAbstract startEntry;

    public CategoryScreen(Book book, CategoryAbstract category, PlayerEntity player, ItemStack bookStack, @Nullable EntryAbstract startEntry) {
        super(new TranslationTextComponent(category.name), player, bookStack);
        this.book = book;
        this.category = category;
        this.pageTexture = book.getPageTexture();
        this.outlineTexture = book.getOutlineTexture();
        this.entryPage = 0;
        this.startEntry = startEntry;
    }

    @Override
    public void func_230430_a_(MatrixStack stack, int mouseX, int mouseY, float renderPartialTicks) { //render
        field_230706_i_.getTextureManager().bindTexture(pageTexture); //minecraft
        func_238474_b_(stack, guiLeft, guiTop, 0, 0, xSize, ySize);
        field_230706_i_.getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(stack, guiLeft, guiTop, 0, 0, xSize, ySize, book.getColor());

        entryPage = MathHelper.clamp(entryPage, 0, entryWrapperMap.size() - 1);

        for (EntryWrapper wrapper : this.entryWrapperMap.get(entryPage)) {
            if (wrapper.canPlayerSee()) {
                wrapper.draw(stack, mouseX, mouseY, this);
                wrapper.drawExtras(stack, mouseX, mouseY, this);
            }
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                wrapper.onHoverOver(mouseX, mouseY);
            }
        }

        drawCenteredStringWithoutShadow(stack, field_230712_o_ /*fontRenderer*/, String.format("%d/%d", entryPage + 1, entryWrapperMap.asMap().size()), guiLeft + xSize / 2, guiTop + 5 * ySize / 6, 0);
        func_238472_a_(stack, field_230712_o_, category.getName(), guiLeft + xSize / 2, guiTop - 10, Color.WHITE.getRGB());

        buttonPrev.field_230694_p_ = entryPage != 0; //visible
        buttonNext.field_230694_p_ = entryPage != entryWrapperMap.asMap().size() - 1 && !entryWrapperMap.asMap().isEmpty();

        super.func_230430_a_(stack, mouseX, mouseY, renderPartialTicks);
    }

    @Override
    public boolean func_231043_a_(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double movement) { //mouseScrolled
        if (movement < 0)
            nextPage();
        else if (movement > 0)
            prevPage();

        return movement != 0 || super.func_231043_a_(p_mouseScrolled_1_, p_mouseScrolled_3_, movement);
    }

    @Override
    public boolean func_231044_a_(double mouseX, double mouseY, int typeofClick) { //mouseClicked
        boolean ret = super.func_231044_a_(mouseX, mouseY, typeofClick);

        for (EntryWrapper wrapper : this.entryWrapperMap.get(entryPage)) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                if (typeofClick == 0) wrapper.entry.onLeftClicked(book, category, mouseX, mouseY, player, this);
                else if (typeofClick == 1)
                    wrapper.entry.onRightClicked(book, category, mouseX, mouseY, player, this);
            }
        }

        if (typeofClick == 1)
            this.field_230706_i_.displayGuiScreen(new HomeScreen(book, player, bookStack)); //minecraft
        return ret;
    }

    @Override
    public boolean func_231046_a_(int keyCode, int p_keyPressed_2_, int p_keyPressed_3_) { //keyPressed
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE || keyCode == this.field_230706_i_.gameSettings.keyBindUseItem.getKey().getKeyCode()) { //minecraft
            this.field_230706_i_.displayGuiScreen(new HomeScreen(book, player, bookStack));
            return true;
        } else if ((keyCode == GLFW.GLFW_KEY_UP || keyCode == GLFW.GLFW_KEY_RIGHT) && entryPage + 1 < entryWrapperMap.asMap().size()) {


            nextPage();
            return true;
        } else if ((keyCode == GLFW.GLFW_KEY_DOWN || keyCode == GLFW.GLFW_KEY_LEFT) && entryPage > 0) {
            prevPage();
            return true;
        }
        return super.func_231046_a_(keyCode, p_keyPressed_2_, p_keyPressed_3_);
    }

    @Override
    public void func_231160_c_() { //Init
        this.entryWrapperMap.clear();

        guiLeft = (this.field_230708_k_ - this.xSize) / 2; //width
        guiTop = (this.field_230709_l_ - this.ySize) / 2; //Height

        //addButton
        func_230480_a_(buttonBack = new ButtonBack(guiLeft + xSize / 6, guiTop, (btn) -> {
            this.field_230706_i_.displayGuiScreen(new HomeScreen(book, player, bookStack)); //minecraft
        }, this));
        func_230480_a_(buttonNext = new ButtonNext(guiLeft + 4 * xSize / 6, guiTop + 5 * ySize / 6, (btn) -> {
            if (entryPage + 1 < entryWrapperMap.asMap().size()) {
                nextPage();
            }
        }, this));
        func_230480_a_(buttonPrev = new ButtonPrev(guiLeft + xSize / 5, guiTop + 5 * ySize / 6, (btn) -> {
            if (entryPage > 0) {
                prevPage();
            }
        }, this));
        func_230480_a_(buttonSearch = new ButtonSearch((guiLeft + xSize / 6) - 25, guiTop + 5, (btn) -> {
            this.field_230706_i_.displayGuiScreen(new SearchScreen(book, player, bookStack, this));
        }, this));

        int eX = guiLeft + 37;
        int eY = guiTop + 15;
        int i = 0;
        int pageNumber = 0;
        List<EntryAbstract> entries = Lists.newArrayList(category.entries.values());
        for (EntryAbstract entry : entries) {
            entry.onInit(book, category, this, player, bookStack);
            entryWrapperMap.put(pageNumber, new EntryWrapper(this, book, category, entry, eX, eY, 4 * xSize / 6, 10, player, this.field_230712_o_, bookStack));
            if (entry.equals(this.startEntry)) {
                this.startEntry = null;
                this.entryPage = pageNumber;
            }
            eY += 13;
            i++;

            if (i >= 11) {
                i = 0;
                eY = guiTop + 15;
                pageNumber++;
            }
        }
    }

    @Override
    public void func_231175_as__() { //onClose
        super.func_231175_as__();

        PacketHandler.INSTANCE.sendToServer(new PacketSyncCategory(book.getCategoryList().indexOf(category), entryPage));
    }

    public void nextPage() {
        if (entryPage >= entryWrapperMap.asMap().size())
            entryPage = entryWrapperMap.asMap().size() - 1;
        if (entryPage != entryWrapperMap.asMap().size() - 1 && !entryWrapperMap.asMap().isEmpty())
            entryPage++;
    }

    public void prevPage() {
        if (entryPage >= entryWrapperMap.asMap().size())
            entryPage = entryWrapperMap.asMap().size() - 1;
        if (entryPage != 0)
            entryPage--;
    }
}
