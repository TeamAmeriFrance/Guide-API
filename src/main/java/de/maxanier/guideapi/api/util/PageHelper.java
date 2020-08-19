package de.maxanier.guideapi.api.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.page.PageItemStack;
import de.maxanier.guideapi.page.PageText;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

import java.util.ArrayList;
import java.util.List;

public class PageHelper {


    /**
     * Split the given text into multiple section if it does not fit one page.
     * The first page can have a different number of lines than the subsequent ones if desired
     * Insert new line characters to wrap the text to the available line width.
     *
     * @param text             Text component to process
     * @param lineWidth        Available width (pixel)
     * @param firstHeight      Available height on the first page (pixel)
     * @param subsequentHeight Available height on subsequent pages (pixel)
     * @return Each list element should be drawn on a individual page. Lines are wrapped using '\n'
     */
    public static List<ITextProperties> prepareForLongText(ITextProperties text, int lineWidth, int firstHeight, int subsequentHeight) {
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int firstCount = firstHeight / fontRenderer.FONT_HEIGHT;
        int count = subsequentHeight / fontRenderer.FONT_HEIGHT;
        List<ITextProperties> lines = new ArrayList<>(fontRenderer.func_238420_b_().func_238362_b_(text, lineWidth, Style.EMPTY));
        List<ITextProperties> pages = new ArrayList<>();

        List<ITextProperties> pageLines = lines.size() > firstCount ? lines.subList(0, firstCount) : lines;
        pages.add(combineWithNewLine(pageLines));
        pageLines.clear();
        while (lines.size() > 0) {
            pageLines = lines.size() > count ? lines.subList(0, count) : lines;
            pages.add(combineWithNewLine(pageLines));
            pageLines.clear();
        }
        return pages;
    }


    /**
     * Spread the text over multiple pages if necessary. Display ingredient at first page
     */
    public static List<IPage> pagesForLongText(ITextProperties text, Ingredient ingredient) {
        List<ITextProperties> pageText = prepareForLongText(text, 164, 81, 120);
        List<IPage> pageList = new ArrayList<>();
        for (int i = 0; i < pageText.size(); i++) {
            if (i == 0) {
                pageList.add(new PageItemStack(pageText.get(i), ingredient));
            } else {
                pageList.add(new PageText(pageText.get(i)));
            }
        }
        return pageList;
    }


    public static List<IPage> pagesForLongText(ITextProperties text) {
        List<IPage> pageList = new ArrayList<>();
        prepareForLongText(text, 164, 126, 126).forEach(t -> pageList.add(new PageText(t)));
        return pageList;
    }


    public static void drawFormattedText(MatrixStack stack, int x, int y, BaseScreen guiBase, ITextProperties toDraw) {
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

        List<IReorderingProcessor> cutLines = fontRenderer.func_238425_b_(toDraw, 170);
        for (IReorderingProcessor cut : cutLines) {
            fontRenderer.func_238422_b_(stack, cut, x, y, 0);
            y += 10;
        }

    }

    /**
     * @param text - Text
     * @param item - The item to put on the first page
     * @return a list of IPages with the text cut to fit on page
     */
    public static List<IPage> pagesForLongText(ITextProperties text, Item item) {
        return pagesForLongText(text, new ItemStack(item));
    }

    /**
     * @param text  - Text
     * @param block - The block to put on the first page
     * @return a list of IPages with the text cut to fit on page
     */
    public static List<IPage> pagesForLongText(ITextProperties text, Block block) {
        return pagesForLongText(text, new ItemStack(block));
    }

    /**
     * @param text - Text
     * @param item    - The stack to put on the first page
     * @return a list of IPages with the text cut to fit on page
     */
    public static List<IPage> pagesForLongText(ITextProperties text, ItemStack item) {
        return pagesForLongText(text, Ingredient.fromStacks(item));
    }

    /**
     * @param recipe1 - The first IRecipe to compare
     * @param recipe2 - The second IRecipe to compare
     * @return whether or not the class, size and the output of the recipes are the same
     */
    public static boolean areIRecipesEqual(IRecipe recipe1, IRecipe recipe2) {
        if (recipe1 == recipe2) return true;
        if (recipe1 == null || recipe2 == null || recipe1.getClass() != recipe2.getClass()) return false;
        if (recipe1.equals(recipe2)) return true;
        return recipe1.getRecipeOutput().isItemEqual(recipe2.getRecipeOutput());
//        if (recipe1.getRecipeSize() != recipe2.getRecipeSize()) return false;//FN was removed, there is no size now
    }


    /**
     * @param elements The list ist not used itself, but the elements are passed to the new ITextProperties
     * @return a new ITextProperties that combines the given elements with a newline in between
     */
    private static ITextProperties combineWithNewLine(List<ITextProperties> elements) {
        ITextProperties newLine = new StringTextComponent("\n");
        List<ITextProperties> copy = new ArrayList<>(elements.size() * 2);
        for (int i = 0; i < elements.size() - 1; i++) {
            copy.add(elements.get(i));
            copy.add(newLine);
        }
        copy.add(elements.get(elements.size() - 1));
        return ITextProperties.func_240654_a_(copy);
    }
}
