package amerifrance.guideapi.api.button;

import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.gui.widget.button.Button;

public class ButtonGuideAPI extends Button {

    public GuiBase guiBase;

    public ButtonGuideAPI(int id, int x, int y, GuiBase guiBase) {
        super(id, x, y, "");
        this.guiBase = guiBase;
    }
}
