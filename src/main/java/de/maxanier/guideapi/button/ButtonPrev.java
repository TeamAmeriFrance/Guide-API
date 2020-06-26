package de.maxanier.guideapi.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.button.ButtonGuideAPI;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class ButtonPrev extends ButtonGuideAPI {

    public ButtonPrev(int widthIn, int heightIn, Button.IPressable onPress, BaseScreen guiBase) {
        super(widthIn, heightIn, 18, 10, new TranslationTextComponent("guideapi.button.prev"), onPress, guiBase);
    }

    @Override
    public void func_230431_b_(MatrixStack stack, int mouseX, int mouseY, float partialTicks) { //renderButton
        if (this.field_230694_p_) { //visible
            RenderHelper.enableStandardItemLighting();
            RenderSystem.enableBlend();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableLighting();
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(GuideMod.ID, "textures/gui/book_colored.png"));
            if (GuiHelper.isMouseBetween(mouseX, mouseY, field_230690_l_, field_230691_m_, field_230688_j_, field_230689_k_)) { //x,y,width,height
                this.func_238474_b_(stack, field_230690_l_, field_230691_m_ + 1, 47, 214, 18, 10); //blit
                GuiUtils.drawHoveringText(stack, getHoveringText(), mouseX, mouseY, guiBase.field_230708_k_, guiBase.field_230709_l_, -1, Minecraft.getInstance().fontRenderer);
            } else {
                this.func_238474_b_(stack, field_230690_l_, field_230691_m_, 24, 214, 18, 10);
            }
            GlStateManager.disableBlend();
            RenderHelper.disableStandardItemLighting();
        }
    }


}