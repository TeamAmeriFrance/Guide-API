package amerifrance.guideapi.info;

import amerifrance.guideapi.api.IInfoRenderer;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.util.GuiHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringEscapeUtils;

import java.awt.Color;
import java.util.List;

public class InfoRendererDescription implements IInfoRenderer {

    private final ItemStack stack;
    private final ITextComponent description;
    private boolean tiny;
    private int yOffset;

    public InfoRendererDescription(ItemStack stack, ITextComponent description) {
        this.stack = stack;
        this.description = description;
    }

    @Override
    public void drawInformation(Book book, World world, BlockPos pos, IBlockState state, RayTraceResult rayTrace, EntityPlayer player) {
        if (tiny) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        int scaleMulti = tiny ? 2 : 1;

        GuiHelper.drawItemStack(stack, (resolution.getScaledWidth() / 2 + 55) * scaleMulti, ((resolution.getScaledHeight() / 2 - (tiny ? 20 : 30)) + yOffset) * scaleMulti);

        int y = 0;
        String toDraw = StringEscapeUtils.unescapeJava(description.getFormattedText()).replaceAll("\\t", "     ");
        String[] lines = toDraw.split("\n");
        for (String line : lines) {
            List<String> cutLines = fontRenderer.listFormattedStringToWidth(line, 100 * scaleMulti);
            for (String cut : cutLines) {
                fontRenderer.drawStringWithShadow(cut, (resolution.getScaledWidth() / 2 + 20) * scaleMulti, (((resolution.getScaledHeight() / 2 - 10) - y) * scaleMulti) + yOffset, Color.WHITE.getRGB());
                y -= 10 / scaleMulti;
            }
        }
        if (tiny)
            GlStateManager.popMatrix();
    }

    public InfoRendererDescription setTiny(boolean tiny) {
        this.tiny = tiny;
        return this;
    }

    public InfoRendererDescription setOffsetY(int yOffset) {
        this.yOffset = yOffset;
        return this;
    }
}
