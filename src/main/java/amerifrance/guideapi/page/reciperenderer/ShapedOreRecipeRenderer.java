package amerifrance.guideapi.page.reciperenderer;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// TODO: Fix rendering of recipe
public class ShapedOreRecipeRenderer extends BasicRecipeRenderer<ShapedOreRecipe> {

    private static final Field _width = ReflectionHelper.findField(ShapedOreRecipe.class, "width");
    private static final Field _height = ReflectionHelper.findField(ShapedOreRecipe.class, "height");

    private int width, height;

    public ShapedOreRecipeRenderer(ShapedOreRecipe recipe) {
        super(recipe);
        try {
            this.width = _width.getInt(recipe);
            this.height = _height.getInt(recipe);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int stackX = (x + 1) * 17 + (guiLeft + 29);
                int stackY = (y + 1) * 17 + (guiTop + 40);
                Object component = recipe.getInput()[y * width + x];
                if (component != null) {
                    if (component instanceof ItemStack) {
                        ItemStack input = (ItemStack) component;
                        if (input.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                            input.setItemDamage(0);

                        GuiHelper.drawItemStack((ItemStack) component, stackX, stackY);
                        if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                            tooltips = GuiHelper.getTooltip((ItemStack) component);
                        }
                    } else {
                        List<ItemStack> list = (List<ItemStack>) component;
                        if (!list.isEmpty()) {
                            ItemStack stack = list.get(getRandomizedCycle(x + (y * 3), list.size()));
                            if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                                NonNullList<ItemStack> subItems = NonNullList.func_191196_a();
                                stack.getItem().getSubItems(stack.getItem(), stack.getItem().getCreativeTab(), subItems);
                                stack = subItems.get(getRandomizedCycle(x, subItems.size()));
                            }
                            GuiHelper.drawItemStack(stack, stackX, stackY);
                            if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                                tooltips = GuiHelper.getTooltip(stack);
                            }
                        }
                    }
                }
            }
        }
    }
}
