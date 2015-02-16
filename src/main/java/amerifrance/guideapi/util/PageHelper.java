package amerifrance.guideapi.util;

import amerifrance.guideapi.objects.abstraction.AbstractPage;
import amerifrance.guideapi.objects.pages.PageLocItemStack;
import amerifrance.guideapi.objects.pages.PageLocText;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PageHelper {

    public static List<AbstractPage> pagesForLongText(String locText, FontRenderer fontRenderer) {
        List<AbstractPage> pageList = new ArrayList<AbstractPage>();
        List<String> stringList = fontRenderer.listFormattedStringToWidth(locText, 2550);
        for (String s : stringList) pageList.add(new PageLocText(s));
        return pageList;
    }

    public static List<AbstractPage> pagesForLongText(String locText, FontRenderer fontRenderer, ItemStack stack) {
        List<AbstractPage> pageList = new ArrayList<AbstractPage>();
        List<String> stringList = fontRenderer.listFormattedStringToWidth(locText, 2550);
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) pageList.add(new PageLocItemStack(stringList.get(i), stack));
            else pageList.add(new PageLocText(stringList.get(i)));
        }
        return pageList;
    }
}
