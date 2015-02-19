package amerifrance.guideapi.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.interfaces.IRecipeRenderer;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.EntryAbstract;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.IRecipe;

public class PageCustomIRecipe extends PageIRecipe {

    public IRecipeRenderer iRecipeRenderer;

    /**
     * @param recipe          - Recipe to draw
     * @param iRecipeRenderer - Your custom Recipe drawer
     */
    public PageCustomIRecipe(IRecipe recipe, IRecipeRenderer iRecipeRenderer) {
        super(recipe);
        this.iRecipeRenderer = iRecipeRenderer;
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
        iRecipeRenderer.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
    }

    @Override
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        super.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
        iRecipeRenderer.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
    }
}
