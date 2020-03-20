package amerifrance.guideapi.button;

import api.SubTexture;
import api.button.ButtonGuideAPI;
import api.util.GuiHelper;
import api.util.TextHelper;
import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;

import java.util.ArrayList;
import java.util.List;

public class ButtonSearch extends ButtonGuideAPI {

    public ButtonSearch(int id, int x, int y, BaseScreen guiBase) {
        super(id, x, y, guiBase);
        width = 15;
        height = 15;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, width, height)) {
                SubTexture.MAGNIFYING_GLASS.draw(x, y + 1);
                guiBase.drawHoveringText(getHoveringText(), mouseX, mouseY, Minecraft.getMinecraft().fontRenderer);
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
