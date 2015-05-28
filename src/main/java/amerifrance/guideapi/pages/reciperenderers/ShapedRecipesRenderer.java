package amerifrance.guideapi.pages.reciperenderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IRecipeRenderer.RecipeRendererBase;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;

public class ShapedRecipesRenderer extends RecipeRendererBase<ShapedRecipes> {

	public ShapedRecipesRenderer(ShapedRecipes recipe) {
		super(recipe);
	}

	@Override
	public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "recipe_elements.png"));
		guiBase.drawTexturedModalRect(guiLeft + 42, guiTop + 53, 0, 0, 105, 65);
		
		guiBase.drawCenteredString(fontRenderer, StatCollector.translateToLocal("text.shaped.crafting"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);
		for (int y = 0; y < recipe.recipeHeight; y++) {
			for (int x = 0; x < recipe.recipeWidth; x++) {
				int stackX = (x + 1) * 17 + (guiLeft + 29);
				int stackY = (y + 1) * 17 + (guiTop + 40);
				ItemStack stack = recipe.recipeItems[y * recipe.recipeWidth + x];
				if (stack != null) {
					GuiHelper.drawItemStack(stack, stackX, stackY);
					if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
						tooltips = GuiHelper.getTooltip(stack);
					}
				}
			}
		}
		int outputX = (5 * 18) + (guiLeft + guiBase.xSize / 7);
		int outputY = (2 * 18) + (guiTop + guiBase.xSize / 5);
		GuiHelper.drawItemStack(recipe.getRecipeOutput(), outputX, outputY);
		if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15)) {
			tooltips = GuiHelper.getTooltip(recipe.getRecipeOutput());
		}
	}
}
