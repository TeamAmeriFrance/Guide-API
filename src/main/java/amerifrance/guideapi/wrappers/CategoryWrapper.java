package amerifrance.guideapi.wrappers;

import amerifrance.guideapi.objects.Category;
import net.minecraft.entity.player.EntityPlayer;

public class CategoryWrapper {

    public Category category;

    public CategoryWrapper(Category category) {
        this.category = category;
    }

    public void onClicked() {
    }

    public void onHoverOver(int mouseX, int mouseY) {
    }

    public boolean canPlayerSee(EntityPlayer player) {
        return true;
    }
}
