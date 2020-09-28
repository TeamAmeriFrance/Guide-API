package amerifrance.guideapi.gui;

import amerifrance.guideapi.api.Button;
import amerifrance.guideapi.utils.MouseHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

public class ImageButton implements Button {

    private final RenderElement image;
    private final RenderElement imageHover;
    private final Screen screen;
    private final int buttonX;
    private final int buttonY;
    private final Runnable onClick;

    public ImageButton(Runnable onClick, Screen screen, RenderElement image, RenderElement imageHover, int buttonX, int buttonY) {
        this.onClick = onClick;
        this.screen = screen;
        this.image = image;
        this.imageHover = imageHover;
        this.buttonX = buttonX;
        this.buttonY = buttonY;
    }

    @Override
    public void draw(TextRenderer textRenderer, MatrixStack matrixStack, int mouseX, int mouseY) {
        image.render(screen, matrixStack, MinecraftClient.getInstance().getTextureManager(), buttonX, buttonY);

        if (mouseOver(textRenderer, mouseX, mouseY)) {
            imageHover.render(screen, matrixStack, MinecraftClient.getInstance().getTextureManager(), buttonX, buttonY);
        }
    }

    @Override
    public boolean click() {
        onClick.run();

        return true;
    }

    @Override
    public boolean mouseOver(TextRenderer textRenderer, double mouseX, double mouseY) {
        return MouseHelper.isInArea(buttonX, buttonY, image.getArea(), mouseX, mouseY);
    }
}
