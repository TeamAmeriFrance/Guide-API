package amerifrance.guideapi.api.util;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.gui.BaseScreen;
import amerifrance.guideapi.page.PageItemStack;
import amerifrance.guideapi.page.PageText;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

public class PageHelper {
    


    /**
     * Split the given text into multiple section if it does not fit one page.
     * The first page can have a different number of lines than the subsequent ones if desired
     * Insert new line characters to wrap the text to the available line width.
     * @param locText Text to process
     * @param lineWidth Available width (pixel)
     * @param firstHeight Available height on the first page (pixel)
     * @param subsequentHeight Available height on subsequent pages (pixel)
     * @return Each list element should be drawn on a individual page. Lines are wrapped using '\n'
     */
    public static List<String> prepareForLongText(String locText, int lineWidth, int firstHeight, int subsequentHeight){
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int firstCount = firstHeight/fontRenderer.FONT_HEIGHT;
        int count = subsequentHeight/fontRenderer.FONT_HEIGHT;
        List<String> lines = new ArrayList<>(fontRenderer.listFormattedStringToWidth(locText,lineWidth)); //Create a new list from returned list so we can later remove items
        List<String> pages = new ArrayList<>();

        List<String> pageLines = lines.size()>firstCount ? lines.subList(0,firstCount):lines;
        pages.add(StringUtils.join( pageLines, "\n"));
        pageLines.clear();
        while(lines.size()>0){
            pageLines = lines.size()>count ? lines.subList(0,count):lines;
            pages.add(StringUtils.join(pageLines,"\n"));
            pageLines.clear();
        }
        return pages;
    }

    /**
     * Spread the text over multiple pages if necessary. Display ingredient at first page
     */
    public static List<IPage> pagesForLongText(String locText, Ingredient ingredient){
        List<String> pageText = prepareForLongText(locText,164,79,120);
        List<IPage> pageList = new ArrayList<>();
        for(int i=0;i<pageText.size();i++){
            if(i==0){
                pageList.add(new PageItemStack(pageText.get(i),ingredient));
            }
            else{
                pageList.add(new PageText(pageText.get(i)));
            }
        }
        return pageList;
    }


    public static List<IPage> pagesForLongText(String locText){
        List<IPage> pageList = new ArrayList<>();
        prepareForLongText(locText,164,120,120).forEach(t->pageList.add(new PageText(t)));
        return pageList;
    }


    public static void drawFormattedText(int x, int y, BaseScreen guiBase, String toDraw) {
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        toDraw = StringEscapeUtils.unescapeJava(toDraw).replaceAll("\\t", "     ");
        String[] lines = toDraw.split("\n");
        for (String line : lines) {
            List<String> cutLines = fontRenderer.listFormattedStringToWidth(line, 2 * guiBase.xSize / 3);
            for (String cut : cutLines) {
                fontRenderer.drawString(cut, x, y, 0);
                y += 10;
            }
        }
    }

    /**
     * @param locText - Text
     * @param item    - The item to put on the first page
     * @return a list of IPages with the text cut to fit on page
     */
    public static List<IPage> pagesForLongText(String locText, Item item) {
        return pagesForLongText(locText, new ItemStack(item));
    }

    /**
     * @param locText - Text
     * @param block   - The block to put on the first page
     * @return a list of IPages with the text cut to fit on page
     */
    public static List<IPage> pagesForLongText(String locText, Block block) {
        return pagesForLongText(locText, new ItemStack(block));
    }

    /**
     * @param locText - Text
     * @param item   - The stack to put on the first page
     * @return a list of IPages with the text cut to fit on page
     */
    public static List<IPage> pagesForLongText(String locText, ItemStack item) {
        return pagesForLongText(locText, Ingredient.fromStacks(item));
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
        if (!recipe1.getRecipeOutput().isItemEqual(recipe2.getRecipeOutput())) return false;
//        if (recipe1.getRecipeSize() != recipe2.getRecipeSize()) return false;//FN was removed, there is no size now
        return true;
    }
}
