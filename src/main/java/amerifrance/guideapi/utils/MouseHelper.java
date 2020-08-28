package amerifrance.guideapi.utils;

public class MouseHelper {

    public static boolean isInRect(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public static boolean isInArea(int x, int y, Area area, double mouseX, double mouseY) {
        return isInRect(x, y, area.getWidth(), area.getHeight(), mouseX, mouseY);
    }

    public static boolean isInRenderStack(RenderStack renderStack, double mouseX, double mouseY) {
        return isInArea(renderStack.getX(), renderStack.getY(), renderStack.getArea(), mouseX, mouseY);
    }
}
