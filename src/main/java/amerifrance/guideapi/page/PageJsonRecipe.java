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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PageJsonRecipe extends PageIRecipe {

    private final ResourceLocation recipeId;

    public PageJsonRecipe(ResourceLocation recipeId) {
        super(null, null);
        this.recipeId = recipeId;
    }

    public PageJsonRecipe(ResourceLocation recipeId, IRecipeRenderer render){
        super(null,render);
        this.recipeId = recipeId;
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
                if(iRecipeRenderer!=null){
                    iRecipeRenderer = getRenderer(recipe);
                }
            }
        }
        this.isValid = recipe != null && iRecipeRenderer != null;
    }
}