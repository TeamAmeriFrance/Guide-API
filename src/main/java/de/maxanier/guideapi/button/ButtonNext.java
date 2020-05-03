package de.maxanier.guideapi.button;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.button.ButtonGuideAPI;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.api.util.TextHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class ButtonNext extends ButtonGuideAPI {

    public ButtonNext(int widthIn, int heightIn, Button.IPressable onPress, BaseScreen guiBase) {
        super(widthIn, heightIn, 18, 10, onPress, guiBase);
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        RenderHelper.enableStandardItemLighting();
        RenderSystem.enableBlend();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableLighting();
        Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(GuideMod.ID, "textures/gui/book_colored.png"));
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, width, height)) {
            this.blit(x, y + 1, 47, 201, 18, 10);
            GuiUtils.drawHoveringText(getHoveringText(), mouseX, mouseY, guiBase.width, guiBase.height, -1, Minecraft.getInstance().fontRenderer);
        } else {
            this.blit(x, y, 24, 201, 18, 10);
        }
        RenderSystem.disableBlend();
        RenderHelper.disableStandardItemLighting();

    }

    public List<String> getHoveringText() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(TextHelper.localizeEffect("guideapi.button.next"));
        return list;
    }
}
