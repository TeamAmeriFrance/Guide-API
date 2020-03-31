package amerifrance.guideapi.entry;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class EntryResourceLocation extends Entry {

    public ResourceLocation image;

    public EntryResourceLocation(List<IPage> pageList, String name, ResourceLocation resourceLocation) {
        super(pageList, name);
        this.image = resourceLocation;
    }

    public EntryResourceLocation(String name, ResourceLocation image) {
        super(name);
        this.image = image;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        Minecraft.getInstance().getTextureManager().bindTexture(image);
        GuiHelper.drawSizedIconWithoutColor(entryX + 2, entryY, 16, 16, 1F);

        super.drawExtras(book, category, entryX, entryY, entryWidth, entryHeight, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryResourceLocation)) return false;
        if (!super.equals(o)) return false;

        EntryResourceLocation that = (EntryResourceLocation) o;

        return image != null ? image.equals(that.image) : that.image == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
