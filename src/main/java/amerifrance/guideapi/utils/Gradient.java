package amerifrance.guideapi.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import org.lwjgl.opengl.GL11;

public enum Gradient {
    VERTICAL {
        @Override
        void appendBuffer(BufferBuilder buffer, GradientSection start, GradientSection end, int left, int top, int right, int bottom) {
            buffer.vertex((double) right, (double) top, 0D).color(start.r, start.g, start.b, start.a).next();
            buffer.vertex((double) left, (double) top, 0D).color(start.r, start.g, start.b, start.a).next();
            buffer.vertex((double) left, (double) bottom, 0D).color(end.r, end.g, end.b, end.a).next();
            buffer.vertex((double) right, (double) bottom, 0D).color(end.r, end.g, end.b, end.a).next();
        }
    },
    HORIZONTAL {
        @Override
        void appendBuffer(BufferBuilder buffer, GradientSection start, GradientSection end, int left, int top, int right, int bottom) {
            buffer.vertex((double) right, (double) top, 0D).color(end.r, end.g, end.b, end.a).next();
            buffer.vertex((double) left, (double) top, 0D).color(start.r, start.g, start.b, start.a).next();
            buffer.vertex((double) left, (double) bottom, 0D).color(start.r, start.g, start.b, start.a).next();
            buffer.vertex((double) right, (double) bottom, 0D).color(end.r, end.g, end.b, end.a).next();
        }
    },
    ;

    public void draw(int left, int top, int width, int height, int startColor, int endColor) {
        int right = left + width;
        int bottom = top + height;

        GradientSection start = new GradientSection(startColor);
        GradientSection end = new GradientSection(endColor);

        GlStateManager.disableTexture();
        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, VertexFormats.POSITION_COLOR);
        appendBuffer(buffer, start, end, left, top, right, bottom);
        tessellator.draw();

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableTexture();
    }

    abstract void appendBuffer(BufferBuilder buffer, GradientSection start, GradientSection end, int left, int top, int right, int bottom);

    private static class GradientSection {
        private final float a, r, g, b;

        public GradientSection(int color) {
            this.a = shift(color, 24);
            this.r = shift(color, 16);
            this.g = shift(color, 8);
            this.b = (float) (color & 255) / 255F;
        }

        private static float shift(int color, int shift) {
            return (float) (color >> shift & 255) / 255F;
        }
    }
}
