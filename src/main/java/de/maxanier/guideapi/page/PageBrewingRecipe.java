package de.maxanier.guideapi.page;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.SubTexture;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.Page;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.api.util.IngredientCycler;
import de.maxanier.guideapi.api.util.TextHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.brewing.BrewingRecipe;

import java.util.ArrayList;
import java.util.List;

public class PageBrewingRecipe extends Page {

    public BrewingRecipe recipe;
    public Ingredient ingredient;
    public Ingredient input;
    public ItemStack output;

    private final IngredientCycler cycler = new IngredientCycler();

    /**
     * Your brewing recipe - what you pass to BrewingRecipeRegistry.addRecipe
     *
     * @param recipe
     */
    public PageBrewingRecipe(BrewingRecipe recipe) {
        this.recipe = recipe;
        this.ingredient = recipe.getIngredient();
        this.input = recipe.getInput();
        this.output = recipe.getOutput();
    }

    /**
     * @param input      - The top slot of our brewing recipe
     * @param ingredient - What goes in the three bottle slots
     * @param output     - Result of recipe
     */
    public PageBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
        this.input = input;
        this.output = output;
        this.ingredient = ingredient;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void draw(MatrixStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        cycler.tick(guiBase.getMinecraft());

        int xStart = guiLeft + 88;
        int yStart = guiTop + 52;

        SubTexture.POTION_GRID.draw(xStart, yStart);

        List<ITextComponent> badTip = new ArrayList<>();
        badTip.add(new TranslationTextComponent("guideapi.text.brewing.error"));

        guiBase.drawCenteredStringWithoutShadow(stack, fontRendererObj, TextHelper.localizeEffect("guideapi.text.brewing.brew"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        //int xmiddle =  guiLeft + guiBase.xSize / 2 - 6;
        int x = xStart + 25;//since item stack is approx 16 wide
        int y = yStart + 1;
        //start input
        int finalX = x;
        int finalY = y;
        cycler.getCycledIngredientStack(ingredient, 0).ifPresent(s -> {
            GuiHelper.drawItemStack(s, finalX, finalY);
        });

        List<ITextComponent> tooltip = null;
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
            tooltip = GuiHelper.getTooltip(ingredient.getMatchingStacks()[0]);

        //the three bottles
        y += 39;
        GuiHelper.drawItemStack(input.getMatchingStacks()[0], x, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
            tooltip = GuiHelper.getTooltip(input.getMatchingStacks()[0]);
        int hSpacing = 24;
        x -= hSpacing;
        y -= 8;
        GuiHelper.drawItemStack(input.getMatchingStacks()[0], x, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
            tooltip = GuiHelper.getTooltip(input.getMatchingStacks()[0]);
        x += hSpacing * 2;
        GuiHelper.drawItemStack(input.getMatchingStacks()[0], x, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
            tooltip = GuiHelper.getTooltip(input.getMatchingStacks()[0]);

        if (output.isEmpty())
            output = new ItemStack(Blocks.BARRIER);

        //start output
        x = xStart + 25;
        y += 31;
        GuiHelper.drawItemStack(output, x, y);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, x, y, 15, 15))
            tooltip = output.getItem() == Item.getItemFromBlock(Blocks.BARRIER) ? badTip : GuiHelper.getTooltip(output);

        if (output.getItem() == Item.getItemFromBlock(Blocks.BARRIER))
            guiBase.drawCenteredStringWithoutShadow(stack, fontRendererObj, TextHelper.localizeEffect("guideapi.text.brewing.error"), guiLeft + guiBase.xSize / 2, guiTop + 4 * guiBase.ySize / 6, 0xED073D);

        if (tooltip != null)
            guiBase.func_238654_b_(stack, tooltip, mouseX, mouseY);
    }


}
