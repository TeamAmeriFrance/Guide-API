package de.maxanier.guideapi.info;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.IInfoRenderer;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import java.awt.*;
import java.util.List;

public class InfoRendererDescription implements IInfoRenderer {

    private final ItemStack itemStack;
    private final Component description;
    private boolean tiny;
    private int yOffset;

    public InfoRendererDescription(ItemStack stack, Component description) {
        this.itemStack = stack;
        this.description = description;
    }

    @Override
    public void drawInformation(PoseStack stack, Book book, Level world, BlockPos pos, BlockState state, HitResult rayTrace, Player player) {
        if (tiny) {
            stack.pushPose();
            stack.scale(0.5F, 0.5F, 0.5F);
        }
        Font fontRenderer = Minecraft.getInstance().font;
        int scaleMulti = tiny ? 2 : 1;

        GuiHelper.drawItemStack(stack, itemStack, (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 55) * scaleMulti, ((Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - (tiny ? 20 : 30)) + yOffset) * scaleMulti);

        int y = 0;

        List<FormattedCharSequence> cutLines = fontRenderer.split(description, 100 * scaleMulti); //trimStringToWidth //Split at new line somehow
        for (FormattedCharSequence cut : cutLines) {
            fontRenderer.drawShadow(stack, cut, (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 20) * scaleMulti, (((Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - 10) - y) * scaleMulti) + yOffset, Color.WHITE.getRGB());
            y -= 10 / scaleMulti;
        }

        if (tiny)
            stack.popPose();
    }

    public InfoRendererDescription setOffsetY(int yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    public InfoRendererDescription setTiny(boolean tiny) {
        this.tiny = tiny;
        return this;
    }
}
