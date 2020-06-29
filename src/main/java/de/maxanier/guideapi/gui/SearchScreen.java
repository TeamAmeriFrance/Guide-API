package de.maxanier.guideapi.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.button.ButtonBack;
import de.maxanier.guideapi.button.ButtonNext;
import de.maxanier.guideapi.button.ButtonPrev;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class SearchScreen extends BaseScreen {

    @Nonnull
    static List<List<Pair<EntryAbstract, CategoryAbstract>>> getMatches(Book book, @Nullable String query, PlayerEntity player, ItemStack bookStack) {
        List<Pair<EntryAbstract, CategoryAbstract>> discovered = Lists.newArrayList();

        for (CategoryAbstract category : book.getCategoryList()) {
            if (!category.canSee(player, bookStack))
                continue;

            for (EntryAbstract entry : category.entries.values()) {
                if (!entry.canSee(player, bookStack))
                    continue;

                if (Strings.isNullOrEmpty(query) || entry.getName().getString().toLowerCase(Locale.ENGLISH).contains(query.toLowerCase(Locale.ENGLISH)))
                    discovered.add(Pair.of(entry, category));
            }
        }

        return Lists.partition(discovered, 10);
    }

    private final Book book;
    private final ResourceLocation outlineTexture;
    private ButtonNext buttonNext;
    private ButtonPrev buttonPrev;
    private TextFieldWidget searchField;
    private final ResourceLocation pageTexture;
    private List<List<Pair<EntryAbstract, CategoryAbstract>>> searchResults;
    private int currentPage = 0;
    private String lastQuery = "";
    private final int renderXOffset = 37;
    private final int renderYOffset = 30;
    private final Screen parent;


    public SearchScreen(Book book, PlayerEntity player, ItemStack bookStack, Screen parent) {
        super(book.getTitle(), player, bookStack);

        this.book = book;
        this.pageTexture = book.getPageTexture();
        this.outlineTexture = book.getOutlineTexture();
        this.parent = parent;
        this.searchResults = getMatches(book, null, player, bookStack);
    }

    @Override
    public void func_230430_a_(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        field_230706_i_.getTextureManager().bindTexture(pageTexture);
        func_238474_b_(stack, guiLeft, guiTop, 0, 0, xSize, ySize);
        field_230706_i_.getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(stack, guiLeft, guiTop, 0, 0, xSize, ySize, book.getColor());

        func_238467_a_(stack, searchField.field_230690_l_ - 1, searchField.field_230691_m_ - 1, searchField.field_230690_l_ + searchField.getAdjustedWidth() + 1, searchField.field_230691_m_ + searchField.getHeight() + 1, new Color(166, 166, 166, 128).getRGB());
        func_238467_a_(stack, searchField.field_230690_l_, searchField.field_230691_m_, searchField.field_230690_l_ + searchField.getAdjustedWidth(), searchField.field_230691_m_ + searchField.getHeight(), new Color(58, 58, 58, 128).getRGB());
        searchField.func_230430_a_(stack, mouseX, mouseY, partialTicks);

        int entryX = guiLeft + renderXOffset;
        int entryY = guiTop + renderYOffset;

        if (searchResults.size() != 0 && currentPage >= 0 && currentPage < searchResults.size()) {
            List<Pair<EntryAbstract, CategoryAbstract>> pageResults = searchResults.get(currentPage);
            for (Pair<EntryAbstract, CategoryAbstract> entry : pageResults) {
                entry.getLeft().draw(stack, book, entry.getRight(), entryX, entryY, 4 * xSize / 6, 10, mouseX, mouseY, this, field_230712_o_);
                entry.getLeft().drawExtras(stack, book, entry.getRight(), entryX, entryY, 4 * xSize / 6, 10, mouseX, mouseY, this, field_230712_o_);

                if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, 4 * xSize / 6, 10)) {
                    if (GLFW.glfwGetKey(field_230706_i_.getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS)
                        GuiUtils.drawHoveringText(stack, entry.getRight().getTooltip(), mouseX, mouseY, field_230708_k_, field_230709_l_, 300, field_230712_o_);
                }

                entryY += 13;
            }
        }

        buttonPrev.field_230694_p_ = currentPage != 0;
        buttonNext.field_230694_p_ = currentPage != searchResults.size() - 1 && !searchResults.isEmpty();

        super.func_230430_a_(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean func_231042_a_(char p_charTyped_1_, int p_charTyped_2_) {
        if (this.searchField.func_231042_a_(p_charTyped_1_, p_charTyped_2_)) {
            this.updateSearch();
            return true;
        }
        return super.func_231042_a_(p_charTyped_1_, p_charTyped_2_);
    }

    @Override
    public boolean func_231043_a_(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double movement) {
        if (movement < 0 && buttonNext.field_230694_p_ && currentPage <= searchResults.size())
            currentPage++;
        else if (movement > 0 && buttonPrev.field_230694_p_ && currentPage > 0)
            currentPage--;

        return movement != 0 || super.func_231043_a_(p_mouseScrolled_1_, p_mouseScrolled_3_, movement);

    }

    @Override
    public boolean func_231044_a_(double mouseX, double mouseY, int typeofClick) {
        if (!super.func_231044_a_(mouseX, mouseY, typeofClick)) {
            if (typeofClick == 0) {
                int entryX = guiLeft + renderXOffset;
                int entryY = guiTop + renderYOffset;

                if (searchResults.size() != 0 && currentPage >= 0 && currentPage < searchResults.size()) {
                    List<Pair<EntryAbstract, CategoryAbstract>> pageResults = searchResults.get(currentPage);
                    for (Pair<EntryAbstract, CategoryAbstract> entry : pageResults) {
                        if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, 4 * xSize / 6, 10)) {
                            GuideMod.PROXY.openEntry(book, entry.getRight(), entry.getLeft(), player, bookStack);
                        }
                        entryY += 13;
                    }
                }
            } else if (typeofClick == 1) {
                if (GuiHelper.isMouseBetween(mouseX, mouseY, searchField.field_230690_l_, searchField.field_230691_m_, searchField.getAdjustedWidth(), searchField.getHeight())) {
                    searchField.setText("");
                    lastQuery = "";
                    searchResults = getMatches(book, "", player, bookStack);
                    return true;
                } else {
                    field_230706_i_.displayGuiScreen(parent);
                    return true;
                }
            }


            return searchField.func_231044_a_(mouseX, mouseY, typeofClick);
        }
        return true;


    }

    private void updateSearch() {
        if (!searchField.getText().equalsIgnoreCase(lastQuery)) {
            lastQuery = searchField.getText();
            searchResults = getMatches(book, searchField.getText(), player, bookStack);
            if (currentPage > searchResults.size())
                currentPage = searchResults.size() - 1;
        }
    }

    @Override
    public boolean func_231046_a_(int keyCode, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (!searchField.func_230999_j_()) {
            return super.func_231046_a_(keyCode, p_keyPressed_2_, p_keyPressed_3_);
        }

        if (keyCode == GLFW.GLFW_KEY_ESCAPE)
            searchField.func_231049_c__(false);

        if (searchField.func_231046_a_(keyCode, p_keyPressed_2_, p_keyPressed_3_)) {
            this.updateSearch();
        }

        return true;
    }

    @Override
    public void func_231160_c_() {

        guiLeft = (this.field_230708_k_ - this.xSize) / 2;
        guiTop = (this.field_230709_l_ - this.ySize) / 2;

        func_230480_a_(new ButtonBack(guiLeft + xSize / 6, guiTop, (btn) -> {
            field_230706_i_.displayGuiScreen(parent);

        }, this));
        func_230480_a_(buttonNext = new ButtonNext(guiLeft + 4 * xSize / 6, guiTop + 5 * ySize / 6, (btn) -> {
            if (currentPage <= searchResults.size() - 1)
                currentPage++;
        }, this));
        func_230480_a_(buttonPrev = new ButtonPrev(guiLeft + xSize / 5, guiTop + 5 * ySize / 6, (btn) -> {
            if (currentPage > 0)
                currentPage--;
        }, this));

        searchField = new TextFieldWidget(field_230712_o_, guiLeft + 43, guiTop + 12, 100, 10, new TranslationTextComponent("guideapi.button.search"));
        searchField.setEnableBackgroundDrawing(false);
        searchField.func_231049_c__(true); //changeFocus
        searchResults = getMatches(book, null, player, bookStack);
    }
}
