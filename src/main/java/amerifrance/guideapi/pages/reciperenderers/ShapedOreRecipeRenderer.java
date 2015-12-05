package amerifrance.guideapi.pages.reciperenderers;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.abstraction.EntryAbstract;
import amerifrance.guideapi.api.base.Book;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;

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
                        GuiHelper.drawItemStack((ItemStack) component, stackX, stackY);
                        if (GuiHelper.isMouseBetween(mouseX, mouseY, stackX, stackY, 15, 15)) {
                            tooltips = GuiHelper.getTooltip((ItemStack) component);
                        }
                    } else if (component instanceof ArrayList<?>) {
                        ArrayList<ItemStack> list = (ArrayList<ItemStack>) component;
                        if (!list.isEmpty()) {
                            ItemStack stack = list.get(getRandomizedCycle(x + (y * width), list.size()));
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
