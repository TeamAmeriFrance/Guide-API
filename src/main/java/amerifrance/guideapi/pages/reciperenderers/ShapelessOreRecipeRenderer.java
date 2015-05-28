package amerifrance.guideapi.pages.reciperenderers;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.abstraction.IRecipeRenderer.RecipeRendererBase;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;

public class ShapelessOreRecipeRenderer extends RecipeRendererBase<ShapelessOreRecipe> {

	public ShapelessOreRecipeRenderer(ShapelessOreRecipe recipe) {
		super(recipe);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "recipe_elements.png"));
		guiBase.drawTexturedModalRect(guiLeft + 42, guiTop + 53, 0, 0, 105, 65);
		
		guiBase.drawCenteredString(fontRenderer, StatCollector.translateToLocal("text.shapeless.crafting"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				int i = 3 * y + x;
				if (i >= recipe.getRecipeSize()) {
				} else {
					int stackX = (x + 1) * 17 + (guiLeft + 29);
					int stackY = (y + 1) * 17 + (guiTop + 40);
					Object component = recipe.getInput().get(i);
					if (component != null) {
						if (component instanceof ItemStack) {
							GuiHelper.drawItemStack((ItemStack) component, stackX, stackY);
							if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
								tooltips = GuiHelper.getTooltip((ItemStack) component);
							}
						} else {
							// TODO: Make the page cycle through the items in the ore dict
							if (((ArrayList<ItemStack>) component).isEmpty())
								return;
							GuiHelper.drawItemStack(((ArrayList<ItemStack>) component).get(0), stackX, stackY);
							if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
								tooltips = GuiHelper.getTooltip(((ArrayList<ItemStack>) component).get(0));
							}
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
}
