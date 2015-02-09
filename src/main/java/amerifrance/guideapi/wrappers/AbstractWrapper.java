package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractWrapper {

    public abstract void onClicked();

    public abstract void onHoverOver(int mouseX, int mouseY);

    public abstract boolean canPlayerSee(EntityPlayer player);

    public abstract void draw();

    public abstract void drawExtras(int mouseX, int mouseY, GuiBase gui);

    public abstract boolean isMouseOnWrapper(int mouseX, int mouseY);
}
