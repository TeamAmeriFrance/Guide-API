package amerifrance.guideapi.proxies;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.GuideAPIItems;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.util.InventoryRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ClientProxy extends CommonProxy {

    private static InventoryRenderHelper renderHelper = new InventoryRenderHelper(ModInformation.TEXLOC);

    @Override
    public void playSound(ResourceLocation sound) {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(sound, 1.0F));
    }

    @Override
    public void openEntry(Book book, CategoryAbstract categoryAbstract, EntryAbstract entryAbstract, EntityPlayer player, ItemStack stack) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiEntry(book, categoryAbstract, entryAbstract, player, stack));
    }

    @Override
    public void initRenders() {
        renderHelper.itemRenderAll(GuideAPIItems.guideBook);
        renderHelper.itemRenderAll(GuideAPIItems.lostPage);
    }
}
