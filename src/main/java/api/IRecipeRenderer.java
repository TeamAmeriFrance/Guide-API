package api;

import amerifrance.guideapi.gui.BaseScreen;
import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.List;
import java.util.stream.Collectors;

public interface IRecipeRenderer {

    @OnlyIn(Dist.CLIENT)
    void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj);

    @OnlyIn(Dist.CLIENT)
    void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj);

    abstract class RecipeRendererBase<T extends IRecipe> implements IRecipeRenderer {

        protected T recipe;
        protected List<ITextComponent> tooltips = Lists.newArrayList();

        public RecipeRendererBase(T recipe) {
            this.recipe = recipe;
        }

        @Override
        public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
            guiBase.drawHoveringTextComponents(tooltips, mouseX, mouseY);
            tooltips.clear();
        }
    }
}
