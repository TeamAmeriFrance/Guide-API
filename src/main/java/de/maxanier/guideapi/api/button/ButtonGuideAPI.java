package de.maxanier.guideapi.api.button;

import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ButtonGuideAPI extends Button {

    public BaseScreen guiBase;

    public ButtonGuideAPI(int widthIn, int heightIn, int width, int height, Component name, Button.OnPress onPress, BaseScreen guiBase) {
        super(widthIn, heightIn, width, height, name, onPress);
        this.guiBase = guiBase;
    }

    public List<Component> getHoveringText() {
        ArrayList<Component> list = new ArrayList<>();
        list.add(getMessage());
        return list;
    }
}
