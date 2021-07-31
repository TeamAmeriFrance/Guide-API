package de.maxanier.guideapi.api.util;


import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Random;


/**
 * Allows convenient consistent cycling through all matching stacks of an ingredient
 */
public class IngredientCycler {

    private final Random rand = new Random();
    private long lastCycle = -1;
    private int cycleIdx = 0;

    /**
     * Retrieves a itemstack that matches the ingredient.
     * Cycles though all matching stacks.
     * Must call {@link IngredientCycler#tick(Minecraft)} before (e.g. once per onDraw)
     *
     * @param ingredient The ingredient
     * @param index      An "unique" id for this ingredient, so multiple ingredients can be cycled independently
     * @return Optional. Can be empty if ingredient is invalid and has no matching stacks
     */
    public Optional<ItemStack> getCycledIngredientStack(@Nonnull Ingredient ingredient, int index) {
        ItemStack[] itemStacks = ingredient.getItems();
        if (itemStacks.length > 0) {
            rand.setSeed(index);
            int id = (index + rand.nextInt(itemStacks.length) + cycleIdx) % itemStacks.length;
            return Optional.of(itemStacks[id]);
        }
        return Optional.empty();
    }

    public void tick(@Nonnull Minecraft mc) {
        long time = mc.level != null ? mc.level.getGameTime() : 0;
        if (lastCycle < 0 || lastCycle < time - 20) {
            if (lastCycle > 0) {
                cycleIdx++;
                cycleIdx = Math.max(0, cycleIdx);
            }
            lastCycle = time;
        }
    }
}
