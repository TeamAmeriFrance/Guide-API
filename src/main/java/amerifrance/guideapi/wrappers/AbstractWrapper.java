package amerifrance.guideapi.wrappers;

import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractWrapper {

    public abstract void onClicked();

    public abstract void onHoverOver(int mouseX, int mouseY);

    public abstract boolean canPlayerSee(EntityPlayer player);

    public abstract void draw();

    public abstract boolean isMouseOnWrapper(int mouseX, int mouseY);
}
