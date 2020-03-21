package api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.util.List;

import static com.mojang.blaze3d.platform.GlStateManager.*;


public class GuiHelper {
    private static final ItemRenderer render = Minecraft.getInstance().getItemRenderer();

    /**
     * @param mouseX - Position of the mouse on the x-axiq
     * @param mouseY - Position of the mouse on the y-axis
     * @param x      - Starting x for the rectangle
     * @param y      - Starting y for the rectangle
     * @param width  - Width of the rectangle
     * @param height - Height of the rectangle
     * @return whether or not the mouse is in the rectangle
     */
    public static boolean isMouseBetween(int mouseX, int mouseY, int x, int y, int width, int height) {
        int xSize = x + width;
        int ySize = y + height;
        return (mouseX >= x && mouseX <= xSize && mouseY >= y && mouseY <= ySize);
    }

    /**
     * @param stack - The itemstack to be drawn
     * @param x     - The position on the x-axis to draw the itemstack
     * @param y     - The position on the y-axis to draw the itemstack
     */
    public static void drawItemStack(ItemStack stack, int x, int y) {
        pushMatrix();
        enableBlend();
        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        enableRescaleNormal();
        enableDepthTest();
        render.renderItemAndEffectIntoGUI(stack, x, y);
        render.renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, stack, x, y, null);
        RenderHelper.disableStandardItemLighting();
        popMatrix();
        disableLighting();
    }

    /**
     * @param stack - The itemstack to be drawn
     * @param x     - The position on the x-axis to draw the itemstack
     * @param y     - The position on the y-axis to draw the itemstack
     * @param scale - The scale with which to draw the itemstack
     */
    public static void drawScaledItemStack(ItemStack stack, int x, int y, float scale) {
        pushMatrix();
        enableBlend();
        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        scalef(scale, scale, 1.0F);
        RenderHelper.enableGUIStandardItemLighting();
        enableRescaleNormal();
        enableDepthTest();//enableDepth?
        render.renderItemAndEffectIntoGUI(stack, (int) (x / scale), (int) (y / scale));
        RenderHelper.disableStandardItemLighting();
        popMatrix();
    }

    /**
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel -
     */
    public static void drawIconWithoutColor(int x, int y, int width, int height, float zLevel) {
        pushMatrix();
        enableBlend();
        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        disableLighting();
        enableRescaleNormal();
        enableDepthTest();
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        tessellator.getBuffer().pos(x, y + height, zLevel).tex(0D, 1D).endVertex();
        tessellator.getBuffer().pos(x + width, y + height, zLevel).tex(1D, 1D).endVertex();
        tessellator.getBuffer().pos(x + width, y, zLevel).tex(1D, 0D).endVertex();
        tessellator.getBuffer().pos(x, y, zLevel).tex(0D, 0D).endVertex();
        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
        popMatrix();
    }

    /**
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel -
     * @param color  - The color the icon will have
     */
    public static void drawIconWithColor(int x, int y, int width, int height, float zLevel, Color color) {
        pushMatrix();
        enableBlend();
        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        disableLighting();
        enableRescaleNormal();
        enableDepthTest();
        color4f((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        tessellator.getBuffer().pos(x, y + height, zLevel).tex(0D, 1D).endVertex();
        tessellator.getBuffer().pos(x + width, y + height, zLevel).tex(1D, 1D).endVertex();
        tessellator.getBuffer().pos(x + width, y, zLevel).tex(1D, 0D).endVertex();
        tessellator.getBuffer().pos(x, y, zLevel).tex(0D, 0D).endVertex();
        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
        color4f(1.0F, 1.0F, 1.0F, 1.0F);
        popMatrix();
    }

    /**
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel -
     */
    public static void drawSizedIconWithoutColor(int x, int y, int width, int height, float zLevel) {
        pushMatrix();
        enableBlend();
        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        color4f(1F, 1F, 1F, 1F);
        scaled(0.5D, 0.5D, 0.5D);
        translated(x, y, zLevel);
        RenderHelper.enableGUIStandardItemLighting();
        disableLighting();
        enableRescaleNormal();
        enableDepthTest();
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        tessellator.getBuffer().pos(x, y + height, zLevel).tex(0D, 1D).endVertex();
        tessellator.getBuffer().pos(x + width, y + height, zLevel).tex(1D, 1D).endVertex();
        tessellator.getBuffer().pos(x + width, y, zLevel).tex(1D, 0D).endVertex();
        tessellator.getBuffer().pos(x, y, zLevel).tex(0D, 0D).endVertex();
        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
        popMatrix();
    }

    /**
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param color  - The color the icon will have
     */
    public static void drawSizedIconWithColor(int x, int y, int width, int height, float zLevel, Color color) {
        pushMatrix();
        enableBlend();
        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        scaled(0.5D, 0.5D, 0.5D);
        color4f((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
        translated(x, y, zLevel);
        RenderHelper.enableGUIStandardItemLighting();
        disableLighting();
        enableRescaleNormal();
        enableDepthTest();
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        tessellator.getBuffer().pos(x, y + height, zLevel).tex(0D, 1D).endVertex();
        tessellator.getBuffer().pos(x + width, y + height, zLevel).tex(1D, 1D).endVertex();
        tessellator.getBuffer().pos(x + width, y, zLevel).tex(1D, 0D).endVertex();
        tessellator.getBuffer().pos(x, y, zLevel).tex(0D, 0D).endVertex();
        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
        popMatrix();
    }

    @SuppressWarnings("unchecked")
    public static List<ITextComponent> getTooltip(ItemStack stack) {
        Minecraft mc = Minecraft.getInstance();
        List<ITextComponent> list = stack.getTooltip(mc.player, mc.gameSettings.advancedItemTooltips ? TooltipFlags.ADVANCED : TooltipFlags.NORMAL);
        for (int k = 0; k < list.size(); ++k) {
            if (k == 0) {
                list.get(k).applyTextStyle(stack.getRarity().color);
            } else {
                list.get(k).applyTextStyle(TextFormatting.GRAY);
            }
        }
        return list;
    }
}
