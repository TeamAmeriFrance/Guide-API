package amerifrance.guideapi.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import java.awt.*;

public class GraphicalElement {

    public static final GraphicalElement BUTTON_NEXT = new GraphicalElement(new Identifier("guideapi", "textures/widgets.png"), new Rectangle(0, 0, 18, 10));
    public static final GraphicalElement BUTTON_NEXT_HOVER = new GraphicalElement(new Identifier("guideapi", "textures/widgets.png"), new Rectangle(0, 10, 18, 10));
    public static final GraphicalElement BUTTON_PREVIOUS = new GraphicalElement(new Identifier("guideapi", "textures/widgets.png"), new Rectangle(18, 0, 18, 10));
    public static final GraphicalElement BUTTON_PREVIOUS_HOVER = new GraphicalElement(new Identifier("guideapi", "textures/widgets.png"), new Rectangle(18, 10, 18, 10));
    public static final GraphicalElement BUTTON_BACK = new GraphicalElement(new Identifier("guideapi", "textures/widgets.png"), new Rectangle(36, 0, 18, 10));
    public static final GraphicalElement BUTTON_BACK_HOVER = new GraphicalElement(new Identifier("guideapi", "textures/widgets.png"), new Rectangle(36, 10, 18, 10));
    public static final GraphicalElement BUTTON_SEARCH = new GraphicalElement(new Identifier("guideapi", "textures/widgets.png"), new Rectangle(0, 20, 14, 14));
    public static final GraphicalElement BLANK_PAGE = new GraphicalElement(new Identifier("guideapi", "textures/widgets.png"), new Rectangle(54, 0, 132, 165));

    public static final GraphicalElement BACKGROUND = new GraphicalElement(new Identifier("guideapi", "textures/gui/background.png"), new Rectangle(0, 0, 170, 215));

    private final Identifier location;
    private final Rectangle area;

    public GraphicalElement(Identifier location, Rectangle area) {
        this.location = location;
        this.area = area;
    }

    public Identifier getLocation() {
        return location;
    }

    public Rectangle getArea() {
        return area;
    }

    public void draw(double zLevel, int x, int y) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(location);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(7, VertexFormats.POSITION);
        append(zLevel, x, y, builder);
        tessellator.draw();
    }

    private void append(double zLevel, int x, int y, BufferBuilder builder) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        builder.vertex((double) x, (double) (y + area.height), zLevel).texture(area.x * f, (area.y + area.height) * f1).next();
        builder.vertex((double) (x + area.width), (double) (y + area.height), zLevel).texture((area.x + area.width) * f, (area.y + area.height) * f1).next();
        builder.vertex((double) (x + area.width), (double) y, zLevel).texture((area.x + area.width) * f, area.y * f1).next();
        builder.vertex((double) x, (double) (y), zLevel).texture(area.x * f, area.y * f1).next();
    }
}
