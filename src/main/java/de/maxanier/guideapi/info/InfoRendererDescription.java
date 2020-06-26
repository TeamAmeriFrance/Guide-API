package de.maxanier.guideapi.info;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.maxanier.guideapi.api.IInfoRenderer;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.util.GuiHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringEscapeUtils;

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
            RenderSystem.pushMatrix();
            RenderSystem.scalef(0.5F, 0.5F, 0.5F);
        }
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int scaleMulti = tiny ? 2 : 1;

        GuiHelper.drawItemStack(itemStack, (Minecraft.getInstance().getMainWindow().getScaledWidth() / 2 + 55) * scaleMulti, ((Minecraft.getInstance().getMainWindow().getScaledHeight() / 2 - (tiny ? 20 : 30)) + yOffset) * scaleMulti);

        int y = 0;
        String toDraw = StringEscapeUtils.unescapeJava(description.getFormattedText()).replaceAll("\\t", "     ");
        String[] lines = toDraw.split("\n");
        for (String line : lines) {
            List<ITextProperties> cutLines = fontRenderer.func_238417_a_(line, 100 * scaleMulti);
            for (ITextProperties cut : cutLines) {
                fontRenderer.func_238407_a_(stack, cut, (Minecraft.getInstance().getMainWindow().getScaledWidth() / 2 + 20) * scaleMulti, (((Minecraft.getInstance().getMainWindow().getScaledHeight() / 2 - 10) - y) * scaleMulti) + yOffset, Color.WHITE.getRGB());
                y -= 10 / scaleMulti;
            }
        }
        if (tiny)
            RenderSystem.popMatrix();
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
