package amerifrance.guideapi.interfaces;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import net.minecraft.client.gui.FontRenderer;

public interface ICustomIRecipeDrawingHandler {

    public void draw(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer);

    public void drawExtras(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer);
}
