package amerifrance.guideapi.util;

import amerifrance.guideapi.objects.abstraction.IPage;
import amerifrance.guideapi.pages.PageLocItemStack;
import amerifrance.guideapi.pages.PageLocText;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;
import java.util.List;

public class PageHelper {

    public static List<IPage> pagesForLongText(String locText, FontRenderer fontRenderer) {
        List<IPage> pageList = new ArrayList<IPage>();
        List<String> stringList = fontRenderer.listFormattedStringToWidth(locText, 2550);
        for (String s : stringList) pageList.add(new PageLocText(s));
        return pageList;
    }

    public static List<IPage> pagesForLongText(String locText, FontRenderer fontRenderer, ItemStack stack) {
        List<IPage> pageList = new ArrayList<IPage>();
        List<String> stringList = fontRenderer.listFormattedStringToWidth(locText, 2550);
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) pageList.add(new PageLocItemStack(stringList.get(i), stack));
            else pageList.add(new PageLocText(stringList.get(i)));
        }
        return pageList;
    }

    public static boolean areIRecipesEqual(IRecipe recipe1, IRecipe recipe2) {
        if (recipe1 == recipe2) return true;
        if (recipe1 == null || recipe2 == null || recipe1.getClass() != recipe2.getClass()) return false;
        if (recipe1.equals(recipe2)) return true;
        if (!recipe1.getRecipeOutput().isItemEqual(recipe2.getRecipeOutput())) return false;
        if (recipe1.getRecipeSize() != recipe2.getRecipeSize()) return false;
        return true;
    }
}
