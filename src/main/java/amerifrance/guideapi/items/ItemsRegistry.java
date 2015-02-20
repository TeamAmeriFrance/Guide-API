package amerifrance.guideapi.items;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemsRegistry {

    public static ItemGuideBook guideBook;

    public static void registerItems() {
        guideBook = new ItemGuideBook();
        GameRegistry.registerItem(guideBook, "ItemGuideBook");
    }
}
