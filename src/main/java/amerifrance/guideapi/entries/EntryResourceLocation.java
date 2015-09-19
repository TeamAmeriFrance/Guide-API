package amerifrance.guideapi.entries;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.IPage;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.base.EntryBase;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.List;

public class EntryResourceLocation extends EntryBase {

    public ResourceLocation image;

    public EntryResourceLocation(List<IPage> pageList, String unlocEntryName, ResourceLocation resourceLocation, boolean unicode) {
        super(pageList, unlocEntryName, unicode);

        this.image = resourceLocation;
    }

    public EntryResourceLocation(List<IPage> pageList, String unlocEntryName, ResourceLocation resourceLocation) {
        this(pageList, unlocEntryName, resourceLocation, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        GuiHelper.drawSizedIconWithoutColor(entryX + 2, entryY, 16, 16, 1F);
    }
}
