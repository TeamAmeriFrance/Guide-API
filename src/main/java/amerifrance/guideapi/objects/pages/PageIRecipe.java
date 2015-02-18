package amerifrance.guideapi.objects.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import amerifrance.guideapi.util.GuiHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;

public class PageIRecipe extends PageBase {

    public IRecipe recipe;

    public PageIRecipe(IRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        if (recipe instanceof ShapedRecipes) {
            ShapedRecipes shapedRecipes = (ShapedRecipes) recipe;
            for (int y = 0; y < shapedRecipes.recipeHeight; y++) {
                for (int x = 0; x < shapedRecipes.recipeWidth; x++) {
                    int stackX = (x + 1) * 20 + (guiLeft + guiBase.xSize / 7);
                    int stackY = (y + 1) * 20 + (guiTop + guiBase.ySize / 5);
                    ItemStack stack = shapedRecipes.recipeItems[y * shapedRecipes.recipeWidth + x];
                    GuiHelper.drawItemStack(stack, stackX, stackY);
                    if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                        guiBase.renderToolTip(stack, stackX, stackY);
                    }
                }
            }
            int outputX = (5 * 20) + (guiLeft + guiBase.xSize / 7);
            int outputY = (2 * 20) + (guiTop + guiBase.xSize / 5);
            GuiHelper.drawItemStack(shapedRecipes.getRecipeOutput(), outputX, outputY);
            if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15)) {
                guiBase.renderToolTip(shapedRecipes.getRecipeOutput(), outputX, outputY);
            }
        } else if (recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe shapedOreRecipe = (ShapedOreRecipe) recipe;
            int width = (Integer) ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, shapedOreRecipe, 4);
            int height = (Integer) ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, shapedOreRecipe, 5);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int stackX = (x + 1) * 20 + (guiLeft + guiBase.xSize / 7);
                    int stackY = (y + 1) * 20 + (guiTop + guiBase.ySize / 5);
                    Object component = shapedOreRecipe.getInput()[y * width + x];
                    if (component != null) {
                        if (component instanceof ItemStack) {
                            GuiHelper.drawItemStack((ItemStack) component, stackX, stackY);
                            if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                                guiBase.renderToolTip((ItemStack) component, stackX, stackY);
                            }
                        } else {
                            //TODO: Make the page cycle through the items in the ore dict
                            if (((ArrayList<ItemStack>) component).isEmpty()) return;
                            GuiHelper.drawItemStack(((ArrayList<ItemStack>) component).get(0), stackX, stackY);
                            if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                                guiBase.renderToolTip(((ArrayList<ItemStack>) component).get(0), stackX, stackY);
                            }
                        }
                    }
                }
            }
            int outputX = (5 * 20) + (guiLeft + guiBase.xSize / 7);
            int outputY = (2 * 20) + (guiTop + guiBase.xSize / 5);
            GuiHelper.drawItemStack(shapedOreRecipe.getRecipeOutput(), outputX, outputY);
            if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15)) {
                guiBase.renderToolTip(shapedOreRecipe.getRecipeOutput(), outputX, outputY);
            }
        } else if (recipe instanceof ShapelessRecipes) {
            ShapelessRecipes shapelessRecipes = (ShapelessRecipes) recipe;
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    int i = 3 * y + x;
                    if (i >= shapelessRecipes.getRecipeSize()) {
                    } else {
                        int stackX = (x + 1) * 20 + (guiLeft + guiBase.xSize / 7);
                        int stackY = (y + 1) * 20 + (guiTop + guiBase.ySize / 5);
                        ItemStack stack = (ItemStack) shapelessRecipes.recipeItems.get(i);
                        GuiHelper.drawItemStack(stack, stackX, stackY);
                        if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                            guiBase.renderToolTip(stack, stackX, stackY);
                        }
                    }
                }
            }
            int outputX = (5 * 20) + (guiLeft + guiBase.xSize / 7);
            int outputY = (2 * 20) + (guiTop + guiBase.xSize / 5);
            GuiHelper.drawItemStack(shapelessRecipes.getRecipeOutput(), outputX, outputY);
            if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15)) {
                guiBase.renderToolTip(shapelessRecipes.getRecipeOutput(), outputX, outputY);
            }
        } else if (recipe instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe shapelessOreRecipe = (ShapelessOreRecipe) recipe;
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    int i = 3 * y + x;
                    if (i >= shapelessOreRecipe.getRecipeSize()) {
                    } else {
                        int stackX = (x + 1) * 20 + (guiLeft + guiBase.xSize / 7);
                        int stackY = (y + 1) * 20 + (guiTop + guiBase.ySize / 5);
                        Object component = shapelessOreRecipe.getInput().get(i);
                        if (component != null) {
                            if (component instanceof ItemStack) {
                                GuiHelper.drawItemStack((ItemStack) component, stackX, stackY);
                                if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                                    guiBase.renderToolTip((ItemStack) component, stackX, stackY);
                                }
                            } else {
                                //TODO: Make the page cycle through the items in the ore dict
                                if (((ArrayList<ItemStack>) component).isEmpty()) return;
                                GuiHelper.drawItemStack(((ArrayList<ItemStack>) component).get(0), stackX, stackY);
                                if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                                    guiBase.renderToolTip(((ArrayList<ItemStack>) component).get(0), stackX, stackY);
                                }
                            }
                        }
                    }
                }
                int outputX = (5 * 20) + (guiLeft + guiBase.xSize / 7);
                int outputY = (2 * 20) + (guiTop + guiBase.xSize / 5);
                GuiHelper.drawItemStack(shapelessOreRecipe.getRecipeOutput(), outputX, outputY);
                if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15)) {
                    guiBase.renderToolTip(shapelessOreRecipe.getRecipeOutput(), outputX, outputY);
                }
            }
        }
    }
}
