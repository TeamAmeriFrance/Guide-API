package de.maxanier.guideapi.wrapper;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.gui.BaseScreen;

public abstract class AbstractWrapper {

    public abstract boolean canPlayerSee();

    public abstract void draw(PoseStack stack, int mouseX, int mouseY, BaseScreen gui);

    public abstract void drawExtras(PoseStack stack, int mouseX, int mouseY, BaseScreen gui);

    public abstract boolean isMouseOnWrapper(double mouseX, double mouseY);

    public abstract void onHoverOver(int mouseX, int mouseY);
}
