package amerifrance.guideapi.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;

public class GuiHelper {

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
        return (mouseX > x && mouseX < xSize && mouseY > y && mouseY < ySize);
    }

    /**
     * @param stack - The itemstack to be drawn
     * @param x     - The position on the x-axis to draw the itemstack
     * @param y     - The position on the y-axis to draw the itemstack
     */
    public static void drawItemStack(ItemStack stack, int x, int y) {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.func_175042_a(stack, x, y);
        renderItem.func_175042_a(stack, x, y);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }

    /**
     * @param stack - The itemstack to be drawn
     * @param x     - The position on the x-axis to draw the itemstack
     * @param y     - The position on the y-axis to draw the itemstack
     * @param scale - The scale with which to draw the itemstack
     */
    public static void drawScaledItemStack(ItemStack stack, int x, int y, float scale) {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glScalef(scale, scale, 1.0F);
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.func_175042_a(stack, (int) (x / scale), (int) (y / scale));
        renderItem.func_175042_a(stack, x, y);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }

    /**
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel -
     */
    public static void drawIconWithoutColor(int x, int y, int width, int height, float zLevel) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().addVertexWithUV(x, y + height, zLevel, 0D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y + height, zLevel, 1D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y, zLevel, 1D, 0D);
        tessellator.getWorldRenderer().addVertexWithUV(x, y, zLevel, 0D, 0D);
        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
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
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().addVertexWithUV(x, y + height, zLevel, 0D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y + height, zLevel, 1D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y, zLevel, 1D, 0D);
        tessellator.getWorldRenderer().addVertexWithUV(x, y, zLevel, 0D, 0D);
        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    /**
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel -
     */
    public static void drawSizedIconWithoutColor(int x, int y, int width, int height, float zLevel) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslated(x, y, zLevel);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().addVertexWithUV(x, y + height, zLevel, 0D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y + height, zLevel, 1D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y, zLevel, 1D, 0D);
        tessellator.getWorldRenderer().addVertexWithUV(x, y, zLevel, 0D, 0D);
        tessellator.getWorldRenderer().draw();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    /**
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel
     * @param color  - The color the icon will have
     */
    public static void drawSizedIconWithColor(int x, int y, int width, int height, float zLevel, Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glColor4f((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
        GL11.glTranslated(x, y, zLevel);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().addVertexWithUV(x, y + height, zLevel, 0D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y + height, zLevel, 1D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y, zLevel, 1D, 0D);
        tessellator.getWorldRenderer().addVertexWithUV(x, y, zLevel, 0D, 0D);
        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
