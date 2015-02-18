package amerifrance.guideapi.objects.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.interfaces.ICustomIRecipeDrawingHandler;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.IRecipe;

public class PageCustomIRecipe extends PageIRecipe {

    public ICustomIRecipeDrawingHandler customIRecipeDrawingHandler;

    /**
     *
     * @param recipe - Recipe to draw
     * @param customIRecipeDrawingHandler - Your custom Recipe drawer
     */
    public PageCustomIRecipe(IRecipe recipe, ICustomIRecipeDrawingHandler customIRecipeDrawingHandler) {
        super(recipe);
        this.customIRecipeDrawingHandler = customIRecipeDrawingHandler;
    }

    @Override
    public void draw(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
        customIRecipeDrawingHandler.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
    }

    @Override
    public void drawExtras(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        super.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
        customIRecipeDrawingHandler.drawExtras(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRenderer);
    }
}
