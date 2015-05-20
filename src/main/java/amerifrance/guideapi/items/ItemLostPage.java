package amerifrance.guideapi.items;

import amerifrance.guideapi.GuideAPI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemLostPage extends Item {

    public ItemLostPage() {
        this.setCreativeTab(GuideAPI.tabGuide);
        this.setUnlocalizedName("LostPage");
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public static boolean bookHasPage(ItemStack bookStack, int book, int category, int entry, int pageNumber) {
        if (!bookStack.hasTagCompound())
            return false;

        return bookStack.stackTagCompound.getBoolean(book + ":" + category + ":" + entry + ":" + pageNumber);
    }
}
