package amerifrance.guideapi.page;

import amerifrance.guideapi.gui.EntryScreen;
import amerifrance.guideapi.util.LogHelper;
import amerifrance.guideapi.api.IRecipeRenderer;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Supplier;

public class PageJsonRecipe extends PageIRecipe {

    @Nonnull
    private final ResourceLocation recipeId;
    @Nonnull
    private final Function<IRecipe<?>,IRecipeRenderer> recipeRendererSupplier;

    public PageJsonRecipe(ResourceLocation recipeId) {
        this(recipeId,PageIRecipe::getRenderer);
    }

    public PageJsonRecipe(ResourceLocation recipeId, Function<IRecipe<?>,IRecipeRenderer> rendererSupplier){
        super(null,null);
        this.recipeId = recipeId;
        this.recipeRendererSupplier=rendererSupplier;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onInit(Book book, CategoryAbstract category, EntryAbstract entry, PlayerEntity player, ItemStack bookStack, EntryScreen guiEntry) {
        super.onInit(book, category, entry, player, bookStack, guiEntry);
        if(recipe==null){
            this.recipe = Minecraft.getInstance().getConnection() ==null ? null : Minecraft.getInstance().getConnection().getRecipeManager().getRecipe(recipeId).orElse(null);
            if(recipe==null){
                LogHelper.error("Cannot find recipe "+recipeId.toString());
            }
            else{
                if(iRecipeRenderer==null){
                    iRecipeRenderer = recipeRendererSupplier.apply(recipe);
                    if(iRecipeRenderer==null){
                        LogHelper.error("Did not get renderer for recipe type "+recipe.getClass().toString()+" for recipe "+recipeId.toString());
                    }
                }
            }
        }
        this.isValid = recipe != null && iRecipeRenderer != null;
    }
}