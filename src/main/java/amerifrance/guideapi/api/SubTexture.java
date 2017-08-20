package amerifrance.guideapi.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SubTexture {

    private static final ResourceLocation RECIPE_ELEMENTS = new ResourceLocation("guideapi", "textures/gui/recipe_elements.png");
    // Grids
    public static final SubTexture CRAFTING_GRID = new SubTexture(RECIPE_ELEMENTS, 0, 48, 102, 56);
    public static final SubTexture FURNACE_GRID = new SubTexture(RECIPE_ELEMENTS, 0, 104, 68, 28);
    public static final SubTexture POTION_GRID = new SubTexture(RECIPE_ELEMENTS, 0, 132, 68, 81);

    // Singletons
    public static final SubTexture SINGLE_SLOT = new SubTexture(RECIPE_ELEMENTS, 0, 28, 20, 20);

    // Large buttons
    public static final SubTexture LARGE_BUTTON = new SubTexture(RECIPE_ELEMENTS, 0, 0, 55, 18);
    public static final SubTexture LARGE_BUTTON_HOVER = new SubTexture(RECIPE_ELEMENTS, 55, 0, 55, 18);
    public static final SubTexture LARGE_BUTTON_PRESS = new SubTexture(RECIPE_ELEMENTS, 110, 0, 55, 18);

    // Small buttons
    public static final SubTexture SMALL_BUTTON_BLANK = new SubTexture(RECIPE_ELEMENTS, 0, 0, 10, 10);
    public static final SubTexture SMALL_BUTTON_BLANK_HOVER = new SubTexture(RECIPE_ELEMENTS, 40, 0, 10, 10);
    public static final SubTexture SMALL_BUTTON_BLANK_PRESS = new SubTexture(RECIPE_ELEMENTS, 80, 0, 10, 10);
    public static final SubTexture SMALL_BUTTON_CRAFT = new SubTexture(RECIPE_ELEMENTS, 10, 0, 10, 10);
    public static final SubTexture SMALL_BUTTON_CRAFT_HOVER = new SubTexture(RECIPE_ELEMENTS, 50, 0, 10, 10);
    public static final SubTexture SMALL_BUTTON_CRAFT_PRESS = new SubTexture(RECIPE_ELEMENTS, 90, 0, 10, 10);
    public static final SubTexture SMALL_BUTTON_FURNACE = new SubTexture(RECIPE_ELEMENTS, 20, 0, 10, 10);
    public static final SubTexture SMALL_BUTTON_FURNACE_HOVER = new SubTexture(RECIPE_ELEMENTS, 60, 0, 10, 10);
    public static final SubTexture SMALL_BUTTON_FURNACE_PRESS = new SubTexture(RECIPE_ELEMENTS, 100, 0, 10, 10);
    public static final SubTexture SMALL_BUTTON_POTION = new SubTexture(RECIPE_ELEMENTS, 30, 0, 10, 10);
    public static final SubTexture SMALL_BUTTON_POTION_HOVER = new SubTexture(RECIPE_ELEMENTS, 70, 0, 10, 10);
    public static final SubTexture SMALL_BUTTON_POTION_PRESS = new SubTexture(RECIPE_ELEMENTS, 110, 0, 10, 10);

    private final ResourceLocation textureLocation;
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;

    public SubTexture(ResourceLocation textureLocation, int xPos, int yPos, int width, int height) {
        this.textureLocation = textureLocation;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    @SideOnly(Side.CLIENT)
    public void draw(int drawX, int drawY, double zLevel) {
        final float someMagicValueFromMojang = 0.00390625F;

        Minecraft.getMinecraft().renderEngine.bindTexture(textureLocation);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double) drawX, (double) (drawY + height), zLevel).tex((double) ((float) xPos * someMagicValueFromMojang), (double) ((float) (yPos + height) * someMagicValueFromMojang)).endVertex();
        vertexbuffer.pos((double) (drawX + width), (double) (drawY + height), zLevel).tex((double) ((float) (xPos + width) * someMagicValueFromMojang), (double) ((float) (yPos + height) * someMagicValueFromMojang)).endVertex();
        vertexbuffer.pos((double) (drawX + width), (double) drawY, zLevel).tex((double) ((float) (xPos + width) * someMagicValueFromMojang), (double) ((float) yPos * someMagicValueFromMojang)).endVertex();
        vertexbuffer.pos((double) drawX, (double) drawY, zLevel).tex((double) ((float) xPos * someMagicValueFromMojang), (double) ((float) yPos * someMagicValueFromMojang)).endVertex();
        tessellator.draw();
    }

    @SideOnly(Side.CLIENT)
    public void draw(int drawX, int drawY) {
        draw(drawX, drawY, 0.1D);
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    public int getDrawX() {
        return xPos;
    }

    public int getDrawY() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
