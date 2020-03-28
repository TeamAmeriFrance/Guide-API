package amerifrance.guideapi.api;

import amerifrance.guideapi.gui.BaseScreen;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public interface IRecipeRenderer {

    @OnlyIn(Dist.CLIENT)
    void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj);

    @OnlyIn(Dist.CLIENT)
    void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj);

    abstract class RecipeRendererBase<T extends IRecipe<?>> implements IRecipeRenderer {

        protected T recipe;
        protected List<ITextComponent> tooltips = Lists.newArrayList();

        private long lastCycle = -1;
        private int cycleIdx = 0;
        private Random rand = new Random();

        public RecipeRendererBase(T recipe) {
            this.recipe = recipe;
        }

        @Override
        public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
            Minecraft mc = guiBase.getMinecraft();
            long time = mc.world.getGameTime();
            if (lastCycle < 0 || lastCycle < time - 20) {
                if (lastCycle > 0) {
                    cycleIdx++;
                    cycleIdx = Math.max(0, cycleIdx);
                }
                lastCycle = mc.world.getGameTime();
            }
        }

        /**
         * Retrieves a itemstack that matches the ingredient.
         * Cycles though all matching stacks.
         * Must call {@link IRecipeRenderer#draw(Book, CategoryAbstract, EntryAbstract, int, int, int, int, BaseScreen, FontRenderer)} if you are overriding it.
         * @param ingredient The ingredient
         * @param index An "unique" id for this ingredient, so multiple ingredients can be cycled independently
         * @return Optional. Can be empty if ingredient is invalid and has no matching stacks
         */
        protected Optional<ItemStack> getCycledIngredientStack(Ingredient ingredient, int index){
            ItemStack[] itemStacks = ingredient.getMatchingStacks();
            if(itemStacks.length>0){
                rand.setSeed(index);
                int id = (index + rand.nextInt(itemStacks.length) + cycleIdx) %itemStacks.length;
                return Optional.of(itemStacks[id]);
            }
            return Optional.empty();
        }

        @Override
        public void drawExtras(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
            guiBase.drawHoveringTextComponents(tooltips, mouseX, mouseY);
            tooltips.clear();
        }
    }
}
