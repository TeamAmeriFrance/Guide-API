package amerifrance.guideapi.proxy;

import api.BookEvent;
import api.GuideAPI;
import api.IGuideItem;
import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.gui.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void playSound(SoundEvent sound) {
        Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(sound,1));
    }

    @Override
    public void openEntry(Book book, CategoryAbstract categoryAbstract, EntryAbstract entryAbstract, PlayerEntity player, ItemStack stack) {
        BookEvent.Open event = new BookEvent.Open(book, stack, player);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            player.sendStatusMessage(event.getCanceledText(), true);
            return;
        }

        Minecraft.getInstance().displayGuiScreen(new EntryScreen(book, categoryAbstract, entryAbstract, player, stack));
    }

    @Override
    public void initColors() {
        for (ItemStack bookStack : GuideAPI.getBookToStack().values()) {
            Minecraft.getInstance().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
                IGuideItem guideItem = (IGuideItem) stack.getItem();
                if (guideItem.getBook(stack) != null && tintIndex == 0)
                    return guideItem.getBook(stack).getColor().getRGB();

                return -1;
            }, bookStack.getItem());
        }
    }
}
