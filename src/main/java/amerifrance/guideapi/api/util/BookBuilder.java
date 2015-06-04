package amerifrance.guideapi.api.util;

import amerifrance.guideapi.api.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.base.Book;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ChestGenHooks;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to set all aspects of your book. None are mandatory.
 *
 * Documentation for each value can be found in {@link Book}
 */
public class BookBuilder {

    private String GUITEXLOC = "guideapi:textures/gui/";

    private List<CategoryAbstract> categoryList = new ArrayList<CategoryAbstract>();
    private String unlocBookTitle = "item.GuideBook.name";
    private String unlocWelcomeMessage = unlocBookTitle;
    private String unlocDisplayName = unlocBookTitle;
    private String author;
    private ResourceLocation pageTexture = new ResourceLocation(GUITEXLOC + "book_colored.png");
    private ResourceLocation outlineTexture = new ResourceLocation(GUITEXLOC + "book_greyscale.png");
    private String itemTexture;
    private Color bookColor = new Color(171, 70, 30);
    private boolean spawnWithBook = false;
    private boolean isLostBook = false;
    private int lootChance = 50;
    private String[] chestHooks = { ChestGenHooks.DUNGEON_CHEST };

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

    public BookBuilder setAuthor(String author) {
        this.author = author;
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

    public BookBuilder setItemTexture(String iconLoc) {
        this.itemTexture = iconLoc;
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

    public BookBuilder setLootChance(int lootChance) {
        this.lootChance = lootChance;
        return this;
    }

    public BookBuilder setChestHooks(String ... chestHooks) {
        this.chestHooks = chestHooks;
        return this;
    }

    public Book build() {
        return new Book(categoryList, unlocBookTitle, unlocWelcomeMessage, unlocDisplayName, author, pageTexture, outlineTexture, itemTexture, bookColor, spawnWithBook, isLostBook, lootChance, chestHooks);
    }
}
