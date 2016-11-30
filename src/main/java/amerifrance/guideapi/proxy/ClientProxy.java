package amerifrance.guideapi.proxy;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.IGuideItem;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.gui.GuiEntry;
import amerifrance.guideapi.util.AnnotationHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public class ClientProxy extends CommonProxy {

    @Override
    public void handleModels() {
        for (Pair<Book, IGuideBook> guide : AnnotationHandler.BOOK_CLASSES)
            guide.getRight().handleModel(GuideAPI.getStackFromBook(guide.getLeft()));
    }

    @Override
    public void playSound(SoundEvent sound) {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(sound, 1.0F));
    }

    @Override
    public void openEntry(Book book, CategoryAbstract categoryAbstract, EntryAbstract entryAbstract, EntityPlayer player, ItemStack stack) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiEntry(book, categoryAbstract, entryAbstract, player, stack));
    }

    @Override
    public void initColors() {
        for (ItemStack bookStack : GuideAPI.getBookToStack().values()) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
                @Override
                public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                    IGuideItem guideItem = (IGuideItem) stack.getItem();
                    if (guideItem.getBook(stack) != null && tintIndex == 0)
                        return guideItem.getBook(stack).getColor().getRGB();

                    return 16777215;
                }
            }, bookStack.getItem());
        }
    }
}
