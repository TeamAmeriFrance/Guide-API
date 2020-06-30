package amerifrance.guideapi.displays;

import amerifrance.guideapi.gui.GuideGui;
import net.minecraft.client.util.math.MatrixStack;

public interface Display {
    void init(GuideGui guideGui, int top, int left, int width, int height);

    void draw(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, float delta);

    boolean mouseClicked(GuideGui guideGui, double mouseX, double mouseY, int button);

    boolean mousesScrolled(GuideGui guideGui, double mouseX, double mouseY, double amount);
}
