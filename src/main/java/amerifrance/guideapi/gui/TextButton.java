package amerifrance.guideapi.gui;

import amerifrance.guideapi.api.Button;
import amerifrance.guideapi.utils.MouseHelper;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class TextButton implements Button {

    private final String text;
    private final int buttonX;
    private final int buttonY;
    private final Runnable onClick;

    public TextButton(Runnable onClick, String text, int buttonX, int buttonY) {
        this.onClick = onClick;
        this.text = text;
        this.buttonX = buttonX;
        this.buttonY = buttonY;
    }

    @Override
    public void draw(TextRenderer textRenderer, MatrixStack matrixStack, int mouseX, int mouseY) {
        textRenderer.draw(matrixStack, text, buttonX, buttonY, 0);

        if (mouseOver(textRenderer, mouseX, mouseY)) {
            textRenderer.draw(matrixStack, text, buttonX, buttonY, 0xffffff);
        }
    }

    @Override
    public boolean click() {
        onClick.run();

        return true;
    }

    @Override
    public boolean mouseOver(TextRenderer textRenderer, double mouseX, double mouseY) {
        return MouseHelper.isInRect(buttonX, buttonY, textRenderer.getWidth(text), textRenderer.fontHeight, mouseX, mouseY);
    }
}
