package amerifrance.guideapi.objects;

import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Book {

    public List<AbstractCategory> categoryList = new ArrayList<AbstractCategory>();
    public String unlocBookTitle;
    public String unlocWelcomeMessage;
    public ResourceLocation pageTexture;
    public ResourceLocation outlineTexture;
    public Color bookColor = new Color(171, 70, 30);

    /**
     *
     * @param categoryList - Category List with all your information
     * @param unlocBookTitle - Unlocalized name for a book title
     * @param unlocWelcomeMessage - Unlocalized welcome message
     * @param pageTexture - Texture for book's page
     * @param outlineTexture - Texture for book outline
     * @param bookColor - Color of book
     */
    public Book(List<AbstractCategory> categoryList, String unlocBookTitle, String unlocWelcomeMessage, ResourceLocation pageTexture, ResourceLocation outlineTexture, Color bookColor) {
        this.categoryList = categoryList;
        this.unlocBookTitle = unlocBookTitle;
        this.unlocWelcomeMessage = unlocWelcomeMessage;
        this.pageTexture = pageTexture;
        this.outlineTexture = outlineTexture;
        this.bookColor = bookColor;
    }

    /**
     *
     * @param category - Add this category
     */
    public void addCategory(AbstractCategory category) {
        this.categoryList.add(category);
    }

    /**
     *
     * @param category - Remove this category
     */
    public void removeCategory(AbstractCategory category) {
        this.categoryList.remove(category);
    }

    /**
     *
     * @param categories - Add these category
     */
    public void addCategoryList(List<AbstractCategory> categories) {
        this.categoryList.addAll(categories);
    }

    /**
     *
     * @param categories - Remove these category
     */
    public void removeCategoryList(List<AbstractCategory> categories) {
        this.categoryList.removeAll(categories);
    }

    /**
     *
     * @return - Localized book title
     */
    public String getLocalizedBookTitle() {
        return StatCollector.translateToLocal(unlocBookTitle);
    }

    /**
     *
     * @return - Localized welcome message
     */
    public String getLocalizedWelcomeMessage() {
        return StatCollector.translateToLocal(unlocWelcomeMessage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (bookColor != null ? !bookColor.equals(book.bookColor) : book.bookColor != null) return false;
        if (categoryList != null ? !categoryList.equals(book.categoryList) : book.categoryList != null) return false;
        if (outlineTexture != null ? !outlineTexture.equals(book.outlineTexture) : book.outlineTexture != null)
            return false;
        if (pageTexture != null ? !pageTexture.equals(book.pageTexture) : book.pageTexture != null) return false;
        if (unlocBookTitle != null ? !unlocBookTitle.equals(book.unlocBookTitle) : book.unlocBookTitle != null)
            return false;
        if (unlocWelcomeMessage != null ? !unlocWelcomeMessage.equals(book.unlocWelcomeMessage) : book.unlocWelcomeMessage != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = categoryList != null ? categoryList.hashCode() : 0;
        result = 31 * result + (unlocBookTitle != null ? unlocBookTitle.hashCode() : 0);
        result = 31 * result + (unlocWelcomeMessage != null ? unlocWelcomeMessage.hashCode() : 0);
        result = 31 * result + (pageTexture != null ? pageTexture.hashCode() : 0);
        result = 31 * result + (outlineTexture != null ? outlineTexture.hashCode() : 0);
        result = 31 * result + (bookColor != null ? bookColor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{categoryList=" + categoryList + ", unlocBookTitle='" + unlocBookTitle + '\'' + ", unlocWelcomeMessage='"
                + unlocWelcomeMessage + '\'' + ", pageTexture=" + pageTexture + ", outlineTexture=" + outlineTexture + ", bookColor=" + bookColor + '}';
    }
}
