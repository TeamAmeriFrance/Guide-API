package amerifrance.guideapi.displays;

import amerifrance.guideapi.api.Button;
import amerifrance.guideapi.api.DisplayProvider;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.TextButton;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

public abstract class HistoryBaseDisplay implements Display {
    protected DisplayProvider hovered;

    private Button backButton;

    @Override
    public void init(GuideGui guideGui, int top, int left, int width, int height) {
        backButton = new TextButton(guideGui::back, "Back", left + width, top);
    }

    @Override
    public void draw(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        hovered = null;
        TextRenderer textRenderer = guideGui.getTextRenderer();

        if (!guideGui.getHistory().isEmpty()) {
            backButton.draw(textRenderer, matrixStack, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(GuideGui guideGui, double mouseX, double mouseY, int button) {
        TextRenderer textRenderer = guideGui.getTextRenderer();

        if (backButton.mouseOver(textRenderer, mouseX, mouseY)) {
            return backButton.click();
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
