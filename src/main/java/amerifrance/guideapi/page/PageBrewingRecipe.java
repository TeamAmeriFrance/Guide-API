package amerifrance.guideapi.page;

import amerifrance.guideapi.GuideMod;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import lombok.EqualsAndHashCode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class PageBrewingRecipe extends Page {

    public BrewingRecipe recipe;
    public ItemStack ingredient;
    public ItemStack input;
    public ItemStack output;

   /**
    * Your brewing recipe - what you pass to BrewingRecipeRegistry.addRecipe
    * @param recipe
    */
    public PageBrewingRecipe(BrewingRecipe recipe) {
        this.recipe = recipe;
        this.ingredient = recipe.getIngredient();
        this.input = recipe.getInput();
        this.output = recipe.getOutput();
    }
    /**
     * 
     * @param input - The top slot of our brewing recipe
     * @param ingredient - What goes in the three bottle slots
     * @param output - Result of recipe
     */
    public PageBrewingRecipe(ItemStack input, ItemStack ingredient, ItemStack output) {
        this.input = input;
        this.output = ingredient;
        this.ingredient = output;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(GuideMod.ID, "textures/gui/recipe_elements.png"));
        guiBase.drawTexturedModalRect(guiLeft + 42, guiTop + 53, 0, 65, 105, 65);

        List<String> badTip = new ArrayList<String>();
        badTip.add(TextHelper.localizeEffect("text.brewing.error"));

        guiBase.drawCenteredString(fontRendererObj, TextHelper.localizeEffect("text.brewing.brew"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int xmiddle =  guiLeft + guiBase.xSize / 2 - 6;
        int x = xmiddle;//since item stack is approx 16 wide
        int y = guiTop + 32;
        //start input
        GuiHelper.drawItemStack(ingredient, x, y);

        List<String> tooltip = null;
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
            tooltip = GuiHelper.getTooltip(input);
        
        //the three bottles
        int hSpacing = 38;
        y += 70;
        GuiHelper.drawItemStack(input, x, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
          tooltip = GuiHelper.getTooltip(input);
        x -= hSpacing;
        y -= 20;
        GuiHelper.drawItemStack(input, x, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
          tooltip = GuiHelper.getTooltip(input);
        x += hSpacing*2;
        GuiHelper.drawItemStack(input, x, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
          tooltip = GuiHelper.getTooltip(input);
        
        
        if (output == null)
            output = new ItemStack(Blocks.BARRIER);

        //start output
        x = xmiddle;
        y += 52;
        GuiHelper.drawItemStack(output, x, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
          tooltip = output.getItem() == Item.getItemFromBlock(Blocks.BARRIER) ? badTip : GuiHelper.getTooltip(output);

        if (output.getItem() == Item.getItemFromBlock(Blocks.BARRIER))
            guiBase.drawCenteredString(fontRendererObj, TextHelper.localizeEffect("text.furnace.error"), guiLeft + guiBase.xSize / 2, guiTop + 4 * guiBase.ySize / 6, 0xED073D);

        if (tooltip != null)
            guiBase.drawHoveringText(tooltip, mouseX, mouseY);
    }
}
