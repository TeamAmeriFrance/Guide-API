package de.maxanier.guideapi.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static com.mojang.blaze3d.platform.GlStateManager.disableBlend;


public class BaseScreen extends Screen {

    public int guiLeft, guiTop;
    public int xSize = 245;
    public int ySize = 192;
    public PlayerEntity player;
    public ItemStack bookStack;
    public float publicZLevel;

    public BaseScreen(ITextComponent title, PlayerEntity player, ItemStack bookStack) {
        super(title);
        this.player = player;
        this.bookStack = bookStack;
        this.publicZLevel = this.getBlitOffset(); //getBlitOffset
    }

    public void drawCenteredStringWithoutShadow(MatrixStack matrixStack, FontRenderer fontRendererObj, String string, int x, int y, int color) {
        RenderHelper.disableStandardItemLighting();
        fontRendererObj.drawString(matrixStack, string, x - fontRendererObj.getStringWidth(string) / 2f, y, color); //drawString
        RenderHelper.disableStandardItemLighting();
    }

    public void drawCenteredStringWithoutShadow(MatrixStack matrixStack, FontRenderer fontRendererObj, ITextProperties string, int x, int y, int color) {
        RenderHelper.disableStandardItemLighting();
        fontRendererObj.func_238422_b_(matrixStack, string, x - fontRendererObj.func_238414_a_(string) / 2f, y, color); //drawString
        RenderHelper.disableStandardItemLighting();
    }

    public void drawTexturedModalRectWithColor(MatrixStack stack, int x, int y, int textureX, int textureY, int width, int height, Color color) {
        stack.push();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        RenderSystem.disableLighting();
        RenderSystem.color3f((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        tessellator.getBuffer().pos(x, y + height, this.publicZLevel).tex((float) (textureX) * f, (float) (textureY + height) * f1).endVertex();
        tessellator.getBuffer().pos(x + width, y + height, this.publicZLevel).tex((float) (textureX + width) * f, (float) (textureY + height) * f1).endVertex();
        tessellator.getBuffer().pos(x + width, y, this.publicZLevel).tex((float) (textureX + width) * f, (float) (textureY) * f1).endVertex();
        tessellator.getBuffer().pos(x, y, this.publicZLevel).tex((float) (textureX) * f, (float) (textureY) * f1).endVertex();
        tessellator.draw();
        disableBlend();
        stack.pop();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    } //IsPauseScreen

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) { //KeyPressed
        if (minecraft != null && (p_keyPressed_1_ == GLFW.GLFW_KEY_ESCAPE || p_keyPressed_1_ == this.minecraft.gameSettings.keyBindInventory.getKey().getKeyCode())) { //minecraft
            this.closeScreen(); //onClose
            this.minecraft.setGameFocused(true);
            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }


}
