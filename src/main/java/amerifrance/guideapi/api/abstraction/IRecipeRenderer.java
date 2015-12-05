package amerifrance.guideapi.api.abstraction;

import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.gui.GuiBase;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface IRecipeRenderer {

    @SideOnly(Side.CLIENT)
    void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj);

    @SideOnly(Side.CLIENT)
    void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj);

    abstract class RecipeRendererBase<T extends IRecipe> implements IRecipeRenderer {

        protected T recipe;
        protected List<String> tooltips = Lists.newArrayList();

        public RecipeRendererBase(T recipe) {
            this.recipe = recipe;
        }

        @Override
        public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
            guiBase.drawHoveringText(tooltips, mouseX, mouseY);
            tooltips.clear();
        }
    }
}
