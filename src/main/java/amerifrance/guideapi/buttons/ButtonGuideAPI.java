package amerifrance.guideapi.buttons;

import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.gui.GuiButton;

public class ButtonGuideAPI extends GuiButton {

    public GuiBase guiBase;

    public ButtonGuideAPI(int id, int x, int y, GuiBase guiBase) {
        super(id, x, y, "");
        this.guiBase = guiBase;
    }
}
