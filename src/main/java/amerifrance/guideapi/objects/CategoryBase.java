package amerifrance.guideapi.objects;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import amerifrance.guideapi.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class CategoryBase extends AbstractCategory {

    public ItemStack stack;

    public CategoryBase(List<AbstractEntry> entryList, String unlocCategoryName, ItemStack itemstack) {
        super(entryList, unlocCategoryName);
        this.stack = itemstack;
    }

    @Override
    public void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
        if (drawOnLeft) {
            guiBase.mc.getTextureManager().bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "category_left.png"));
            GuiHelper.drawIconWithColor(categoryX - 5, categoryY + 2, 102, 12, guiBase.publicZLevel, book.color());
        } else {
            guiBase.mc.getTextureManager().bindTexture(new ResourceLocation(ModInformation.GUITEXLOC + "category_right.png"));
            GuiHelper.drawIconWithColor(categoryX - 80, categoryY + 2, 102, 12, guiBase.publicZLevel, book.color());
        }
    }

    @Override
    public void drawExtras(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
        GuiHelper.drawItemStack(this.stack, categoryX, categoryY);
        if (canSee(guiBase.player, guiBase.bookStack) && GuiHelper.isMouseBetween(mouseX, mouseY, categoryX, categoryY, categoryWidth, categoryHeight)) {
            guiBase.drawHoveringText(getTooltip(), mouseX, mouseY, Minecraft.getMinecraft().fontRenderer);
        }
    }

    @Override
    public boolean canSee(EntityPlayer player, ItemStack bookStack) {
        return true;
    }

    @Override
    public void onLeftClicked(Book book, int mouseX, int mouseY, EntityPlayer player, ItemStack bookStack) {
        System.out.println(getLocalizedName() + "Left Clicked");
        Minecraft.getMinecraft().displayGuiScreen(new GuiCategory(book, this, player, bookStack));
    }

    @Override
    public void onRightClicked(Book book, int mouseX, int mouseY, EntityPlayer player, ItemStack bookStack) {
        System.out.println(getLocalizedName() + "Right Clicked");
    }
}
