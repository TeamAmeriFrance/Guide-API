package amerifrance.guideapi.button;

import amerifrance.guideapi.api.SubTexture;
import amerifrance.guideapi.api.button.ButtonGuideAPI;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;

import java.util.ArrayList;
import java.util.List;

public class ButtonSearch extends ButtonGuideAPI {

    public ButtonSearch(int id, int x, int y, GuiBase guiBase) {
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
