package amerifrance.guideapi.page.reciperenderer;

import amerifrance.guideapi.GuideMod;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.IRecipeRenderer.RecipeRendererBase;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class BasicRecipeRenderer<T extends IRecipe> extends RecipeRendererBase<T> {

    private long lastCycle = -1;
    private int cycleIdx = 0;
    private Random rand = new Random();

    public BasicRecipeRenderer(T recipe) {
        super(recipe);
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {
        Minecraft mc = Minecraft.getMinecraft();

        long time = mc.theWorld.getTotalWorldTime();
        if (lastCycle < 0 || lastCycle < time - 20) {
            if (lastCycle > 0) {
                cycleIdx++;
                cycleIdx = Math.max(0, cycleIdx);
            }
            lastCycle = mc.theWorld.getTotalWorldTime();
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(GuideMod.GUITEXLOC + "recipe_elements.png"));
        guiBase.drawTexturedModalRect(guiLeft + 42, guiTop + 53, 0, 0, 105, 65);
        guiBase.drawCenteredString(fontRendererObj, getRecipeName(), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int outputX = (5 * 18) + (guiLeft + guiBase.xSize / 7);
        int outputY = (2 * 18) + (guiTop + guiBase.xSize / 5);
        GuiHelper.drawItemStack(recipe.getRecipeOutput(), outputX, outputY);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, outputX, outputY, 15, 15)) {
            tooltips = GuiHelper.getTooltip(recipe.getRecipeOutput());
        }
    }

    protected int getRandomizedCycle(int index, int max) {
        rand.setSeed(index);
        return (index + rand.nextInt(max) + cycleIdx) % max;
    }

    protected String getRecipeName() {
        return TextHelper.localizeEffect("text.shaped.crafting");
    }
}
