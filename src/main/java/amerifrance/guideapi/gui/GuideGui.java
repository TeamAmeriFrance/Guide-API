package amerifrance.guideapi.gui;

import amerifrance.guideapi.guide.Guide;
import amerifrance.guideapi.displays.Display;
import amerifrance.guideapi.utils.Gradient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;

import java.awt.*;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class GuideGui extends Screen {

    private final Guide guide;

    private final int GUI_WIDTH = 170;
    private final int GUI_HEIGHT = 215;

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
        Gradient.VERTICAL.draw(left, top, GUI_WIDTH, GUI_HEIGHT, Color.RED.getRGB(), Color.BLUE.getRGB());

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

    public int getGuiWidth() {
        return GUI_WIDTH;
    }

    public int getGuiHeight() {
        return GUI_HEIGHT;
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
