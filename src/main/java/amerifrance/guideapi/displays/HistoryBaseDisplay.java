package amerifrance.guideapi.displays;

import amerifrance.guideapi.utils.MouseHelper;
import amerifrance.guideapi.api.DisplayProvider;
import amerifrance.guideapi.gui.GuideGui;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

public abstract class HistoryBaseDisplay implements Display {
    private static final String BACK_BUTTON_TEXT = "Back";

    protected DisplayProvider hovered;

    private int backButtonX;
    private int backButtonY;

    @Override
    public void init(GuideGui guideGui, int top, int left, int width, int height) {
        backButtonX = left + width;
        backButtonY = top;
    }

    @Override
    public void draw(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        hovered = null;
        TextRenderer textRenderer = guideGui.getTextRenderer();

        //FIXME Temporary
        if (!guideGui.getHistory().isEmpty()) {
            textRenderer.draw(matrixStack, BACK_BUTTON_TEXT, backButtonX, backButtonY, 0);

            if (MouseHelper.isInRect(backButtonX, backButtonY, textRenderer.getWidth(BACK_BUTTON_TEXT), textRenderer.fontHeight, mouseX, mouseY)) {
                textRenderer.draw(matrixStack, BACK_BUTTON_TEXT, backButtonX, backButtonY, 0xffffff);
            }
        }
    }

    @Override
    public boolean mouseClicked(GuideGui guideGui, double mouseX, double mouseY, int button) {
        TextRenderer textRenderer = guideGui.getTextRenderer();

        if (MouseHelper.isInRect(backButtonX, backButtonY, textRenderer.getWidth(BACK_BUTTON_TEXT), textRenderer.fontHeight, mouseX, mouseY)) {
            guideGui.back();
            return true;
        }

        if (hovered != null) {
            guideGui.show(hovered.getDisplay());

            return true;
        }

        return false;
    }

    @Override
    public boolean mousesScrolled(GuideGui guideGui, double mouseX, double mouseY, double amount) {
        return false;
    }
}
