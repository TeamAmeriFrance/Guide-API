package amerifrance.guideapi.api.abstraction;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.IRecipe;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.gui.GuiBase;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IRecipeRenderer {

    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer);

    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer);
    
	public static abstract class RecipeRendererBase<T extends IRecipe> implements IRecipeRenderer {

		protected T recipe;
		protected List<String> tooltips = Lists.newArrayList(); 

		public RecipeRendererBase(T recipe) {
			this.recipe = recipe;
		}
		
		@Override
		public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
			guiBase.func_146283_a(tooltips, mouseX, mouseY);
			tooltips.clear();
		}
	}
}
