package amerifrance.guideapi.page;

import amerifrance.guideapi.gui.EntryScreen;
import amerifrance.guideapi.util.LogHelper;
import api.IRecipeRenderer;
import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;

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