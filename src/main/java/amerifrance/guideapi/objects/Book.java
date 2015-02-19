package amerifrance.guideapi.objects;

import amerifrance.guideapi.objects.abstraction.CategoryAbstract;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Book {

    public List<CategoryAbstract> categoryList = new ArrayList<CategoryAbstract>();
    public String unlocBookTitle;
    public String unlocWelcomeMessage;
    public ResourceLocation pageTexture;
    public ResourceLocation outlineTexture;
    public Color bookColor = new Color(171, 70, 30);

    /**
     * @param categoryList        - Category List with all your information
     * @param unlocBookTitle      - Unlocalized name for a book title
     * @param unlocWelcomeMessage - Unlocalized welcome message
     * @param pageTexture         - Texture for book's page
     * @param outlineTexture      - Texture for book outline
     * @param bookColor           - Color of book
     */
    public Book(List<CategoryAbstract> categoryList, String unlocBookTitle, String unlocWelcomeMessage, ResourceLocation pageTexture, ResourceLocation outlineTexture, Color bookColor) {
        this.categoryList = categoryList;
        this.unlocBookTitle = unlocBookTitle;
        this.unlocWelcomeMessage = unlocWelcomeMessage;
        this.pageTexture = pageTexture;
        this.outlineTexture = outlineTexture;
        this.bookColor = bookColor;
    }

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
     * @param categories - Add these category
     */
    public void addCategoryList(List<CategoryAbstract> categories) {
        this.categoryList.addAll(categories);
    }

    /**
     * @param categories - Remove these category
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
}
