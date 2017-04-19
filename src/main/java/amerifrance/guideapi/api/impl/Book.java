package amerifrance.guideapi.api.impl;

import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.util.TextHelper;
import lombok.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private static final String GUITEXLOC = "guideapi:textures/gui/";

    /** A list of categories contained in this book */
    private List<CategoryAbstract> categoryList = new ArrayList<CategoryAbstract>();
    /** The unlocalized display title of this book. Drawn at the top of the home page. */
    private String title = "item.GuideBook.name";
    /** The unlocalized welcome message. Displayed on the home page. */
    private String welcomeMessage = title;
    /** The unlocalized display title of the book item. */
    private String displayName = title;
    /** The author of this book. Usually your mod name/id. */
    private String author;
    /** The texture to draw for your page. */
    private ResourceLocation pageTexture = new ResourceLocation(GUITEXLOC + "book_colored.png");
    /** The texture to draw around your page. */
    private ResourceLocation outlineTexture = new ResourceLocation(GUITEXLOC + "book_greyscale.png");
    /** Whether or not your book has a custom model. If false, a colored default model will be used. */
    private boolean customModel;
    /** The color of your book. Used for your model (if {@link #customModel} is false) and for the {@link #outlineTexture} */
    private Color color = new Color(171, 70, 30);
    /** Whether or not player's should spawn with this book when they first join the world. */
    private boolean spawnWithBook;
    /** A registry name for this book. Used internally. */
    private ResourceLocation registryName;
    /** Optionally set this to have the item appear in your creative tab rather than the misc creative tab */
    private CreativeTabs creativeTab = CreativeTabs.MISC;

    /**
     * @param category - Add this category
     */
    public void addCategory(CategoryAbstract category) {
        this.categoryList.add(category);
    }

    /**
     * @param category - Remove this category
     */
    public void removeCategory(CategoryAbstract category) {
        this.categoryList.remove(category);
    }

    /**
     * @param categories - Add these categories
     */
    public void addCategoryList(List<CategoryAbstract> categories) {
        this.categoryList.addAll(categories);
    }

    /**
     * @param categories - Remove these categories
     */
    public void removeCategoryList(List<CategoryAbstract> categories) {
        this.categoryList.removeAll(categories);
    }

    /**
     * @return - Localized book title
     */
    public String getLocalizedBookTitle() {
        return TextHelper.localizeEffect(getTitle());
    }

    /**
     * @return - Localized welcome message
     */
    public String getLocalizedWelcomeMessage() {
        return TextHelper.localizeEffect(getWelcomeMessage());
    }

    /**
     * @return - Localized item display name
     */
    public String getLocalizedDisplayName() {
        return TextHelper.localize(getDisplayName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return getRegistryName().equals(book.getRegistryName());

    }

    @Override
    public int hashCode() {
        return getRegistryName().hashCode();
    }
}
