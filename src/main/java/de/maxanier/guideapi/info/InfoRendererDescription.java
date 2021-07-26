package de.maxanier.guideapi.info;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.IInfoRenderer;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.util.GuiHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.awt.*;
import java.util.List;

public class InfoRendererDescription implements IInfoRenderer {

    private final ItemStack itemStack;
    private final ITextComponent description;
    private boolean tiny;
    private int yOffset;

    public InfoRendererDescription(ItemStack stack, ITextComponent description) {
        this.itemStack = stack;
        this.description = description;
    }

    @Override
    public void drawInformation(MatrixStack stack, Book book, World world, BlockPos pos, BlockState state, RayTraceResult rayTrace, PlayerEntity player) {
        if (tiny) {
            stack.pushPose();
            stack.scale(0.5F, 0.5F, 0.5F);
        }
        FontRenderer fontRenderer = Minecraft.getInstance().font;
        int scaleMulti = tiny ? 2 : 1;

        GuiHelper.drawItemStack(stack, itemStack, (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 55) * scaleMulti, ((Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - (tiny ? 20 : 30)) + yOffset) * scaleMulti);

        int y = 0;

        List<IReorderingProcessor> cutLines = fontRenderer.split(description, 100 * scaleMulti); //trimStringToWidth //Split at new line somehow
        for (IReorderingProcessor cut : cutLines) {
            fontRenderer.drawShadow(stack, cut, (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 20) * scaleMulti, (((Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - 10) - y) * scaleMulti) + yOffset, Color.WHITE.getRGB());
            y -= 10 / scaleMulti;
        }

        if (tiny)
            stack.popPose();
    }

    public InfoRendererDescription setTiny(boolean tiny) {
        this.tiny = tiny;
        return this;
    }

    public InfoRendererDescription setOffsetY(int yOffset) {
        this.yOffset = yOffset;
        return this;
    }
}
