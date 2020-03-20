package amerifrance.guideapi.wrapper;

import amerifrance.guideapi.gui.BaseScreen;

public abstract class AbstractWrapper {

    public abstract void onHoverOver(int mouseX, int mouseY);

    public abstract boolean canPlayerSee();

    public abstract void draw(int mouseX, int mouseY, BaseScreen gui);

    public abstract void drawExtras(int mouseX, int mouseY, BaseScreen gui);

    public abstract boolean isMouseOnWrapper(int mouseX, int mouseY);
}
