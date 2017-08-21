package amerifrance.guideapi.gui;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.button.ButtonBack;
import amerifrance.guideapi.button.ButtonNext;
import amerifrance.guideapi.button.ButtonPrev;
import amerifrance.guideapi.network.PacketHandler;
import amerifrance.guideapi.network.PacketSyncCategory;
import amerifrance.guideapi.wrapper.EntryWrapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

public class GuiCategory extends GuiBase {

    public ResourceLocation outlineTexture;
    public ResourceLocation pageTexture;
    public Book book;
    public CategoryAbstract category;
    public HashMultimap<Integer, EntryWrapper> entryWrapperMap = HashMultimap.create();
    public ButtonBack buttonBack;
    public ButtonNext buttonNext;
    public ButtonPrev buttonPrev;
    public int entryPage;

    public GuiCategory(Book book, CategoryAbstract category, EntityPlayer player, ItemStack bookStack) {
        super(player, bookStack);
        this.book = book;
        this.category = category;
        this.pageTexture = book.getPageTexture();
        this.outlineTexture = book.getOutlineTexture();
        this.entryPage = 0;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.entryWrapperMap.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        this.buttonList.add(buttonBack = new ButtonBack(0, guiLeft + xSize / 6, guiTop, this));
        this.buttonList.add(buttonNext = new ButtonNext(1, guiLeft + 4 * xSize / 6, guiTop + 5 * ySize / 6, this));
        this.buttonList.add(buttonPrev = new ButtonPrev(2, guiLeft + xSize / 5, guiTop + 5 * ySize / 6, this));

        int eX = guiLeft + 37;
        int eY = guiTop + 15;
        int i = 0;
        int pageNumber = 0;
        List<EntryAbstract> entries = Lists.newArrayList(category.getEntries().values());
        for (EntryAbstract entry : entries) {
            entry.onInit(book, category, this, player, bookStack);
            entryWrapperMap.put(pageNumber, new EntryWrapper(this, book, category, entry, eX, eY, 4 * xSize / 6, 10, player, this.fontRenderer, bookStack));
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
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(pageTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        Minecraft.getMinecraft().getTextureManager().bindTexture(outlineTexture);
        drawTexturedModalRectWithColor(guiLeft, guiTop, 0, 0, xSize, ySize, book.getColor());

        entryPage = MathHelper.clamp(entryPage, 0, entryWrapperMap.size() - 1);

        for (EntryWrapper wrapper : this.entryWrapperMap.get(entryPage)) {
            if (wrapper.canPlayerSee()) {
                wrapper.draw(mouseX, mouseY, this);
                wrapper.drawExtras(mouseX, mouseY, this);
            }
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                wrapper.onHoverOver(mouseX, mouseY);
            }
        }

        drawCenteredString(fontRenderer, String.format("%d/%d", entryPage + 1, entryWrapperMap.asMap().size()), guiLeft + xSize / 2, guiTop + 5 * ySize / 6, 0);
        drawCenteredStringWithShadow(fontRenderer, category.getLocalizedName(), guiLeft + xSize / 2, guiTop - 10, Color.WHITE.getRGB());

        buttonPrev.visible = entryPage != 0;
        buttonNext.visible = entryPage != entryWrapperMap.asMap().size() - 1 && !entryWrapperMap.asMap().isEmpty();

        super.drawScreen(mouseX, mouseY, renderPartialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int typeofClick) throws IOException {
        super.mouseClicked(mouseX, mouseY, typeofClick);

        for (EntryWrapper wrapper : this.entryWrapperMap.get(entryPage)) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                if (typeofClick == 0) wrapper.entry.onLeftClicked(book, category, mouseX, mouseY, player, this);
                else if (typeofClick == 1)
                    wrapper.entry.onRightClicked(book, category, mouseX, mouseY, player, this);
            }
        }

        if (typeofClick == 1)
            this.mc.displayGuiScreen(new GuiHome(book, player, bookStack));
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        int movement = Mouse.getEventDWheel();
        if (movement < 0)
            nextPage();
        else if (movement > 0)
            prevPage();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_BACK || keyCode == this.mc.gameSettings.keyBindUseItem.getKeyCode())
            this.mc.displayGuiScreen(new GuiHome(book, player, bookStack));
        if ((keyCode == Keyboard.KEY_UP || keyCode == Keyboard.KEY_RIGHT) && entryPage + 1 < entryWrapperMap.asMap().size())
            nextPage();
        if ((keyCode == Keyboard.KEY_DOWN || keyCode == Keyboard.KEY_LEFT) && entryPage > 0)
            prevPage();
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 0)
            this.mc.displayGuiScreen(new GuiHome(book, player, bookStack));
        else if (button.id == 1 && entryPage + 1 < entryWrapperMap.asMap().size())
            nextPage();
        else if (button.id == 2 && entryPage > 0)
            prevPage();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

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
