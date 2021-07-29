package de.maxanier.guideapi.button;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.maxanier.guideapi.api.SubTexture;
import de.maxanier.guideapi.api.button.ButtonGuideAPI;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.util.GuiUtilsCopy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import com.mojang.blaze3d.platform.Lighting;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TranslatableComponent;

public class ButtonSearch extends ButtonGuideAPI {

    public ButtonSearch(int widthIn, int heightIn, Button.OnPress onPress, BaseScreen guiBase) {
        super(widthIn, heightIn, 15, 15, new TranslatableComponent("guideapi.button.search"), onPress, guiBase);
    }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, width, height)) { //x,y,width,height
                SubTexture.MAGNIFYING_GLASS.draw(stack, x, y + 1);
                GuiUtilsCopy.drawHoveringText(stack, getHoveringText(), mouseX, mouseY, guiBase.width, guiBase.height, -1, Minecraft.getInstance().font);
            } else {
                SubTexture.MAGNIFYING_GLASS.draw(stack, x, y);
            }
            RenderSystem.disableBlend();
        }
    }

}
