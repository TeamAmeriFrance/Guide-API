package amerifrance.guideapi.items;

import amerifrance.guideapi.api.GuideAPIItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemsRegistry {

    public static void registerItems() {
        GuideAPIItems.guideBook = new ItemGuideBook();
        GameRegistry.registerItem(GuideAPIItems.guideBook, "ItemGuideBook");

        GuideAPIItems.lostPage = new ItemLostPage();
        GameRegistry.registerItem(GuideAPIItems.lostPage, "ItemLostPage");
    }
}
