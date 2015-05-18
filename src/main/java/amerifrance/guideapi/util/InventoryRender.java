package amerifrance.guideapi.util;

import amerifrance.guideapi.ModInformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryRender {

    public static void inventoryItemRender(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(ModInformation.TEXLOC + item.getClass().getSimpleName(), "inventory"));
    }

    public static void inventoryItemRender(Item item, int meta, String name) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(ModInformation.TEXLOC + name, "inventory"));
    }

    public static void inventoryItemRenderAll(Item item) {
        final Item renderItem = item;

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(ModInformation.TEXLOC + renderItem.getClass().getSimpleName(), "inventory");
            }
        });
    }
}
