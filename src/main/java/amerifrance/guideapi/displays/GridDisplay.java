package amerifrance.guideapi.displays;

import amerifrance.guideapi.gui.GuideGui;
import amerifrance.guideapi.api.DisplayProvider;
import amerifrance.guideapi.api.TextProvider;
import amerifrance.guideapi.api.ParentOf;
import amerifrance.guideapi.api.RendererProvider;
import net.minecraft.client.util.math.MatrixStack;

public class GridDisplay<T extends TextProvider & ParentOf<U>, U extends RendererProvider & DisplayProvider> extends HistoryBaseDisplay {

    private final T object;

    public GridDisplay(T object) {
        this.object = object;
    }

    @Override
    public void draw(GuideGui guideGui, MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        super.draw(guideGui, matrixStack, mouseX, mouseY, delta);

    }
}
