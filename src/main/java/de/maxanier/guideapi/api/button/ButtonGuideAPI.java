package de.maxanier.guideapi.api.button;

import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class ButtonGuideAPI extends Button {

    public BaseScreen guiBase;

    public ButtonGuideAPI(int widthIn, int heightIn, int width, int height, ITextComponent name, Button.IPressable onPress, BaseScreen guiBase) {
        super(widthIn, heightIn, width, height, name, onPress);
        this.guiBase = guiBase;
    }

    public List<ITextComponent> getHoveringText() {
        ArrayList<ITextComponent> list = new ArrayList<>();
        list.add(getMessage());
        return list;
    }
}
