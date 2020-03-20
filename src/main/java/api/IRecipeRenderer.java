package api;

import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public interface IRecipeRenderer {

    @OnlyIn(Dist.CLIENT)
    void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj);

    @OnlyIn(Dist.CLIENT)
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
