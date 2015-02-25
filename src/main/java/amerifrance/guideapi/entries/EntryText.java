package amerifrance.guideapi.entries;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.EntryBase;
import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import amerifrance.guideapi.objects.abstraction.IPage;
import amerifrance.guideapi.util.GuiHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;

import java.util.List;

public class EntryText extends EntryBase {

    public EntryText(List<IPage> pageList, String unlocEntryName) {
        super(pageList, unlocEntryName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        if (GuiHelper.isMouseBetween(mouseX, mouseY, entryX, entryY, entryWidth, entryHeight)) {
            fontRenderer.drawString(getLocalizedName(), entryX, entryY - 2, 0x423EBC);
        } else {
            fontRenderer.drawString(getLocalizedName(), entryX, entryY, 0);
        }
    }
}
