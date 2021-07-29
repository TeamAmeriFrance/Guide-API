package de.maxanier.guideapi.api.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.world.item.TooltipFlag.Default;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;



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
    public static boolean isMouseBetween(double mouseX, double mouseY, int x, int y, int width, int height) {
        int xSize = x + width;
        int ySize = y + height;
        return (mouseX >= x && mouseX <= xSize && mouseY >= y && mouseY <= ySize);
    }

    /**
     * MatrixStack isn't used yet as vanilla ItemRenderer does not use it yet.
     *
     * @param poseStack - The itemstack to be drawn
     * @param x     - The position on the x-axis to draw the itemstack
     * @param y     - The position on the y-axis to draw the itemstack
     */
    public static void drawItemStack(PoseStack poseStack, ItemStack stack, int x, int y) {
        PoseStack mStack = RenderSystem.getModelViewStack();

        mStack.pushPose();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        render.renderAndDecorateItem(stack, x, y);
        render.renderGuiItemDecorations(Minecraft.getInstance().font, stack, x, y, null);
        mStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    /**
     * MatrixStack isn't used yet as vanilla ItemRenderer does not use it yet.
     * @param poseStack - The itemstack to be drawn
     * @param x     - The position on the x-axis to draw the itemstack
     * @param y     - The position on the y-axis to draw the itemstack
     * @param scale - The scale with which to draw the itemstack
     */
    public static void drawScaledItemStack(PoseStack poseStack, ItemStack stack, int x, int y, float scale) {
        PoseStack mStack = RenderSystem.getModelViewStack();

        mStack.pushPose();
        mStack.scale(scale, scale, 1f);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        RenderSystem.applyModelViewMatrix();
        render.renderAndDecorateItem(stack, (int )(x/scale) , (int)(y /scale));
        mStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    /**
     * MatrixStack isn't used yet as vanilla ItemRenderer does not use it yet.
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel -
     */
    public static void drawIconWithoutColor(PoseStack poseStack, int x, int y, int width, int height, float zLevel) {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        Tesselator tessellator = Tesselator.getInstance();
        tessellator.getBuilder().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        tessellator.getBuilder().vertex(x, y + height, zLevel).uv(0f, 1f).endVertex();
        tessellator.getBuilder().vertex(x + width, y + height, zLevel).uv(1f, 1f).endVertex();
        tessellator.getBuilder().vertex(x + width, y, zLevel).uv(1f, 0f).endVertex();
        tessellator.getBuilder().vertex(x, y, zLevel).uv(0f, 0f).endVertex();
        tessellator.end();
    }

    /**
     * MatrixStack isn't used yet as vanilla ItemRenderer does not use it yet.
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel -
     * @param color  - The color the icon will have
     */
    public static void drawIconWithColor(PoseStack poseStack, int x, int y, int width, int height, float zLevel, Color color) {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
        Tesselator tessellator = Tesselator.getInstance();
        tessellator.getBuilder().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        tessellator.getBuilder().vertex(x, y + height, zLevel).uv(0f, 1f).endVertex();
        tessellator.getBuilder().vertex(x + width, y + height, zLevel).uv(1f, 1f).endVertex();
        tessellator.getBuilder().vertex(x + width, y, zLevel).uv(1f, 0f).endVertex();
        tessellator.getBuilder().vertex(x, y, zLevel).uv(0f, 0f).endVertex();
        tessellator.end();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * MatrixStack isn't used yet as vanilla ItemRenderer does not use it yet.
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param zLevel -
     */
    public static void drawSizedIconWithoutColor(PoseStack poseStack, int x, int y, int width, int height, float zLevel) {
        PoseStack mStack = RenderSystem.getModelViewStack();
        mStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        mStack.scale(0.5f, 0.5f, 0.5f);
        mStack.translate(x, y, zLevel);
        RenderSystem.applyModelViewMatrix();
        RenderSystem.enableDepthTest();
        Tesselator tessellator = Tesselator.getInstance();
        tessellator.getBuilder().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        tessellator.getBuilder().vertex(x, y + height, zLevel).uv(0f, 1f).endVertex();
        tessellator.getBuilder().vertex(x + width, y + height, zLevel).uv(1f, 1).endVertex();
        tessellator.getBuilder().vertex(x + width, y, zLevel).uv(1, 0).endVertex();
        tessellator.getBuilder().vertex(x, y, zLevel).uv(0, 0).endVertex();
        tessellator.end();
        mStack.popPose();
        RenderSystem.applyModelViewMatrix();

    }

    /**
     * MatrixStack isn't used yet as vanilla ItemRenderer does not use it yet.
     * @param x      - The position on the x-axis to draw the icon
     * @param y      - The position on the y-axis to draw the icon
     * @param width  - The width of the icon
     * @param height - The height of the icon
     * @param color  - The color the icon will have
     */
    public static void drawSizedIconWithColor(PoseStack poseStack, int x, int y, int width, int height, float zLevel, Color color) {
        PoseStack mStack = RenderSystem.getModelViewStack();
        mStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mStack.scale(0.5f, 0.5f, 0.5f);
        RenderSystem.setShaderColor((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
        mStack.translate(x, y, zLevel);
        RenderSystem.applyModelViewMatrix();
        RenderSystem.enableDepthTest();
        Tesselator tessellator = Tesselator.getInstance();
        tessellator.getBuilder().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        tessellator.getBuilder().vertex(x, y + height, zLevel).uv(0, 1).endVertex();
        tessellator.getBuilder().vertex(x + width, y + height, zLevel).uv(1, 1).endVertex();
        tessellator.getBuilder().vertex(x + width, y, zLevel).uv(1, 0).endVertex();
        tessellator.getBuilder().vertex(x, y, zLevel).uv(0, 0).endVertex();
        tessellator.end();
        mStack.pushPose();
        RenderSystem.applyModelViewMatrix();
    }

    @SuppressWarnings("unchecked")
    public static List<Component> getTooltip(ItemStack stack) {
        Minecraft mc = Minecraft.getInstance();
        List<Component> list = stack.getTooltipLines(mc.player, mc.options.advancedItemTooltips ? Default.ADVANCED : Default.NORMAL);
        for (int k = 0; k < list.size(); ++k) {
            Component c = list.get(k);
            if (c instanceof MutableComponent) {
                if (k == 0) {
                    ((MutableComponent) c).withStyle(stack.getRarity().color); //applyTextComponent
                } else {
                    ((MutableComponent) c).withStyle(ChatFormatting.GRAY);
                }
            }

        }
        return list;
    }
}
