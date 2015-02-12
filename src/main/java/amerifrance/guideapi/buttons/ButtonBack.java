package amerifrance.guideapi.buttons;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ButtonBack extends GuiButton {

    public ButtonBack(int id, int x, int y) {
        super(id, x, y, "");
        width = 18;
        height = 10;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.visible) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            minecraft.getTextureManager().bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "book_colored.png"));
            if (GuiHelper.isMouseBetween(mouseX, mouseY, xPosition, yPosition, width, height)) {
                this.drawTexturedModalRect(xPosition, yPosition - 2, 70, 201, 18, 10);
            } else {
                this.drawTexturedModalRect(xPosition, yPosition, 94, 201, 18, 10);
            }
            GL11.glDisable(GL11.GL_BLEND);
        }
    }
}
