package amerifrance.guideapi.page.reciperenderer;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class ShapelessOreRecipeRenderer extends BasicRecipeRenderer<ShapelessOreRecipe> {

    public ShapelessOreRecipeRenderer(ShapelessOreRecipe recipe) {
        super(recipe);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int i = 3 * y + x;
                if (i >= recipe.getRecipeSize()) {
                } else {
                    int stackX = (x + 1) * 17 + (guiLeft + 27) + x;
                    int stackY = (y + 1) * 17 + (guiTop + 38) + y;
                    Object component = recipe.getInput().get(i);
                    if (component instanceof ItemStack) {
                        ItemStack input = (ItemStack) component;
                        if (input.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                            input = getNextItem(input, x);

                        GuiHelper.drawItemStack(input, stackX, stackY);
                        if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15))
                            tooltips = GuiHelper.getTooltip(input);
                    } else {
                        List<ItemStack> list = (List<ItemStack>) component;
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
    }

    @Override
    protected String getRecipeName() {
        return TextHelper.localizeEffect("text.shapeless.crafting");
    }
}
