package amerifrance.guideapi.api.button;

import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.widget.button.Button;

public class ButtonGuideAPI extends Button {

    public BaseScreen guiBase;

    public ButtonGuideAPI(int widthIn, int heightIn, int width, int height, Button.IPressable onPress, BaseScreen guiBase) {
        super(widthIn, heightIn, width, height, "", onPress);
        this.guiBase = guiBase;
    }
}
