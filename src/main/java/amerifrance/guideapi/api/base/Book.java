package amerifrance.guideapi.api.base;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import lombok.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private final String GUITEXLOC = "guideapi:textures/gui/";

    private List<CategoryAbstract> categoryList = new ArrayList<CategoryAbstract>();
    private String unlocBookTitle = "item.GuideBook.name";
    private String unlocWelcomeMessage = unlocBookTitle;
    private String unlocDisplayName = unlocBookTitle;
    private String author;
    private ResourceLocation pageTexture = new ResourceLocation(GUITEXLOC + "book_colored.png");
    private ResourceLocation outlineTexture = new ResourceLocation(GUITEXLOC + "book_greyscale.png");
    private boolean customModel;
    private Color bookColor = new Color(171, 70, 30);
    private boolean spawnWithBook;
    private boolean isLostBook;
    private int lootChance = 50;
    private String[] chestHooks = {};

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
        return StatCollector.translateToLocal(unlocBookTitle);
    }

    /**
     * @return - Localized welcome message
     */
    public String getLocalizedWelcomeMessage() {
        return StatCollector.translateToLocal(unlocWelcomeMessage);
    }

    /**
     * @return - Localized item display name
     */
    public String getLocalizedDisplayName() {
        return StatCollector.translateToLocal(unlocDisplayName);
    }
}
