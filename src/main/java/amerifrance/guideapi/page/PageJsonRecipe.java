package amerifrance.guideapi.page;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class PageJsonRecipe extends PageIRecipe {

    private final ResourceLocation recipeId;

    public PageJsonRecipe(ResourceLocation recipeId) {
        super(null, null);

        this.recipeId = recipeId;
    }

    public void init() {
        this.recipe = Minecraft.getInstance().getConnection().getRecipeManager().getRecipe(recipeId).get(); //TODO
        this.iRecipeRenderer = getRenderer(recipe);
        this.isValid = recipe != null && iRecipeRenderer != null;
    }
}