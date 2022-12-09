/*
 * Minecraft Forge
 * Copyright (c) 2016-2020.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */


package de.maxanier.guideapi.util;


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.client.gui.ScreenUtils.*;


/**
 * This class provides several methods and constants used by the Config GUI classes.
 *
 * @author bspkrs
 */
public class GuiUtilsCopy {


    @Nonnull
    private static final ItemStack cachedTooltipStack = ItemStack.EMPTY;


    public static void drawHoveringText(PoseStack mStack, List<? extends Component> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, Font font) {
        drawHoveringText(mStack, textLines, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, DEFAULT_BACKGROUND_COLOR, DEFAULT_BORDER_COLOR_START, DEFAULT_BORDER_COLOR_END, font);
    }

    /**
     * Draws a tooltip box on the screen with text in it.
     * Automatically positions the box relative to the mouse to match Mojang's implementation.
     * Automatically wraps text when there is not enough space on the screen to display the text without wrapping.
     * Can have a maximum width set to avoid creating very wide tooltips.
     *
     * @param textLines        the lines of text to be drawn in a hovering tooltip box.
     * @param mouseX           the mouse X position
     * @param mouseY           the mouse Y position
     * @param screenWidth      the available screen width for the tooltip to drawn in
     * @param screenHeight     the available  screen height for the tooltip to drawn in
     * @param maxTextWidth     the maximum width of the text in the tooltip box.
     *                         Set to a negative number to have no max width.
     * @param backgroundColor  The background color of the box
     * @param borderColorStart The starting color of the box border
     * @param borderColorEnd   The ending color of the box border. The border color will be smoothly interpolated
     *                         between the start and end values.
     * @param font             the font for drawing the text in the tooltip box
     */
    public static void drawHoveringText(PoseStack mStack, List<? extends Component> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight,
                                        int maxTextWidth, int backgroundColor, int borderColorStart, int borderColorEnd, Font font) {
        drawHoveringText(cachedTooltipStack, mStack, textLines, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, backgroundColor, borderColorStart, borderColorEnd, font);
    }

    public static void drawHoveringText(@Nonnull final ItemStack stack, PoseStack mStack, List<? extends Component> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, Font font) {
        drawHoveringText(stack, mStack, textLines, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, DEFAULT_BACKGROUND_COLOR, DEFAULT_BORDER_COLOR_START, DEFAULT_BORDER_COLOR_END, font);
    }

    /**
     * Use this version if calling from somewhere where ItemStack context is available.
     *
     * @see #drawHoveringText(MatrixStack, List, int, int, int, int, int, int, int, int, FontRenderer)
     */
    //TODO, Validate rendering is the same as the original
    public static void drawHoveringText(@Nonnull final ItemStack stack, PoseStack mStack, List<? extends Component> textLines, int mouseX, int mouseY,
                                        int screenWidth, int screenHeight, int maxTextWidth,
                                        int backgroundColor, int borderColorStart, int borderColorEnd, Font font) {
        if (!textLines.isEmpty()) {


            RenderSystem.disableDepthTest();
            int tooltipTextWidth = 0;

            for (Component textLine : textLines) {
                int textLineWidth = font.width(textLine);
                if (textLineWidth > tooltipTextWidth)
                    tooltipTextWidth = textLineWidth;
            }

            boolean needsWrap = false;

            int titleLinesCount = 1;
            int tooltipX = mouseX + 12;
            if (tooltipX + tooltipTextWidth + 4 > screenWidth) {
                tooltipX = mouseX - 16 - tooltipTextWidth;
                if (tooltipX < 4) // if the tooltip doesn't fit on the screen
                {
                    if (mouseX > screenWidth / 2)
                        tooltipTextWidth = mouseX - 12 - 8;
                    else
                        tooltipTextWidth = screenWidth - 16 - mouseX;
                    needsWrap = true;
                }
            }

            if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth) {
                tooltipTextWidth = maxTextWidth;
                needsWrap = true;
            }
            List<FormattedCharSequence> finalTexLines;
            if (needsWrap) {
                int wrappedTooltipWidth = 0;
                List<FormattedCharSequence> wrappedTextLines = new ArrayList<>();
                for (int i = 0; i < textLines.size(); i++) {
                    FormattedText textLine = textLines.get(i);
                    List<FormattedCharSequence> wrappedLine = font.split(textLine, tooltipTextWidth);
                    if (i == 0)
                        titleLinesCount = wrappedLine.size();

                    for (FormattedCharSequence line : wrappedLine) {
                        int lineWidth = font.width(line);
                        if (lineWidth > wrappedTooltipWidth)
                            wrappedTooltipWidth = lineWidth;
                        wrappedTextLines.add(line);
                    }
                }
                tooltipTextWidth = wrappedTooltipWidth;
                finalTexLines = wrappedTextLines;

                if (mouseX > screenWidth / 2)
                    tooltipX = mouseX - 16 - tooltipTextWidth;
                else
                    tooltipX = mouseX + 12;
            } else {
                finalTexLines = textLines.stream().map(t -> Language.getInstance().getVisualOrder(t)).collect(ImmutableList.toImmutableList());
            }

            int tooltipY = mouseY - 12;
            int tooltipHeight = 8;

            if (finalTexLines.size() > 1) {
                tooltipHeight += (finalTexLines.size() - 1) * 10;
                if (finalTexLines.size() > titleLinesCount)
                    tooltipHeight += 2; // gap between title lines and next lines
            }

            if (tooltipY < 4)
                tooltipY = 4;
            else if (tooltipY + tooltipHeight + 4 > screenHeight)
                tooltipY = screenHeight - tooltipHeight - 4;

            final int zLevel = 400;

            mStack.pushPose();
            Matrix4f mat = mStack.last().pose();
            //TODO, lots of unnessesary GL calls here, we can buffer all these together.
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);


            MultiBufferSource.BufferSource renderType = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            mStack.translate(0.0D, 0.0D, zLevel);

            int tooltipTop = tooltipY;

            for (int lineNumber = 0; lineNumber < finalTexLines.size(); ++lineNumber) {
                FormattedCharSequence line = finalTexLines.get(lineNumber);
                if (line != null)
                    font.drawInBatch(line, (float) tooltipX, (float) tooltipY, -1, true, mat, renderType, false, 0, 15728880);

                if (lineNumber + 1 == titleLinesCount)
                    tooltipY += 2;

                tooltipY += 10;
            }

            renderType.endBatch();
            mStack.popPose();


            RenderSystem.enableDepthTest();
        }
    }

}