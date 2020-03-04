package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IPage {

    @OnlyIn(Dist.CLIENT)
    void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj);

    @OnlyIn(Dist.CLIENT)
    void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj);

    boolean canSee(Book book, CategoryAbstract category, EntryAbstract entry, PlayerEntity player, ItemStack bookStack, GuiEntry guiEntry);

    @OnlyIn(Dist.CLIENT)
    void onLeftClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, PlayerEntity player, GuiEntry guiEntry);

    @OnlyIn(Dist.CLIENT)
    void onRightClicked(Book book, CategoryAbstract category, EntryAbstract entry, int mouseX, int mouseY, PlayerEntity player, GuiEntry guiEntry);

    @OnlyIn(Dist.CLIENT)
    void onInit(Book book, CategoryAbstract category, EntryAbstract entry, PlayerEntity player, ItemStack bookStack, GuiEntry guiEntry);
}
