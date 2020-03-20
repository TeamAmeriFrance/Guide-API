package api.button;

import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.widget.button.Button;

public class ButtonGuideAPI extends Button {

    public BaseScreen guiBase;

    public ButtonGuideAPI(int id, int x, int y, BaseScreen guiBase) {
        super(id, x, y, "");
        this.guiBase = guiBase;
    }
}
