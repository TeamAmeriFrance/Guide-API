package de.maxanier.guideapi.api;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.impl.Book;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Used to render information on screen about a block while a player is looking at and holding the guide. This is only
 * called on the client.
 * <p>
 * Use {@link GuideAPI#registerInfoRenderer(Book, IInfoRenderer, net.minecraft.block.Block...)} to register your
 * handler. You can also implement {@link Block} on a block.
 * <p>
 * Some example usages can be found in {@link de.maxanier.guideapi.info}
 * <p>
 * You can display recipes, information about what a block does, etc
 */
public interface IInfoRenderer {

    /**
     * Draws information on screen while the player is holding the guide and looking at the block.
     *
     * @param book     - The book this instance belongs to
     * @param world    - The current world
     * @param pos      - The position of the block being looked at
     * @param state    - The current state of the block
     * @param rayTrace - A RayTraceResult containing data about the block currently looked at
     * @param player   - The player looking at the block
     */
    void drawInformation(MatrixStack stack, Book book, World world, BlockPos pos, BlockState state, RayTraceResult rayTrace, PlayerEntity player);

    /**
     * You can implement this in your block. However, this of course creates a hard dependency on GuideAPI
     */
    interface Block {

        /**
         * Gets an IInfoRenderer for a block. Make sure that the book is yours.
         *
         * @param book     - The book this instance belongs to
         * @param world    - The current world
         * @param pos      - The position of the block being looked at
         * @param state    - The current state of the block
         * @param rayTrace - A RayTraceResult containing data about the block currently looked at
         * @param player   - The player looking at the block
         * @return an IInfoRenderer for this block. If no IInfoRenderer is needed, return null.
         */
        @Nullable
        IInfoRenderer getInfoRenderer(Book book, World world, BlockPos pos, BlockState state, RayTraceResult rayTrace, PlayerEntity player);

        /**
         * @return returns the book required to display information.
         */
        @Nonnull
        Book getBook();
    }
}
