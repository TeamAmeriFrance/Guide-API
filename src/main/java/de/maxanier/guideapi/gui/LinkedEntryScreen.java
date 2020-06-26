package de.maxanier.guideapi.gui;

import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.button.ButtonBack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * Simple GuiEntry which back button leads to a previous entry and not to the category page
 */
public class LinkedEntryScreen extends EntryScreen {

    private final EntryAbstract from;
    private final int fromPage;

    public LinkedEntryScreen(Book book, CategoryAbstract category, EntryAbstract entry, PlayerEntity player, ItemStack bookStack, EntryAbstract from, int fromPage) {
        super(book, category, entry, player, bookStack);
        this.from = from;
        this.fromPage = fromPage;
    }

    @Override
    public void func_231160_c_() {
        super.func_231160_c_();
        this.field_230710_m_.remove(this.buttonBack); //buttons
        this.field_230705_e_.remove(this.buttonBack); //children
        this.func_230480_a_(this.buttonBack = new ButtonBack(this.guiLeft + this.xSize / 6, this.guiTop, (btn) -> {
            EntryScreen e = new EntryScreen(book, category, from, player, bookStack);
            e.pageNumber = fromPage;
            this.field_230706_i_.displayGuiScreen(e);
        }, this));
    }
}
