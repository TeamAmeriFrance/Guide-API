package amerifrance.guideapi.item;

import amerifrance.guideapi.api.GuideAPIItems;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemsRegistry {

    public static void registerItems() {
        GuideAPIItems.guideBook = new ItemGuideBook();
        GameRegistry.registerItem(GuideAPIItems.guideBook, "ItemGuideBook");
    }
}
