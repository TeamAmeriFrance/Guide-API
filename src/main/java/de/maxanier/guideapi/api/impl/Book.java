package de.maxanier.guideapi.api.impl;

import com.google.common.base.Joiner;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.util.LogHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Book {

    private final List<CategoryAbstract> categories = new ArrayList<>();
    private final Consumer<List<CategoryAbstract>> contentProvider;
    private final Component title;
    private final Component header;
    private final Component itemName;
    private final Component author;
    private final ResourceLocation pageTexture;
    private final ResourceLocation outlineTexture;
    private final Color color;
    private final boolean spawnWithBook;
    private final ResourceLocation registryName;
    private boolean isInitialized;


    protected Book(Consumer<List<CategoryAbstract>> contentProvider, Component title, Component header, Component displayName, Component author, ResourceLocation pageTexture, ResourceLocation outlineTexture, Color color, boolean spawnWithBook, ResourceLocation registryName) {
        this.contentProvider = contentProvider;
        this.title = title;
        this.header = header;
        this.itemName = displayName;
        this.author = author;
        this.pageTexture = pageTexture;
        this.outlineTexture = outlineTexture;
        this.color = color;
        this.spawnWithBook = spawnWithBook;
        this.registryName = registryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return getRegistryName().equals(book.getRegistryName());

    }

    /**
     * Can be used to force content initialisation independent of first use regardless of if it was initialized previously.
     * Use at own risk. Might cause crashes if the book is currently open.
     */
    public void forceInitializeContent() {
        LogHelper.info("Force initializing book content " + registryName.toString());
        categories.clear();
        isInitialized = false;
        initializeContent();
    }

    public Component getAuthor() {
        return this.author;
    }

    public List<CategoryAbstract> getCategoryList() {
        return this.categories;
    }

    public Color getColor() {
        return this.color;
    }

    public Component getHeader() {
        return this.header;
    }

    public Component getItemName() {
        return this.itemName;
    }

    public ResourceLocation getOutlineTexture() {
        return this.outlineTexture;
    }

    public ResourceLocation getPageTexture() {
        return this.pageTexture;
    }

    public ResourceLocation getRegistryName() {
        return this.registryName;
    }

    public Component getTitle() {
        return this.title;
    }

    @Override
    public int hashCode() {
        return getRegistryName().hashCode();
    }

    public void initializeContent() {
        if (!isInitialized) {
            LogHelper.debug("Opening book " + registryName.toString() + " for the first time -> Initializing content");
            contentProvider.accept(categories);
            isInitialized = true;
        }
    }

    public boolean shouldSpawnWithBook() {
        return this.spawnWithBook;
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
                .append("color", color)
                .append("spawnWithBook", spawnWithBook)
                .append("registryName", registryName)
                .toString();
    }
}
