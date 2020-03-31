package amerifrance.guideapi.api.impl;

import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import com.google.common.base.Strings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.forgespi.language.IModInfo;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;

public class BookBinder {

    private final ResourceLocation registryName;
    private Consumer<List<CategoryAbstract>> contentProvider;
    private String guideTitle = "item.guideapi.book";
    private String header;
    private String itemName;
    private String author;
    private ResourceLocation pageTexture = new ResourceLocation("guideapi", "textures/gui/book_colored.png");
    private ResourceLocation outlineTexture = new ResourceLocation("guideapi", "textures/gui/book_greyscale.png");
    private boolean hasCustomModel;
    private Color color = new Color(171, 70, 30);
    private boolean spawnWithBook;
    private ItemGroup creativeTab = ItemGroup.MISC;

    /**
     * Creates a new {@link Book} builder which will provide a much more user-friendly interface for creating books.
     *
     * @param registryName The registry name for the book to build. Should use your modid as the domain.
     */
    public BookBinder(ResourceLocation registryName) {
        this.registryName = registryName;
    }


    /**
     * Set a consumer (method) that will generate the content for your book and add it to the provided list
     * It will be called during GuideAPI's {@link InterModProcessEvent} (so it might execute in parallel with your mod)
     *
     * @param contentProvider The consumer. Categories are displayed in which they are added to the provided list
     * @return the builder instance for chaining.
     */
    public BookBinder setContentProvider(Consumer<List<CategoryAbstract>> contentProvider){
        this.contentProvider = contentProvider;
        return this;
    }

    /**
     * Sets the title of this book to be displayed in the GUI.
     *
     * @param guideTitle The title of this guide.
     * @return the builder instance for chaining.
     */
    public BookBinder setGuideTitle(String guideTitle) {
        this.guideTitle = guideTitle;
        return this;
    }

    /**
     * Sets the header text of this book. The header is displayed at the top of the home page above the category listing.
     *
     * By default, this is the same as {@link #guideTitle}.
     *
     * @param header The header text to display.
     * @return the builder instance for chaining.
     */
    public BookBinder setHeader(String header) {
        this.header = header;
        return this;
    }

    /**
     * Sets the unlocalized name for the item containing this book.
     *
     * By default, this is the same as {@link #guideTitle}.
     *
     * @param itemName The unlocalized name for this item.
     * @return the builder instance for chaining.
     */
    public BookBinder setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    /**
     * The author of this book. If your books are lore-heavy, using an actual author name is acceptable. If not, you can
     * just use your mod name.
     *
     * By default, this uses the name of the mod container obtained from looking up the domain of {@link #registryName}.
     *
     * @param author The author of this book.
     * @return the builder instance for chaining.
     */
    public BookBinder setAuthor(String author) {
        this.author = author;
        return this;
    }

    /**
     * The texture to use for the pages themselves. These are un-colored and drawn just how they appear in the texture file.
     * The dimensions should remain the same as the default texture.
     *
     * By default, this uses the same page texture as vanilla books.
     *
     * @param pageTexture The page texture to use for this guide.
     * @return the builder instance for chaining.
     */
    public BookBinder setPageTexture(ResourceLocation pageTexture) {
        this.pageTexture = pageTexture;
        return this;
    }

    /**
     * The texture to use for the border of the book. These are colored with {@link #color}. The dimensions should remain
     * the same as the default texture.
     *
     * By default, this uses a greyscale version of the outline of vanilla books.
     *
     * @param outlineTexture The outline texture to use for this guide.
     * @return the builder instance for chaining.
     */
    public BookBinder setOutlineTexture(ResourceLocation outlineTexture) {
        this.outlineTexture = outlineTexture;
        return this;
    }

    /**
     * Indicates that the item containing this book has a custom model that you will manually register.
     *
     * By default, a generic book model will be registered and colored with {@link #color}.
     *
     * @return the builder instance for chaining.
     */
    public BookBinder setHasCustomModel() {
        this.hasCustomModel = true;
        return this;
    }

    /**
     * Sets the color to overlay on the book model and GUI border.
     *
     * By default, this is a reddish-brown color.
     *
     * @param color The color to overlay with.
     * @return the builder instance for chaining.
     */
    public BookBinder setColor(Color color) {
        this.color = color;
        return this;
    }

    /**
     * An overload that takes an RGB color instead of a {@link Color} instance.
     *
     * @see #setColor(int)
     *
     * @param color The color to overlay with.
     * @return the builder instance for chaining.
     */
    public BookBinder setColor(int color) {
        return setColor(new Color(color));
    }

    /**
     * Sets the default config option for whether new players should spawn with this book in their inventory. Players may
     * override this in the config if they wish.
     *
     * By default, books will not spawn in the player's inventory.
     *
     * @return the builder instance for chaining.
     */
    public BookBinder setSpawnWithBook() {
        this.spawnWithBook = true;
        return this;
    }

    /**
     * Sets the Creative Tab this book should appear in.
     *
     * By default, all books will appear in {@link ItemGroup#MISC}.
     *
     * @param creativeTab The creative tab this book should display in.
     * @return the builder instance for chaining.
     */
    public BookBinder setCreativeTab(ItemGroup creativeTab) {
        this.creativeTab = creativeTab;
        return this;
    }

    /**
     * Constructs a book from the given data. Will modify specific values if not set so they have defaults.
     * @return a constructed book.
     */
    public Book build() {
        if (Strings.isNullOrEmpty(author))
            this.author = ModList.get().getModContainerById(registryName.getNamespace()).map(ModContainer::getModInfo).map(IModInfo::getDisplayName).orElse("Unknown");

        if (header == null)
            this.header = guideTitle;

        if (this.itemName == null)
            this.itemName = guideTitle.substring(5);

        if(contentProvider ==null){
            throw new IllegalStateException("Content supplier of book "+registryName.toString()+" must be provided");
        }

        return new Book(contentProvider, guideTitle, header, itemName, author, pageTexture, outlineTexture, hasCustomModel, color, spawnWithBook, registryName, creativeTab);
    }
}
