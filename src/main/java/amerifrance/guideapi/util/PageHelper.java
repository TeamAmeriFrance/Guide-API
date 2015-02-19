package amerifrance.guideapi.util;

import amerifrance.guideapi.objects.abstraction.PageAbstract;
import amerifrance.guideapi.pages.PageLocItemStack;
import amerifrance.guideapi.pages.PageLocText;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PageHelper {

    public static List<PageAbstract> pagesForLongText(String locText, FontRenderer fontRenderer) {
        List<PageAbstract> pageList = new ArrayList<PageAbstract>();
        List<String> stringList = fontRenderer.listFormattedStringToWidth(locText, 2550);
        for (String s : stringList) pageList.add(new PageLocText(s));
        return pageList;
    }

    public static List<PageAbstract> pagesForLongText(String locText, FontRenderer fontRenderer, ItemStack stack) {
        List<PageAbstract> pageList = new ArrayList<PageAbstract>();
        List<String> stringList = fontRenderer.listFormattedStringToWidth(locText, 2550);
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) pageList.add(new PageLocItemStack(stringList.get(i), stack));
            else pageList.add(new PageLocText(stringList.get(i)));
        }
        return pageList;
    }
}
