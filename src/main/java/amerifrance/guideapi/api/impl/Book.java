package amerifrance.guideapi.api.impl;

import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.util.LogHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Book {

    private static final String GUITEXLOC = "guideapi:textures/gui/";

    /**
     * A list of categories contained in this book
     */
    private List<CategoryAbstract> categoryList = new ArrayList<CategoryAbstract>();
    /**
     * The unlocalized display title of this book. Drawn at the top of the home page.
     */
    private String title = "item.GuideBook.name";
    /**
     * The unlocalized welcome message. Displayed on the home page.
     */
    private String welcomeMessage = title;
    /**
     * The unlocalized display title of the book item.
     */
    private String displayName = title;
    /**
     * The author of this book. Usually your mod name/id.
     */
    private String author;
    /**
     * The texture to draw for your page.
     */
    private ResourceLocation pageTexture = new ResourceLocation(GUITEXLOC + "book_colored.png");
    /**
     * The texture to draw around your page.
     */
    private ResourceLocation outlineTexture = new ResourceLocation(GUITEXLOC + "book_greyscale.png");
    /**
     * Whether or not your book has a custom model. If false, a colored default model will be used.
     */
    private boolean customModel;
    /**
     * The color of your book. Used for your model (if {@link #customModel} is false) and for the {@link #outlineTexture}
     */
    private Color color = new Color(171, 70, 30);
    /**
     * Whether or not player's should spawn with this book when they first join the world.
     */
    private boolean spawnWithBook;
    /**
     * A registry name for this book. Used internally.
     */
    private ResourceLocation registryName;
    /**
     * Optionally set this to have the item appear in your creative tab rather than the misc creative tab
     */
    private CreativeTabs creativeTab = CreativeTabs.MISC;

    public Book(List<CategoryAbstract> categoryList, String title, String welcomeMessage, String displayName, String author, ResourceLocation pageTexture, ResourceLocation outlineTexture, boolean customModel, Color color, boolean spawnWithBook, ResourceLocation registryName, CreativeTabs creativeTab) {
        if(categoryList.removeAll(Collections.singleton(null))){
            LogHelper.error("A category in book "+displayName+" was null. Please report this to the appropriate mod's issue tracker(Not Guide API).");
        }
        this.categoryList = categoryList;
        this.title = title;
        this.welcomeMessage = welcomeMessage;
        this.displayName = displayName;
        this.author = author;
        this.pageTexture = pageTexture;
        this.outlineTexture = outlineTexture;
        this.customModel = customModel;
        this.color = color;
        this.spawnWithBook = spawnWithBook;
        this.registryName = registryName;
        this.creativeTab = creativeTab;
    }

    public Book() {
    }

    /**
     * @param category - Add this category
     */
    public void addCategory(CategoryAbstract category) {
        if(category == null){
            LogHelper.error("A category in book "+toString()+" was null. Please report this to the appropriate mod's issue tracker(Not Guide API).");
            return;
        }
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
        if(categories.removeAll(Collections.singleton(null))){
            LogHelper.error("A category in book "+toString()+" was null. Please report this to the appropriate mod's issue tracker(Not Guide API).");
        }
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

    public List<CategoryAbstract> getCategoryList() {
        if(categoryList.removeAll(Collections.singleton(null))){
            LogHelper.error("A category in book "+toString()+" was null. Please report this to the appropriate mod's issue tracker(Not Guide API).");
        }
        return this.categoryList;
    }

    public String getTitle() {
        return this.title;
    }

    public String getWelcomeMessage() {
        return this.welcomeMessage;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getAuthor() {
        return this.author;
    }

    public ResourceLocation getPageTexture() {
        return this.pageTexture;
    }

    public ResourceLocation getOutlineTexture() {
        return this.outlineTexture;
    }

    public boolean isCustomModel() {
        return this.customModel;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean isSpawnWithBook() {
        return this.spawnWithBook;
    }

    public ResourceLocation getRegistryName() {
        return this.registryName;
    }

    public CreativeTabs getCreativeTab() {
        return this.creativeTab;
    }

    public void setCategoryList(List<CategoryAbstract> categoryList) {
        if(categoryList.removeAll(Collections.singleton(null))){
            LogHelper.error("A category in book "+toString()+" was null. Please report this to the appropriate mod's issue tracker(Not Guide API).");
        }
        this.categoryList = categoryList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPageTexture(ResourceLocation pageTexture) {
        this.pageTexture = pageTexture;
    }

    public void setOutlineTexture(ResourceLocation outlineTexture) {
        this.outlineTexture = outlineTexture;
    }

    public void setCustomModel(boolean customModel) {
        this.customModel = customModel;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSpawnWithBook(boolean spawnWithBook) {
        this.spawnWithBook = spawnWithBook;
    }

    public void setRegistryName(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    public void setCreativeTab(CreativeTabs creativeTab) {
        this.creativeTab = creativeTab;
    }

    public String toString() {
        return "amerifrance.guideapi.api.impl.Book(categoryList=" + this.getCategoryList() + ", title=" + this.getTitle() + ", welcomeMessage=" + this.getWelcomeMessage() + ", displayName=" + this.getDisplayName() + ", author=" + this.getAuthor() + ", pageTexture=" + this.getPageTexture() + ", outlineTexture=" + this.getOutlineTexture() + ", customModel=" + this.isCustomModel() + ", color=" + this.getColor() + ", spawnWithBook=" + this.isSpawnWithBook() + ", registryName=" + this.getRegistryName() + ", creativeTab=" + this.getCreativeTab() + ")";
    }
}
