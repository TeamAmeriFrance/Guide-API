package amerifrance.guideapi.api.impl;

import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.util.LogHelper;
import com.google.common.base.Joiner;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Book {

    private static final String GUITEXLOC = "guideapi:textures/gui/";

    private final List<CategoryAbstract> categories = new ArrayList<>();
    private final Consumer<List<CategoryAbstract>> contentProvider;
    private final String title;
    private final String header;
    private final String itemName;
    private final String author;
    private final ResourceLocation pageTexture;
    private final ResourceLocation outlineTexture;
    private final boolean customModel;
    private final Color color;
    private final boolean spawnWithBook;
    private final ResourceLocation registryName;
    private final ItemGroup creativeTab;
    private boolean isInitialized;


    protected Book(Consumer<List<CategoryAbstract>> contentProvider, String title, String header, String displayName, String author, ResourceLocation pageTexture, ResourceLocation outlineTexture, boolean customModel, Color color, boolean spawnWithBook, ResourceLocation registryName, ItemGroup creativeTab) {
        this.contentProvider = contentProvider;
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

    public void initializeContent(){
        if(!isInitialized){
            LogHelper.debug("Opening book "+registryName.toString()+" for the first time -> Initializing content");
            contentProvider.accept(categories);
            isInitialized=true;
        }
    }

    /**
     * Can be used to force content initialisation independent of first use regardless of if it was initialized previously.
     * Use at own risk. Might cause crashes if the book is currently open.
     */
    public void forceInitializeContent(){
        LogHelper.info("Force initializing book content "+registryName.toString());
        categories.clear();
        isInitialized=false;
        initializeContent();
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

    public ItemGroup getCreativeTab() {
        return this.creativeTab;
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
