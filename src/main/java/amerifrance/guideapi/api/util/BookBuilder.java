package amerifrance.guideapi.api.util;

import amerifrance.guideapi.ModInformation;
import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.base.Book;
import net.minecraft.util.ResourceLocation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class BookBuilder {

    private List<CategoryAbstract> categoryList = new ArrayList<CategoryAbstract>();
    private String unlocBookTitle = "item.GuideBook.name";
    private String unlocWelcomeMessage = unlocBookTitle;
    private String unlocDisplayName = unlocBookTitle;
    private ResourceLocation pageTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_colored.png");
    private ResourceLocation outlineTexture = new ResourceLocation(ModInformation.GUITEXLOC + "book_greyscale.png");
    private Color bookColor = new Color(171, 70, 30);
    private boolean spawnWithBook = false;
    private boolean isLostBook = false;

    public BookBuilder() {

    }

    public BookBuilder setCategories(List<CategoryAbstract> categoryList) {
        this.categoryList = categoryList;
        return this;
    }

    public BookBuilder setUnlocBookTitle(String unlocBookTitle) {
        this.unlocBookTitle = unlocBookTitle;
        return this;
    }

    public BookBuilder setUnlocWelcomeMessage(String unlocWelcomeMessage) {
        this.unlocWelcomeMessage = unlocWelcomeMessage;
        return this;
    }

    public BookBuilder setUnlocDisplayName(String unlocDisplayName) {
        this.unlocDisplayName = unlocDisplayName;
        return this;
    }

    public BookBuilder setPageTexture(ResourceLocation pageTexture) {
        this.pageTexture = pageTexture;
        return this;
    }

    public BookBuilder setOutlineTexture(ResourceLocation outlineTexture) {
        this.outlineTexture = outlineTexture;
        return this;
    }

    public BookBuilder setBookColor(Color bookColor) {
        this.bookColor = bookColor;
        return this;
    }

    public BookBuilder setSpawnWithBook(boolean spawnWithBook) {
        this.spawnWithBook = spawnWithBook;
        return this;
    }

    public BookBuilder setIsLostBook(boolean isLostBook) {
        this.isLostBook = isLostBook;
        return this;
    }

    public Book build() {
        return new Book(categoryList, unlocBookTitle, unlocWelcomeMessage, unlocDisplayName, pageTexture, outlineTexture, bookColor, spawnWithBook, isLostBook);
    }
}
