package amerifrance.guideapi.gui;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.Category;
import amerifrance.guideapi.wrappers.CategoryWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiHome extends GuiBase {

    public ResourceLocation texture;
    public Book book;
    public List<CategoryWrapper> categoryWrappers = new ArrayList<CategoryWrapper>();

    public GuiHome(Book book, EntityPlayer player) {
        super(player);
        this.texture = new ResourceLocation(ModInformation.GUITEXLOC + "default_home");
        this.book = book;
    }

    public GuiHome(ResourceLocation texture, Book book, EntityPlayer player) {
        super(player);
        this.texture = texture;
        this.book = book;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.categoryWrappers.clear();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        int cX = guiLeft + 0;
        int cY = guiTop + 5;
        for (Category category : book.categories()) {
            categoryWrappers.add(new CategoryWrapper(book, category, cX, cY, 15, 15, player, this.fontRendererObj, this.itemRender));
            cY += 15;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        super.drawScreen(mouseX, mouseY, renderPartialTicks);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.canPlayerSee()) {
                wrapper.draw();
                wrapper.drawExtras(mouseX, mouseY, this);
            }

            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee()) {
                this.drawHoveringText(wrapper.getTooltip(), mouseX, mouseY, this.fontRendererObj);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int typeOfHit) {
        for (CategoryWrapper wrapper : this.categoryWrappers) {
            if (wrapper.isMouseOnWrapper(mouseX, mouseY) && wrapper.canPlayerSee(player)) {
                wrapper.onClicked();
            }
        }
    }
}
