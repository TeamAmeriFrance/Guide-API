package amerifrance.guideapi.proxies;

import amerifrance.guideapi.GuideAPI;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.util.serialization.BookCreator;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerBooks() {
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
