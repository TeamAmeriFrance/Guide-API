package amerifrance.guideapi.gui;

import amerifrance.guideapi.utils.Area;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RenderElement {

    private final Identifier identifier;
    private final int u;
    private final int v;
    private final Area area;

    public RenderElement(Identifier identifier, int u, int v, int width, int height) {
        this.identifier = identifier;
        this.u = u;
        this.v = v;
        this.area = new Area(width, height);
    }

    public void render(Screen screen, MatrixStack matrixStack, TextureManager textureManager, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        textureManager.bindTexture(identifier);
        screen.drawTexture(matrixStack, x, y, u, v, area.getWidth(), area.getHeight());
    }

    public Area getArea() {
        return area;
    }
}
