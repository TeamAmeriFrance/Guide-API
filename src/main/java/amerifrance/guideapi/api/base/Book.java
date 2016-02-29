package amerifrance.guideapi.api.base;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.util.TextHelper;
import lombok.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private final String GUITEXLOC = "guideapi:textures/gui/";

    private List<CategoryAbstract> categoryList = new ArrayList<CategoryAbstract>();
    private String title = "item.GuideBook.name";
    private String welcomeMessage = title;
    private String displayName = title;
    private String author;
    private ResourceLocation pageTexture = new ResourceLocation(GUITEXLOC + "book_colored.png");
    private ResourceLocation outlineTexture = new ResourceLocation(GUITEXLOC + "book_greyscale.png");
    private boolean customModel;
    private Color color = new Color(171, 70, 30);
    private boolean spawnWithBook;

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

    @Deprecated
    public String getUnlocBookTitle() {
        return getTitle();
    }

    @Deprecated
    public String getUnlocWelcomeMessage() {
        return getWelcomeMessage();
    }

    @Deprecated
    public String getUnlocDisplayName() {
        return getDisplayName();
    }

    @Deprecated
    public Color getBookColor() {
        return getColor();
    }

    @Deprecated
    public void setUnlocBookTitle(String unlocBookTitle) {
        setTitle(unlocBookTitle);
    }

    @Deprecated
    public void setUnlocWelcomeMessage(String unlocWelcomeMessage) {
        setWelcomeMessage(unlocWelcomeMessage);
    }

    @Deprecated
    public void setUnlocDisplayName(String unlocDisplayName) {
        setDisplayName(unlocDisplayName);
    }

    @Deprecated
    public void setBookColor(Color color) {
        setColor(color);
    }
}
