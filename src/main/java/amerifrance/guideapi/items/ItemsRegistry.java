package amerifrance.guideapi.items;

import amerifrance.guideapi.api.GuideAPIItems;
import amerifrance.guideapi.util.InventoryRender;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemsRegistry {

    public static void registerItems() {
        GuideAPIItems.guideBook = new ItemGuideBook();
        GameRegistry.registerItem(GuideAPIItems.guideBook, "ItemGuideBook");
    }

    public static void registerInventoryRender() {
        InventoryRender.inventoryItemRenderAll(GuideAPIItems.guideBook);
    }
}
