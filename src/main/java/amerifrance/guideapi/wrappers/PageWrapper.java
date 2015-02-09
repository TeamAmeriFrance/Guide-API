package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.objects.Page;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;

public class PageWrapper extends AbstractWrapper {

    public Page page;
    public int guiWidth, guiHeight;
    public EntityPlayer player;
    public FontRenderer renderer;

    public PageWrapper(Page page, int guiWidth, int guiHeight, EntityPlayer player, FontRenderer renderer) {
        this.page = page;
        this.guiWidth = guiWidth;
        this.guiHeight = guiHeight;
        this.player = player;
        this.renderer = renderer;
    }

    @Override
    public void onClicked() {
    }

    @Override
    public void onHoverOver(int mouseX, int mouseY) {
    }

    @Override
    public boolean canPlayerSee(EntityPlayer player) {
        return true;
    }

    public boolean canPlayerSee() {
        return canPlayerSee(player);
    }

    @Override
    public void draw() {
        page.drawPage(guiWidth, guiHeight);
    }

    @Override
    public boolean isMouseOnWrapper(int mouseX, int mouseY) {
        return true;
    }
}
