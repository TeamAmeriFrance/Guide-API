package de.maxanier.guideapi.button;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import de.maxanier.guideapi.api.SubTexture;
import de.maxanier.guideapi.api.button.ButtonGuideAPI;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.api.util.TextHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class ButtonSearch extends ButtonGuideAPI {

    public ButtonSearch(int widthIn, int heightIn, Button.IPressable onPress, BaseScreen guiBase) {
        super(widthIn, heightIn, 15, 15, onPress, guiBase);
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            RenderHelper.enableStandardItemLighting();
            RenderSystem.enableBlend();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableLighting();
            if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, width, height)) {
                SubTexture.MAGNIFYING_GLASS.draw(x, y + 1);
                GuiUtils.drawHoveringText(getHoveringText(), mouseX, mouseY, guiBase.width, guiBase.height, -1, Minecraft.getInstance().fontRenderer);
            } else {
                SubTexture.MAGNIFYING_GLASS.draw(x, y);
            }
            RenderSystem.disableBlend();
            RenderHelper.disableStandardItemLighting();
        }
    }

    public List<String> getHoveringText() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(TextHelper.localizeEffect("guideapi.button.search"));
        return list;
    }
}
