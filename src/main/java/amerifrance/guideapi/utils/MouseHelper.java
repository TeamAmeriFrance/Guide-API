package amerifrance.guideapi.utils;

public class MouseHelper {

    public static boolean isInRect(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
