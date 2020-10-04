package amerifrance.guideapi.api;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

public interface Button {
    void draw(TextRenderer textRenderer, MatrixStack matrixStack, int mouseX, int mouseY);

    boolean click();

    boolean mouseOver(TextRenderer textRenderer, double mouseX, double mouseY);
}
