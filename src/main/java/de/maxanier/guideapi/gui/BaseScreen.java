package de.maxanier.guideapi.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public class BaseScreen extends Screen {

    public int guiLeft, guiTop;
    public int xSize = 245;
    public int ySize = 192;
    public Player player;
    public ItemStack bookStack;
    public float publicZLevel;

    public BaseScreen(Component title, Player player, ItemStack bookStack) {
        super(title);
        this.player = player;
        this.bookStack = bookStack;
        this.publicZLevel = this.getBlitOffset();
    }

    public void drawCenteredStringWithoutShadow(PoseStack matrixStack, Font fontRendererObj, String string, int x, int y, int color) {
        fontRendererObj.draw(matrixStack, string, x - fontRendererObj.width(string) / 2f, y, color);
    }

    public void drawCenteredStringWithoutShadow(PoseStack matrixStack, Font fontRendererObj, FormattedCharSequence string, int x, int y, int color) {
        fontRendererObj.draw(matrixStack, string, x - fontRendererObj.width(string) / 2f, y, color);
    }

    public void drawCenteredStringWithoutShadow(PoseStack matrixStack, Font fontRendererObj, Component string, int x, int y, int color) {
        fontRendererObj.draw(matrixStack, string, x - fontRendererObj.width(string) / 2f, y, color);
    }


    public void drawTexturedModalRectWithColor(PoseStack stack, int x, int y, int textureX, int textureY, int width, int height, Color color) {
        stack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        RenderSystem.setShaderColor((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F,1f);
        Tesselator tessellator = Tesselator.getInstance();
        tessellator.getBuilder().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        tessellator.getBuilder().vertex(x, y + height, this.publicZLevel).uv((float) (textureX) * f, (float) (textureY + height) * f1).endVertex();
        tessellator.getBuilder().vertex(x + width, y + height, this.publicZLevel).uv((float) (textureX + width) * f, (float) (textureY + height) * f1).endVertex();
        tessellator.getBuilder().vertex(x + width, y, this.publicZLevel).uv((float) (textureX + width) * f, (float) (textureY) * f1).endVertex();
        tessellator.getBuilder().vertex(x, y, this.publicZLevel).uv((float) (textureX) * f, (float) (textureY) * f1).endVertex();
        tessellator.end();
        GlStateManager._disableBlend();
        stack.popPose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (minecraft != null && (p_keyPressed_1_ == GLFW.GLFW_KEY_ESCAPE || p_keyPressed_1_ == this.minecraft.options.keyInventory.getKey().getValue())) {
            this.onClose();
            this.minecraft.setWindowActive(true);
            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }
}
