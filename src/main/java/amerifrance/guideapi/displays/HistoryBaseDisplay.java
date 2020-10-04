package amerifrance.guideapi.displays;

import amerifrance.guideapi.api.Button;
import amerifrance.guideapi.api.Display;
import amerifrance.guideapi.api.DisplayProvider;
import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.gui.ImageButton;
import amerifrance.guideapi.gui.RenderElement;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public abstract class HistoryBaseDisplay implements Display {
    private static final RenderElement BACK_BUTTON_RENDER = new RenderElement(
            new Identifier("guideapi", "textures/gui/background.png"),
            72, 225, 18, 10);

    private static final RenderElement BACK_BUTTON_HOVER_RENDER = new RenderElement(
            new Identifier("guideapi", "textures/gui/background.png"),
            90, 225, 18, 10);

    protected DisplayProvider hovered;

    private Button backButton;

    @Override
    public void init(GuideGui guideGui, int top, int left, int width, int height) {
        this.backButton = new ImageButton(guideGui::back,
                guideGui,
                BACK_BUTTON_RENDER, BACK_BUTTON_HOVER_RENDER,
                left, top);
    }

    @Override
    public void draw(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        hovered = null;

        if (!guideGui.getHistory().isEmpty()) {
            backButton.draw(guideGui.getTextRenderer(), matrixStack, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(GuideGui guideGui, double mouseX, double mouseY, int button) {
        if (backButton.mouseOver(guideGui.getTextRenderer(), mouseX, mouseY))
            return backButton.click();

        if (hovered != null) {
            guideGui.show(hovered.getDisplay());

            return true;
        }

        return false;
    }

    @Override
    public boolean mouseScrolled(GuideGui guideGui, double mouseX, double mouseY, double amount) {
        return false;
    }
}
