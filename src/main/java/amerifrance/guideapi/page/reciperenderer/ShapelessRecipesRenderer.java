package amerifrance.guideapi.page.reciperenderer;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ShapelessRecipesRenderer extends BasicRecipeRenderer<ShapelessRecipes> {

    public ShapelessRecipesRenderer(ShapelessRecipes recipe) {
        super(recipe);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int i = 3 * y + x;
                int stackX = (x + 1) * 17 + (guiLeft + 27) + x;
                int stackY = (y + 1) * 17 + (guiTop + 38) + y;
                if (i < recipe.getIngredients().size()) {
                    Ingredient ingredient = recipe.getIngredients().get(i);
                    List<ItemStack> list = Arrays.asList(ingredient.getMatchingStacks());
                    List<ItemStack> removeList = Lists.newArrayList();
                    for (Iterator<ItemStack> iterator = list.iterator(); iterator.hasNext(); ) {
                        ItemStack testStack = iterator.next();
                        if (testStack == null || testStack.getItem() == null)
                            removeList.add(testStack);
                    }
                    list.removeAll(removeList);
                    if (!list.isEmpty()) {
                        ItemStack stack = list.get(getRandomizedCycle(x + (y * 3), list.size()));
                        if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                            stack = getNextItem(stack, x);

                        GuiHelper.drawItemStack(stack, stackX, stackY);
                        if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15))
                            tooltips = GuiHelper.getTooltip(stack);
                    }
                }
            }
        }
    }

    @Override
    protected String getRecipeName() {
        return TextHelper.localizeEffect("text.shapeless.crafting");
    }
}
