package amerifrance.guideapi.button;

import amerifrance.guideapi.GuideMod;
import amerifrance.guideapi.api.button.ButtonGuideAPI;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ButtonPrev extends ButtonGuideAPI {

    public ButtonPrev(int id, int x, int y, GuiBase guiBase) {
        super(id, x, y, guiBase);
        width = 18;
        height = 10;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            minecraft.getTextureManager().bindTexture(new ResourceLocation(GuideMod.ID, "textures/gui/book_colored.png"));
            if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, width, height)) {
                this.drawTexturedModalRect(x, y + 1, 47, 214, 18, 10);
                guiBase.drawHoveringText(getHoveringText(), mouseX, mouseY, Minecraft.getMinecraft().fontRenderer);
            } else {
                this.drawTexturedModalRect(x, y, 24, 214, 18, 10);
            }
            GlStateManager.disableBlend();
            RenderHelper.disableStandardItemLighting();
        }
    }

    public List<String> getHoveringText() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(TextHelper.localizeEffect("button.prev.name"));
        return list;
    }
}