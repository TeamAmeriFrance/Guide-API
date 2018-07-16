package amerifrance.guideapi.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.util.List;

import static net.minecraft.client.renderer.GlStateManager.*;

public class GuiBase extends GuiScreen {

    public int guiLeft, guiTop;
    public int xSize = 192;
    public int ySize = 192;
    public EntityPlayer player;
    public ItemStack bookStack;
    public float publicZLevel;

    public GuiBase(EntityPlayer player, ItemStack bookStack) {
        this.player = player;
        this.bookStack = bookStack;
        this.publicZLevel = zLevel;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }

    public void drawTexturedModalRectWithColor(int x, int y, int textureX, int textureY, int width, int height, Color color) {
        pushMatrix();
        enableBlend();
        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        disableLighting();
        color((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        tessellator.getBuffer().pos((double) (x), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX) * f), (double) ((float) (textureY + height) * f1)).endVertex();
        tessellator.getBuffer().pos((double) (x + width), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX + width) * f), (double) ((float) (textureY + height) * f1)).endVertex();
        tessellator.getBuffer().pos((double) (x + width), (double) (y), (double) this.zLevel).tex((double) ((float) (textureX + width) * f), (double) ((float) (textureY) * f1)).endVertex();
        tessellator.getBuffer().pos((double) (x), (double) (y), (double) this.zLevel).tex((double) ((float) (textureX) * f), (double) ((float) (textureY) * f1)).endVertex();
        tessellator.draw();
        disableBlend();
        popMatrix();
    }

    @Override
    public void drawCenteredString(FontRenderer fontRendererObj, String string, int x, int y, int color) {
        RenderHelper.disableStandardItemLighting();
        fontRendererObj.drawString(string, x - fontRendererObj.getStringWidth(string) / 2, y, color);
        RenderHelper.disableStandardItemLighting();
    }

    public void drawCenteredStringWithShadow(FontRenderer fontRendererObj, String string, int x, int y, int color) {
        super.drawCenteredString(fontRendererObj, string, x, y, color);
    }

    @Override
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        pushMatrix();
        color(1.0F, 1.0F, 1.0F, 1.0F);
        super.drawTexturedModalRect(x, y, textureX, textureY, width, height);
        popMatrix();
    }

    @Override
    public void drawHoveringText(List<String> list, int x, int y, FontRenderer font) {
        disableLighting();
        RenderHelper.disableStandardItemLighting();
        super.drawHoveringText(list, x, y, font);
        RenderHelper.enableStandardItemLighting();
        enableLighting();
    }

    @Override
    public void drawHoveringText(List<String> list, int x, int y) {
        disableLighting();
        RenderHelper.disableStandardItemLighting();
        super.drawHoveringText(list, x, y);
        RenderHelper.enableStandardItemLighting();
        enableLighting();
    }
}
