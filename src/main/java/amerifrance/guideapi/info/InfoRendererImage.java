package amerifrance.guideapi.info;

import api.IInfoRenderer;
import api.impl.Book;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class InfoRendererImage implements IInfoRenderer {

    private final ResourceLocation image;
    private final int imageX;
    private final int imageY;
    private final int imageWidth;
    private final int imageHeight;

    public InfoRendererImage(ResourceLocation image, int imageX, int imageY, int imageWidth, int imageHeight) {
        this.image = image;
        this.imageX = imageX;
        this.imageY = imageY;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    public void drawInformation(Book book, World world, BlockPos pos, BlockState state, RayTraceResult rayTrace, PlayerEntity player) {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        Minecraft.getMinecraft().renderEngine.bindTexture(image);
        AbstractGui.drawModalRectWithCustomSizedTexture(resolution.getScaledWidth() / 2 + 20, resolution.getScaledHeight() / 2 - imageHeight / 2, imageX, imageY, imageWidth, imageHeight, imageWidth, imageHeight);
    }
}
