package api.impl;

import api.impl.abstraction.CategoryAbstract;
import api.util.TextHelper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.Color;
import java.util.List;

public class Book {

    private static final String GUITEXLOC = "guideapi:textures/gui/";

    private List<CategoryAbstract> categories = Lists.newArrayList();
    private String title = "item.GuideBook.name";
    private String header = title;
    private String itemName = title;
    private String author;
    private ResourceLocation pageTexture = new ResourceLocation(GUITEXLOC + "book_colored.png");
    private ResourceLocation outlineTexture = new ResourceLocation(GUITEXLOC + "book_greyscale.png");
    private boolean customModel;
    private Color color = new Color(171, 70, 30);
    private boolean spawnWithBook;
    private ResourceLocation registryName;
    private CreativeTabs creativeTab = CreativeTabs.MISC;

    /**
     * @deprecated see {@link BookBinder}. To be made package private in 1.13.
     */
    @Deprecated
    public Book(List<CategoryAbstract> categoryList, String title, String header, String displayName, String author, ResourceLocation pageTexture, ResourceLocation outlineTexture, boolean customModel, Color color, boolean spawnWithBook, ResourceLocation registryName, CreativeTabs creativeTab) {
        this.categories = categoryList;
        this.title = title;
        this.header = header;
        this.itemName = displayName;
        this.author = author;
        this.pageTexture = pageTexture;
        this.outlineTexture = outlineTexture;
        this.customModel = customModel;
        this.color = color;
        this.spawnWithBook = spawnWithBook;
        this.registryName = registryName;
        this.creativeTab = creativeTab;
    }

    /**
     * @deprecated see {@link BookBinder}. To be removed in 1.13.
     */
    @Deprecated
    public Book() {
    }

    public List<CategoryAbstract> getCategoryList() {
        return this.categories;
    }

