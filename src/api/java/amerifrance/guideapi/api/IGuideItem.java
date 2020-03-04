package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import net.minecraft.item.ItemStack;

public interface IGuideItem {

    Book getBook(ItemStack stack);
}
