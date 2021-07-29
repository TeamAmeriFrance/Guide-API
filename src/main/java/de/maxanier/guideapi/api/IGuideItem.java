package de.maxanier.guideapi.api;

import de.maxanier.guideapi.api.impl.Book;
import net.minecraft.world.item.ItemStack;

public interface IGuideItem {

    Book getBook(ItemStack stack);
}
