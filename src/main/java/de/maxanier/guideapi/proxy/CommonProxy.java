package de.maxanier.guideapi.proxy;

import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CommonProxy {


    public void initColors() {
    }

    public void openEntry(Book book, CategoryAbstract categoryAbstract, EntryAbstract entryAbstract, Player player, ItemStack stack) {
    }

    public void openGuidebook(Player player, Level world, Book book, ItemStack bookStack) {
    }

    public void playSound(SoundEvent sound) {
    }
}
