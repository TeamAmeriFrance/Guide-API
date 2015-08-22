package amerifrance.guideapi.api.base;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiCategory;
import amerifrance.guideapi.gui.GuiHome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CategoryBase extends CategoryAbstract {

    public CategoryBase(List<EntryAbstract> entryList, String unlocCategoryName) {
        super(entryList, unlocCategoryName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, int categoryX, int categoryY, int categoryWidth, int categoryHeight, int mouseX, int mouseY, GuiBase guiBase, boolean drawOnLeft, RenderItem renderItem) {
    }

    @Override
    public boolean canSee(EntityPlayer player, ItemStack bookStack) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onLeftClicked(Book book, int mouseX, int mouseY, EntityPlayer player, ItemStack bookStack) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiCategory(book, this, player, bookStack));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRightClicked(Book book, int mouseX, int mouseY, EntityPlayer player, ItemStack bookStack) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onInit(Book book, GuiHome guiHome, EntityPlayer player, ItemStack bookStack) {
    }
}
