package de.maxanier.guideapi.wrapper;

import de.maxanier.guideapi.gui.BaseScreen;

public abstract class AbstractWrapper {

    public abstract void onHoverOver(int mouseX, int mouseY);

    public abstract boolean canPlayerSee();

    public abstract void draw(int mouseX, int mouseY, BaseScreen gui);

    public abstract void drawExtras(int mouseX, int mouseY, BaseScreen gui);

    public abstract boolean isMouseOnWrapper(double mouseX, double mouseY);
}
