package amerifrance.guideapi.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import static com.mojang.blaze3d.platform.GlStateManager.*;


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
        this.publicZLevel = blitOffset;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (minecraft != null && (p_keyPressed_1_ == GLFW.GLFW_KEY_ESCAPE || p_keyPressed_1_ == this.minecraft.gameSettings.keyBindInventory.getKey().getKeyCode())) {
            this.onClose();
            this.minecraft.setGameFocused(true);
            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }

    public void drawTexturedModalRectWithColor(int x, int y, int textureX, int textureY, int width, int height, Color color) {
        pushMatrix();
        enableBlend();
        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        disableLighting();
        color3f((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        tessellator.getBuffer().pos(x, y + height, this.blitOffset).tex((float) (textureX) * f, (float) (textureY + height) * f1).endVertex();
        tessellator.getBuffer().pos(x + width, y + height, this.blitOffset).tex((float) (textureX + width) * f, (float) (textureY + height) * f1).endVertex();
        tessellator.getBuffer().pos(x + width, y, this.blitOffset).tex((float) (textureX + width) * f, (float) (textureY) * f1).endVertex();
        tessellator.getBuffer().pos(x, y, this.blitOffset).tex((float) (textureX) * f, (float) (textureY) * f1).endVertex();
        tessellator.draw();
        disableBlend();
        popMatrix();
    }

    @Override
    public void drawCenteredString(FontRenderer fontRendererObj, String string, int x, int y, int color) {
        RenderHelper.disableStandardItemLighting();
        fontRendererObj.drawString(string, x - fontRendererObj.getStringWidth(string) / 2f, y, color);
        RenderHelper.disableStandardItemLighting();
    }

    public void drawCenteredStringWithShadow(FontRenderer fontRendererObj, String string, int x, int y, int color) {
        super.drawCenteredString(fontRendererObj, string, x, y, color);
    }

    public void drawHoveringTextComponents(List<ITextComponent> tooltip, int mouseX, int mouseY) {
        this.drawHoveringText(tooltip.stream().map(ITextComponent::getFormattedText).collect(Collectors.toList()), mouseX, mouseY);
    }

    public void drawHoveringText(List<String> tooltip, int mouseX, int mouseY) {
        GuiUtils.drawHoveringText(tooltip, mouseX, mouseY, width, height, -1, font);
    }


}
