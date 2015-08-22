package amerifrance.guideapi.api.util;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_RESCALE_NORMAL;

public class GuiHelper {
	
	private static final RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

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
		glPushMatrix();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		glEnable(GL_RESCALE_NORMAL);
		glEnable(GL_DEPTH_TEST);
        renderItem.func_175042_a(stack, x, y);
        renderItem.func_175042_a(stack, x, y);
		RenderHelper.disableStandardItemLighting();
		glPopMatrix();
		glDisable(GL_LIGHTING);
    }

    /**
     * @param stack - The itemstack to be drawn
     * @param x     - The position on the x-axis to draw the itemstack
     * @param y     - The position on the y-axis to draw the itemstack
     * @param scale - The scale with which to draw the itemstack
     */
    public static void drawScaledItemStack(ItemStack stack, int x, int y, float scale) {
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glScalef(scale, scale, 1.0F);
        RenderHelper.enableGUIStandardItemLighting();
		glEnable(GL_RESCALE_NORMAL);
		glEnable(GL_DEPTH_TEST);
        renderItem.func_175042_a(stack, (int) (x / scale), (int) (y / scale));
        renderItem.func_175042_a(stack, x, y);
        RenderHelper.disableStandardItemLighting();
        glPopMatrix();
    }

    /**
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel -
     */
    public static void drawIconWithoutColor(int x, int y, int width, int height, float zLevel) {
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        glEnable(GL_RESCALE_NORMAL);
        glEnable(GL_DEPTH_TEST);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().addVertexWithUV(x, y + height, zLevel, 0D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y + height, zLevel, 1D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y, zLevel, 1D, 0D);
        tessellator.getWorldRenderer().addVertexWithUV(x, y, zLevel, 0D, 0D);
        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
        glDisable(GL_LIGHTING);
        glPopMatrix();
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
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        glEnable(GL_RESCALE_NORMAL);
        glEnable(GL_DEPTH_TEST);
        glColor4f((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().addVertexWithUV(x, y + height, zLevel, 0D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y + height, zLevel, 1D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y, zLevel, 1D, 0D);
        tessellator.getWorldRenderer().addVertexWithUV(x, y, zLevel, 0D, 0D);
        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
        glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        glDisable(GL_LIGHTING);
        glPopMatrix();
    }

    /**
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel -
     */
    public static void drawSizedIconWithoutColor(int x, int y, int width, int height, float zLevel) {
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(1F, 1F, 1F, 1F);
        glScaled(0.5D, 0.5D, 0.5D);
        glTranslated(x, y, zLevel);
        RenderHelper.enableGUIStandardItemLighting();
        glEnable(GL_RESCALE_NORMAL);
        glEnable(GL_DEPTH_TEST);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().addVertexWithUV(x, y + height, zLevel, 0D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y + height, zLevel, 1D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y, zLevel, 1D, 0D);
        tessellator.getWorldRenderer().addVertexWithUV(x, y, zLevel, 0D, 0D);
        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
        glDisable(GL_LIGHTING);
        glPopMatrix();
    }

    /**
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel - Layer of the icon
     * @param color  - The color the icon will have
     */
    public static void drawSizedIconWithColor(int x, int y, int width, int height, float zLevel, Color color) {
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glScaled(0.5D, 0.5D, 0.5D);
        glColor4f((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
        glTranslated(x, y, zLevel);
        RenderHelper.enableGUIStandardItemLighting();
        glEnable(GL_RESCALE_NORMAL);
        glEnable(GL_DEPTH_TEST);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getWorldRenderer().startDrawingQuads();
        tessellator.getWorldRenderer().addVertexWithUV(x, y + height, zLevel, 0D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y + height, zLevel, 1D, 1D);
        tessellator.getWorldRenderer().addVertexWithUV(x + width, y, zLevel, 1D, 0D);
        tessellator.getWorldRenderer().addVertexWithUV(x, y, zLevel, 0D, 0D);
        tessellator.draw();
        RenderHelper.disableStandardItemLighting();
		glDisable(GL_LIGHTING);
		glPopMatrix();
	}

	@SuppressWarnings("unchecked")
	public static List<String> getTooltip(ItemStack stack) {
		Minecraft mc = Minecraft.getMinecraft();
		List<String> list = stack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);

		for (int k = 0; k < list.size(); ++k) {
			if (k == 0)
				list.set(k, stack.getRarity().rarityColor + list.get(k));
			else
				list.set(k, EnumChatFormatting.GRAY + list.get(k));
		}

		return list;
	}
}
