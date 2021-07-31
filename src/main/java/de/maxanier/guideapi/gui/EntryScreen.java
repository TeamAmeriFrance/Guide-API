package de.maxanier.guideapi.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

    public EntryScreen(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack) {
        super(entry.name, player, bookStack);
        this.book = book;
        this.category = category;
        this.entry = entry;
        this.pageTexture = book.getPageTexture();
        this.outlineTexture = book.getOutlineTexture();
        this.pageNumber = 0;
    }


    @Override
    public void init() {
        super.init();
        entry.onInit(book, category, null, player, bookStack);
        this.pageWrapperList.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        addRenderableWidget(buttonBack = new ButtonBack(guiLeft + xSize / 6, guiTop, (btn) -> {
            this.minecraft.setScreen(new CategoryScreen(book, category, player, bookStack, entry));

        }, this));
        addRenderableWidget(buttonNext = new ButtonNext(guiLeft + 4 * xSize / 6, guiTop + 5 * ySize / 6, (btn) -> {
            if (pageNumber + 1 < pageWrapperList.size()) {
                nextPage();
            }
        }, this));
        addRenderableWidget(buttonPrev = new ButtonPrev(guiLeft + xSize / 5, guiTop + 5 * ySize / 6, (btn) -> {
            if (pageNumber > 0) {
                prevPage();
            }
        }, this));
        addRenderableWidget(buttonSearch = new ButtonSearch((guiLeft + xSize / 6) - 25, guiTop + 5, (btn) -> {
            this.minecraft.setScreen(new SearchScreen(book, player, bookStack, this));
        }, this));

        for (IPage page : this.entry.pageList) {
            page.onInit(book, category, entry, player, bookStack, this);
            pageWrapperList.add(new PageWrapper(this, book, category, entry, page, guiLeft, guiTop, player, this.font, bookStack));
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE || keyCode == this.minecraft.options.keyUse.getKey().getValue()) {
            this.minecraft.setScreen(new CategoryScreen(book, category, player, bookStack, entry));
            return true;
        } else if ((keyCode == GLFW.GLFW_KEY_UP || keyCode == GLFW.GLFW_KEY_RIGHT) && pageNumber + 1 < pageWrapperList.size()) {
            nextPage();
            return true;
        } else if ((keyCode == GLFW.GLFW_KEY_DOWN || keyCode == GLFW.GLFW_KEY_LEFT) && pageNumber > 0) {
            prevPage();
            return true;
        }
        return super.keyPressed(keyCode, p_keyPressed_2_, p_keyPressed_3_);

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int typeofClick) {
        if (!super.mouseClicked(mouseX, mouseY, typeofClick)) {
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
                this.minecraft.setScreen(new CategoryScreen(book, category, player, bookStack, entry));
                return true;
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

    public void nextPage() {
        if (pageNumber != pageWrapperList.size() - 1 && !pageWrapperList.isEmpty())
            pageNumber++;
    }

    @Override
    public void onClose() {
        super.onClose();
        for (IPage page : this.entry.pageList) {
            page.onClose();
        }
        ResourceLocation key = null;
        for (Map.Entry<ResourceLocation, EntryAbstract> mapEntry : category.entries.entrySet())
            if (mapEntry.getValue().equals(entry))
                key = mapEntry.getKey();

        if (key != null)
            PacketHandler.INSTANCE.sendToServer(new PacketSyncEntry(book.getCategoryList().indexOf(category), key, pageNumber));
    }

    public void prevPage() {
        if (pageNumber != 0)
            pageNumber--;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float renderPartialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, pageTexture);
        blit(stack, guiLeft, guiTop, 0, 0, xSize, ySize);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, outlineTexture);
        drawTexturedModalRectWithColor(stack, guiLeft, guiTop, 0, 0, xSize, ySize, book.getColor());

        pageNumber = Mth.clamp(pageNumber, 0, pageWrapperList.size() - 1);

        if (pageNumber < pageWrapperList.size()) {
            if (pageWrapperList.get(pageNumber).canPlayerSee()) {
                pageWrapperList.get(pageNumber).draw(stack, mouseX, mouseY, this);
                pageWrapperList.get(pageNumber).drawExtras(stack, mouseX, mouseY, this);
            }
        }

        drawCenteredStringWithoutShadow(stack, font, String.format("%d/%d", pageNumber + 1, pageWrapperList.size()), guiLeft + xSize / 2, guiTop + 5 * ySize / 6, 0);
        drawCenteredString(stack, font, entry.getName(), guiLeft + xSize / 2, guiTop - 10, Color.WHITE.getRGB());

        buttonPrev.visible = pageNumber != 0;
        buttonNext.visible = pageNumber != pageWrapperList.size() - 1 && !pageWrapperList.isEmpty();

        super.render(stack, mouseX, mouseY, renderPartialTicks);
    }
}
