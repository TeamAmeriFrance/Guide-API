package amerifrance.guideapi.button;

import api.SubTexture;
import api.button.ButtonGuideAPI;
import api.util.GuiHelper;
import api.util.TextHelper;
import amerifrance.guideapi.gui.BaseScreen;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class ButtonSearch extends ButtonGuideAPI {

    public ButtonSearch(int widthIn, int heightIn, Button.IPressable onPress, BaseScreen guiBase) {
        super(widthIn, heightIn, 15, 15, onPress,guiBase);
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableBlend();
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, width, height)) {
                SubTexture.MAGNIFYING_GLASS.draw(x, y + 1);
                GuiUtils.drawHoveringText(getHoveringText(),mouseX,mouseY,width,height,width,Minecraft.getInstance().fontRenderer);
            } else {
                SubTexture.MAGNIFYING_GLASS.draw(x, y);
            }
            GlStateManager.disableBlend();
            RenderHelper.disableStandardItemLighting();
        }
    }

    public List<String> getHoveringText() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(TextHelper.localizeEffect("button.search.name"));
        return list;
    }
}
