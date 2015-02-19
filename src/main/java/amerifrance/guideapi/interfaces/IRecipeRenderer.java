package amerifrance.guideapi.interfaces;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import net.minecraft.client.gui.FontRenderer;

public interface IRecipeRenderer {

    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer);

    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer);
}
