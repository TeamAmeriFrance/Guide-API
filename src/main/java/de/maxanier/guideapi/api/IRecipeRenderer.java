package de.maxanier.guideapi.api;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.IngredientCycler;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public interface IRecipeRenderer {

    @OnlyIn(Dist.CLIENT)
    void draw(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj, IngredientCycler cycler);

    @OnlyIn(Dist.CLIENT)
    void drawExtras(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj);

    abstract class RecipeRendererBase<T extends Recipe<?>> implements IRecipeRenderer {

        protected T recipe;
        protected List<Component> tooltips = Lists.newArrayList();


        public RecipeRendererBase(T recipe) {
            this.recipe = recipe;
        }


        @Override
        public void drawExtras(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {
            guiBase.renderComponentTooltip(stack, tooltips, mouseX, mouseY);
            tooltips.clear();
        }
    }
}
