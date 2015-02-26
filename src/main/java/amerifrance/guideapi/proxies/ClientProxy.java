package amerifrance.guideapi.proxies;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.GuideRegistry;
import amerifrance.guideapi.test.TestBooks;
import amerifrance.guideapi.util.serialization.BookCreator;

import com.google.gson.GsonBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.FileFilterUtils;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerBooks() {
        TestBooks.setTestBooks();
    }

    @Override
    public void registerJsonBooks(GsonBuilder gsonBuilder) {
        File folder = new File(GuideAPI.getConfigDir().getPath() + "/books");
        folder.mkdir();
        File[] files = folder.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        for (File file : files) {
            GuideRegistry.registerBook(BookCreator.createBookFromJson(gsonBuilder, file));
        }
    }

    @Override
    public void playSound(ResourceLocation sound) {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(sound, 1.0F));
    }
}
