package de.maxanier.guideapi.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.button.ButtonGuideAPI;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.util.GuiUtilsCopy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ButtonNext extends ButtonGuideAPI {

    public ButtonNext(int widthIn, int heightIn, Button.OnPress onPress, BaseScreen guiBase) {
        super(widthIn, heightIn, 18, 10, Component.translatable("guideapi.button.next"), onPress, guiBase);
    }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, new ResourceLocation(GuideMod.ID, "textures/gui/book_colored.png"));
            if (GuiHelper.isMouseBetween(mouseX, mouseY, getX(), getY(), width, height)) { //x,y,width,height
                this.blit(stack, getX(), getY() + 1, 47, 201, 18, 10); //blit
                GuiUtilsCopy.drawHoveringText(stack, getHoveringText(), mouseX, mouseY, guiBase.width, guiBase.height, -1, Minecraft.getInstance().font);
            } else {
                this.blit(stack, getX(), getY(), 24, 201, 18, 10);
            }
            RenderSystem.disableBlend();
        }
    }

}
