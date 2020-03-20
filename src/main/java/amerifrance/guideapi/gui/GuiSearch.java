package amerifrance.guideapi.gui;

import amerifrance.guideapi.GuideMod;
import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import api.util.GuiHelper;
import amerifrance.guideapi.button.ButtonBack;
import amerifrance.guideapi.button.ButtonNext;
import amerifrance.guideapi.button.ButtonPrev;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GuiSearch extends GuiBase {

    private Book book;
    private ResourceLocation outlineTexture;
    private ResourceLocation pageTexture;
    private ButtonNext buttonNext;
    private ButtonPrev buttonPrev;
    private TextFieldWidget searchField;
    private Screen parent;
    private List<List<Pair<EntryAbstract, CategoryAbstract>>> searchResults;
    private int currentPage = 0;
    private String lastQuery = "";

    public GuiSearch(Book book, PlayerEntity player, ItemStack bookStack, Screen parent) {
        super(player, bookStack);

        this.book = book;
        this.pageTexture = book.getPageTexture();
        this.outlineTexture = book.getOutlineTexture();
        this.parent = parent;
        this.searchResults = getMatches(book, null, player, bookStack);
    }

    @Override
    public void initGui() {
        buttonList.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        addButton(new ButtonBack(0, guiLeft + xSize / 6, guiTop, this));
        addButton(buttonNext = new ButtonNext(1, guiLeft + 4 * xSize / 6, guiTop + 5 * ySize / 6, this));
        addButton(buttonPrev = new ButtonPrev(2, guiLeft + xSize / 5, guiTop + 5 * ySize / 6, this));

        searchField = new TextFieldWidget(3, fontRenderer, guiLeft + 43, guiTop + 12, 100, 10);
        searchField.setEnableBackgroundDrawing(false);
        searchField.setFocused(true);
        searchResults = getMatches(book, null, player, bookStack);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(pageTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        mc.getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(guiLeft, guiTop, 0, 0, xSize, ySize, book.getColor());

        drawRect(searchField.x - 1, searchField.y - 1, searchField.x + searchField.width + 1, searchField.y + searchField.height + 1, new Color(166, 166, 166, 128).getRGB());
        drawRect(searchField.x, searchField.y, searchField.x + searchField.width, searchField.y + searchField.height, new Color(58, 58, 58, 128).getRGB());
        searchField.drawTextBox();

        int entryX = guiLeft + 37;
        int entryY = guiTop + 30;

        if (searchResults.size() != 0 && currentPage >= 0 && currentPage < searchResults.size()) {
            List<Pair<EntryAbstract, CategoryAbstract>> pageResults = searchResults.get(currentPage);
            for (Pair<EntryAbstract, CategoryAbstract> entry : pageResults) {
                entry.getLeft().draw(book, entry.getRight(), entryX, entryY, 4 * xSize / 6, 10, mouseX, mouseY, this, fontRenderer);
                entry.getLeft().drawExtras(book, entry.getRight(), entryX, entryY, 4 * xSize / 6, 10, mouseX, mouseY, this, fontRenderer);

                if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, 4 * xSize / 6, 10)) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                        GuiUtils.drawHoveringText(Lists.newArrayList(entry.getRight().getLocalizedName()), mouseX, mouseY, width, height, 300, fontRenderer);

                    if (Mouse.isButtonDown(0)) {
                        GuideMod.PROXY.openEntry(book, entry.getRight(), entry.getLeft(), player, bookStack);
                        return;
                    }
                }

                entryY += 13;
            }
        }

        buttonPrev.visible = currentPage != 0;
        buttonNext.visible = currentPage != searchResults.size() - 1 && !searchResults.isEmpty();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 1) {
            if (GuiHelper.isMouseBetween(mouseX, mouseY, searchField.x, searchField.y, searchField.width, searchField.height)) {
                searchField.setText("");
                lastQuery = "";
                searchResults = getMatches(book, "", player, bookStack);
                return;
            } else
                mc.displayGuiScreen(parent);
        }

        searchField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        int movement = Mouse.getEventDWheel();
        if (movement < 0 && buttonNext.visible && currentPage <= searchResults.size())
            currentPage++;
        else if (movement > 0 && buttonPrev.visible && currentPage > 0)
            currentPage--;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (!searchField.isFocused())
            super.keyTyped(typedChar, keyCode);

        if (keyCode == Keyboard.KEY_ESCAPE)
            searchField.setFocused(false);

        searchField.textboxKeyTyped(typedChar, keyCode);
        if (!searchField.getText().equalsIgnoreCase(lastQuery)) {
            lastQuery = searchField.getText();
            searchResults = getMatches(book, searchField.getText(), player, bookStack);
            if (currentPage > searchResults.size())
                currentPage = searchResults.size() - 1;
        }
    }

    @Override
    public void actionPerformed(Button button) {
        switch (button.id) {
            case 0: {
                mc.displayGuiScreen(parent);
                break;
            }
            case 1: {
                if (currentPage <= searchResults.size() - 1)
                    currentPage++;
                break;
            }
            case 2: {
                if (currentPage > 0)
                    currentPage--;
                break;
            }
        }
    }

    @Nonnull
    static List<List<Pair<EntryAbstract, CategoryAbstract>>> getMatches(Book book, @Nullable String query, PlayerEntity player, ItemStack bookStack) {
        List<Pair<EntryAbstract, CategoryAbstract>> discovered = Lists.newArrayList();

        for (CategoryAbstract category : book.getCategoryList()) {
            if (!category.canSee(player, bookStack))
                continue;

            for (EntryAbstract entry : category.entries.values()) {
                if (!entry.canSee(player, bookStack))
                    continue;

                if (Strings.isNullOrEmpty(query) || entry.getLocalizedName().toLowerCase(Locale.ENGLISH).contains(query.toLowerCase(Locale.ENGLISH)))
                    discovered.add(Pair.of(entry, category));
            }
        }

        return Lists.partition(discovered, 10);
    }
}
