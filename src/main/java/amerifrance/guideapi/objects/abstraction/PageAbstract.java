package amerifrance.guideapi.objects.abstraction;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.objects.Book;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class PageAbstract {

    @SideOnly(Side.CLIENT)
    public abstract void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer);

    @SideOnly(Side.CLIENT)
    public abstract void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer);

    public abstract boolean canSee(EntityPlayer player, ItemStack bookStack);

    public abstract void onLeftClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, EntityPlayer player, GuiEntry guiEntry);

    public abstract void onRightClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, EntityPlayer player, GuiEntry guiEntry);
}
