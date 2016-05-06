package amerifrance.guideapi.api;

import amerifrance.guideapi.api.impl.Book;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public class GuideAPI {

    /**
     * The new Book registry. Handles world persistence to avoid de-sync issues.
     */
    public static final IForgeRegistry<Book> BOOKS = PersistentRegistryManager.createRegistry(
            new ResourceLocation("guideapi", "books"),
            Book.class,
            null,
            0,
            Short.MAX_VALUE - 1,
            false,
            new IForgeRegistry.AddCallback<Book>() {
                @Override
                public void onAdd(Book obj, int id, Map<ResourceLocation, ?> slaveset) {
                    // No-op
                }
            },
            new IForgeRegistry.ClearCallback<Book>() {
                @Override
                public void onClear(Map<ResourceLocation, ?> slaveset) {
                    // No-op
                }
            },
            new IForgeRegistry.CreateCallback<Book>() {
                @Override
                public void onCreate(Map<ResourceLocation, ?> slaveset) {
                    // No-op
                }
            }
    );

    /**
     * The item corresponding to the Guide-API books. Access it after the Pre-Init event.
     */
    public static Item guideBook;

    /**
     * Obtains a new ItemStack associated with the provided book.
     *
     * @param book - The book to get an ItemStack for.
     * @return - The ItemStack associated with the provided book.
     */
    public static ItemStack getStackFromBook(Book book) {
        if (BOOKS.containsValue(book))
            return new ItemStack(guideBook, 1, BOOKS.getValues().indexOf(book));

        return null;
    }

    /**
     * Helper method for setting a model for your book.
     *
     * Use if you wish to use a custom model.
     *
     * Only call <b>AFTER</b> you have registered your book.
     *
     * @param book        - Book to set model for
     * @param modelLoc    - Location of the model file
     * @param variantName - Variant to use
     */
    @SideOnly(Side.CLIENT)
    public static void setModel(Book book, ResourceLocation modelLoc, String variantName) {
        ModelLoader.setCustomModelResourceLocation(
                guideBook,
                BOOKS.getValues().indexOf(book),
                new ModelResourceLocation(modelLoc, variantName)
        );
    }

    /**
     * Helper method for setting a model for your book.
     *
     * Use if you wish to use the default model with color.
     *
     * Only call <b>AFTER</b> you have registered your book.
     *
     * @param book - Book to set model for
     */
    @SideOnly(Side.CLIENT)
    public static void setModel(Book book) {
        setModel(book, new ResourceLocation("guideapi", "ItemGuideBook"), "inventory");
    }
}