    public String getTitle() {
        return this.title;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getHeader() {
        return this.header;
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

    public boolean hasCustomModel() {
        return this.customModel;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean shouldSpawnWithBook() {
        return this.spawnWithBook;
    }

    public ResourceLocation getRegistryName() {
        return this.registryName;
    }

    public CreativeTabs getCreativeTab() {
        return this.creativeTab;
    }

    /**
     * @deprecated see {@link #getItemName()}. To be removed in 1.13.
     */
    @Deprecated
    public String getDisplayName() {
        return this.itemName;
    }

    /**
     * @deprecated see {@link #getHeader()}. To be removed in 1.13.
     */
    @Deprecated
    public String getWelcomeMessage() {
        return this.header;
    }

    /**
     * @deprecated see {@link #hasCustomModel()}. To be removed in 1.13.
     */
    @Deprecated
    public boolean isCustomModel() {
        return this.customModel;
    }

    /**
     * @deprecated see {@link #shouldSpawnWithBook()}. To be removed in 1.13.
     */
    @Deprecated
    public boolean isSpawnWithBook() {
        return this.spawnWithBook;
    }

    /**
     * @deprecated see {@link BookBinder#addCategory(CategoryAbstract)}. To be removed in 1.13.
     */
    @Deprecated
    public void setCategoryList(List<CategoryAbstract> categoryList) {
        this.categories = categoryList;
    }

    /**
     * @deprecated see {@link BookBinder#setGuideTitle(String)}. To be removed in 1.13.
     */
    @Deprecated
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @deprecated see {@link BookBinder#setHeader(String)}. To be removed in 1.13.
     */
    @Deprecated
    public void setWelcomeMessage(String header) {
        this.header = header;
    }

    /**
     * @deprecated see {@link BookBinder#setItemName(String)}. To be removed in 1.13.
     */
    @Deprecated
    public void setDisplayName(String displayName) {
        this.itemName = displayName;
    }

    /**
     * @deprecated see {@link BookBinder#setAuthor(String)}. To be removed in 1.13.
     */
    @Deprecated
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @deprecated see {@link BookBinder#setSpawnWithBook()}. To be removed in 1.13.
     */
    @Deprecated
    public void setSpawnWithBook(boolean spawnWithBook) {
        this.spawnWithBook = spawnWithBook;
    }

    /**
     * @deprecated see {@link BookBinder#setPageTexture(ResourceLocation)}. To be removed in 1.13.
     */
    @Deprecated
    public void setPageTexture(ResourceLocation pageTexture) {
        this.pageTexture = pageTexture;
    }

    /**
     * @deprecated see {@link BookBinder#setOutlineTexture(ResourceLocation)}. To be removed in 1.13.
     */
    @Deprecated
    public void setOutlineTexture(ResourceLocation outlineTexture) {
        this.outlineTexture = outlineTexture;
    }

    /**
     * @deprecated see {@link BookBinder#setHasCustomModel()}. To be removed in 1.13.
     */
    @Deprecated
    public void setCustomModel(boolean customModel) {
        this.customModel = customModel;
    }

    /**
     * @deprecated see {@link BookBinder#setColor(int)}. To be removed in 1.13.
     */
    @Deprecated
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @deprecated see {@link BookBinder#BookBinder(ResourceLocation)}. To be removed in 1.13.
     */
    @Deprecated
    public void setRegistryName(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    /**
     * @deprecated see {@link BookBinder#setCreativeTab(CreativeTabs)}. To be removed in 1.13.
     */
    @Deprecated
    public void setCreativeTab(CreativeTabs creativeTab) {
        this.creativeTab = creativeTab;
    }

    /**
     * @deprecated see {@link BookBinder#addCategory(CategoryAbstract)}. To be removed in 1.13.
     *
     * @param category - Add this category
     */
    @Deprecated
    public void addCategory(CategoryAbstract category) {
        this.categories.add(category);
    }

    /**
     * @deprecated see {@link BookBinder}. To be removed in 1.13.
     *
     * @param category - Remove this category
     */
    @Deprecated
    public void removeCategory(CategoryAbstract category) {
        this.categories.remove(category);
    }

    /**
     * @deprecated see {@link BookBinder#addCategory(CategoryAbstract)}. To be removed in 1.13.
     *
     * @param categories - Add these categories
     */
    @Deprecated
    public void addCategoryList(List<CategoryAbstract> categories) {
        this.categories.addAll(categories);
    }

    /**
     * @deprecated see {@link BookBinder}. To be removed in 1.13.
     *
     * @param categories - Remove these categories
     */
    @Deprecated
    public void removeCategoryList(List<CategoryAbstract> categories) {
        this.categories.removeAll(categories);
    }

    /**
     * @deprecated localize yourself with {@link #getTitle()}. To be removed in 1.13.
     *
     * @return - Localized book title
     */
    @Deprecated
    public String getLocalizedBookTitle() {
        return TextHelper.localizeEffect(getTitle());
    }

    /**
     * @deprecated localize yourself with {@link #getHeader()}. To be removed in 1.13.
     *
     * @return - Localized welcome message
     */
    @Deprecated
    public String getLocalizedWelcomeMessage() {
        return TextHelper.localizeEffect(getHeader());
    }

    /**
     * @deprecated localize yourself with {@link #getDisplayName()}. To be removed in 1.13.
     *
     * @return - Localized item display name
     */
    @Deprecated
    public String getLocalizedDisplayName() {
        return TextHelper.localize(getDisplayName());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("categoryList", Joiner.on(", ").join(categories))
                .append("title", title)
                .append("header", header)
                .append("itemName", itemName)
                .append("author", author)
                .append("pageTexture", pageTexture)
                .append("outlineTexture", outlineTexture)
                .append("customModel", customModel)
                .append("color", color)
                .append("spawnWithBook", spawnWithBook)
                .append("registryName", registryName)
                .append("creativeTab", creativeTab)
                .toString();
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
