package amerifrance.guideapi.gui;

import amerifrance.guideapi.displays.Display;
import amerifrance.guideapi.guide.Guide;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringRenderable;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class GuideGui extends Screen {

    public final static int GUI_WIDTH = 170;
    public final static int GUI_HEIGHT = 215;

    public static final int BACKGROUD_GUI_DELTA = 10;
    public final static int BACKGROUND_TEXTURE_WIDTH = GUI_WIDTH + BACKGROUD_GUI_DELTA;
    public final static int BACKGROUND_TEXTURE_HEIGHT = GUI_HEIGHT + BACKGROUD_GUI_DELTA;

    private static final RenderElement GUIDE_BACKGROUND = new RenderElement(
            new Identifier("guideapi", "textures/gui/background.png"),
            0, 0, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);

    private final Guide guide;

    private int top;
    private int left;

    private Display currentDisplay;
    private final List<Display> history = newArrayList();

    public GuideGui(Guide guide) {
        super(new LiteralText(guide.getText()));

        this.guide = guide;
        this.currentDisplay = guide.getDisplay();
    }

    @Override
    public void init() {
        top = client.getWindow().getScaledHeight() / 2 - GUI_HEIGHT / 2;
        left = client.getWindow().getScaledWidth() / 2 - GUI_WIDTH / 2;
        width = GUI_WIDTH;
        height = GUI_HEIGHT;

        currentDisplay.init(this, top, left, GUI_WIDTH, GUI_HEIGHT);
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        GUIDE_BACKGROUND.render(this,
                matrices, client.getTextureManager(),
                left - BACKGROUD_GUI_DELTA, top - BACKGROUD_GUI_DELTA / 2);

        currentDisplay.draw(this, matrices, mouseX, mouseY, delta);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return currentDisplay.mouseClicked(this, mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return currentDisplay.mousesScrolled(this, mouseX, mouseY, amount);
    }

    @Override
    public void renderTooltip(MatrixStack matrices, ItemStack stack, int x, int y) {
        super.renderTooltip(matrices, stack, x, y);
    }

    @Override
    public void renderTextHoverEffect(MatrixStack matrices, Style style, int i, int j) {
        super.renderTextHoverEffect(matrices, style, i, j);
    }

    public void show(Display display) {
        history.add(0, currentDisplay);

        currentDisplay = display;

        init();
    }

    public void back() {
        if (history.isEmpty())
            return;

        currentDisplay = history.get(0);
        history.remove(0);
    }

    public MinecraftClient getMinecraftClient() {
        return client;
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    public int getFontHeight() {
        return getTextRenderer().fontHeight;
    }

    public int getStringWidth(String string) {
        return textRenderer.getWidth(string);
    }

    public int getStringWidth(StringRenderable stringRenderable) {
        return textRenderer.getWidth(stringRenderable);
    }

    public void drawCenteredString(MatrixStack matrices, String text, float x, float y, int color) {
        textRenderer.draw(matrices, text, x - textRenderer.getWidth(text) / 2F, y, color);
    }

    public List<StringRenderable> wrapLines(String string, int width) {
        return textRenderer.wrapLines(new LiteralText(string), width);
    }

    public void drawString(MatrixStack matrixStack, String text, float x, float y, int color) {
        textRenderer.draw(matrixStack, text, x, y, color);
    }

    public void drawString(MatrixStack matrixStack, StringRenderable text, float x, float y, int color) {
        textRenderer.draw(matrixStack, text, x, y, color);
    }

    public int getDrawStartHeight() {
        return getTop() + getFontHeight() * 2;
    }

    public int getDrawEndHeight() {
        return getTop() + GUI_HEIGHT;
    }

    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public List<Display> getHistory() {
        return history;
    }
}
