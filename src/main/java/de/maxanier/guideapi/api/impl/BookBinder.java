package de.maxanier.guideapi.api.impl;

import de.maxanier.guideapi.GuideMod;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class BookBinder {

    private final ResourceLocation registryName;
    private Consumer<List<CategoryAbstract>> contentProvider;
    @Nonnull
    private Component guideTitle = new TranslatableComponent("item.guideapi.book");
    @Nullable
    private Component header;
    @Nullable
    private Component itemName;
    @Nullable
    private Component author;
    private ResourceLocation pageTexture = new ResourceLocation(GuideMod.ID, "textures/gui/book_colored.png");
    private ResourceLocation outlineTexture = new ResourceLocation(GuideMod.ID, "textures/gui/book_greyscale.png");
    private Color color = new Color(171, 70, 30);
    private boolean spawnWithBook;
    private CreativeModeTab creativeTab = CreativeModeTab.TAB_MISC;

    /**
     * Creates a new {@link Book} builder which will provide a much more user-friendly interface for creating books.
     *
     * @param registryName The registry name for the book to build. Should use your modid as the domain.
     */
    public BookBinder(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    /**
     * Constructs a book from the given data. Will modify specific values if not set so they have defaults.
     *
     * @return a constructed book.
     */
    public Book build() {
        if (author == null)
            this.author = new TextComponent(ModList.get().getModContainerById(registryName.getNamespace()).map(ModContainer::getModInfo).map(IModInfo::getDisplayName).orElse("Unknown"));

        if (header == null)
            this.header = guideTitle;

        if (this.itemName == null)
            this.itemName = guideTitle;

        if (contentProvider == null) {
            throw new IllegalStateException("Content supplier of book " + registryName.toString() + " must be provided");
        }

        return new Book(contentProvider, guideTitle, header, itemName, author, pageTexture, outlineTexture, color, spawnWithBook, registryName, creativeTab);
    }

    /**
     * The author of this book. If your books are lore-heavy, using an actual author name is acceptable. If not, you can
     * just use your mod name.
     * <p>
     * By default, this uses the name of the mod container obtained from looking up the domain of {@link #registryName}.
     *
     * @param author The author of this book.
     * @return the builder instance for chaining.
     */
    public BookBinder setAuthor(Component author) {
        this.author = author;
        return this;
    }

    /**
     * Sets the color to overlay on the book model and GUI border.
     * <p>
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
     * @param color The color to overlay with.
     * @return the builder instance for chaining.
     * @see #setColor(int)
     */
    public BookBinder setColor(int color) {
        return setColor(new Color(color));
    }

    /**
     * Set a consumer (method) that will generate the content for your book and add it to the provided list
     * This will be called on client side when the book is opened for the first time.
     *
     * @param contentProvider The consumer. Categories are displayed in which they are added to the provided list
     * @return the builder instance for chaining.
     */
    public BookBinder setContentProvider(Consumer<List<CategoryAbstract>> contentProvider) {
        this.contentProvider = contentProvider;
        return this;
    }

    /**
     * Sets the Creative Tab this book should appear in.
     * <p>
     * By default, all books will appear in {@link ItemGroup#MISC}.
     *
     * @param creativeTab The creative tab this book should display in.
     * @return the builder instance for chaining.
     */
    public BookBinder setCreativeTab(CreativeModeTab creativeTab) {
        this.creativeTab = creativeTab;
        return this;
    }

    /**
     * Sets the title of this book to be displayed in the GUI.
     *
     * @param guideTitle The title of this guide.
     * @return the builder instance for chaining.
     */
    public BookBinder setGuideTitle(Component guideTitle) {
        this.guideTitle = guideTitle;
        return this;
    }

    /**
     * Sets the title of this book to be displayed in the GUI.
     *
     * @param translationKey The translation key for the title of this guide.
     * @return the builder instance for chaining.
     */
    public BookBinder setGuideTitleKey(String translationKey) {
        return this.setGuideTitle(new TranslatableComponent(translationKey));
    }

    /**
     * Sets the header text of this book. The header is displayed at the top of the home page above the category listing.
     * <p>
     * By default, this is the same as {@link #guideTitle}.
     *
     * @param header The header text to display.
     * @return the builder instance for chaining.
     */
    public BookBinder setHeader(Component header) {
        this.header = header;
        return this;
    }

    /**
     * Sets the header text of this book. The header is displayed at the top of the home page above the category listing.
     * <p>
     * By default, this is the same as {@link #guideTitle}.
     *
     * @param translationKey The translation key for the header text to display.
     * @return the builder instance for chaining.
     */
    public BookBinder setHeaderKey(String translationKey) {
        return this.setHeader(new TranslatableComponent(translationKey));
    }

    /**
     * Sets the unlocalized name for the item containing this book.
     * <p>
     * By default, this is the same as {@link #guideTitle}.
     *
     * @param itemName The name for this item.
     * @return the builder instance for chaining.
     */
    public BookBinder setItemName(Component itemName) {
        this.itemName = itemName;
        return this;
    }

    /**
     * Sets the unlocalized name for the item containing this book.
     * <p>
     * By default, this is the same as {@link #guideTitle}.
     *
     * @param translationKey The translation key for the name for this item.
     * @return the builder instance for chaining.
     */
    public BookBinder setItemNameKey(String translationKey) {
        return this.setItemName(new TranslatableComponent(translationKey));
    }

    /**
     * The texture to use for the border of the book. These are colored with {@link #color}. The dimensions should remain
     * the same as the default texture.
     * <p>
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
     * The texture to use for the pages themselves. These are un-colored and drawn just how they appear in the texture file.
     * The dimensions should remain the same as the default texture.
     * <p>
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
     * Sets the default config option for whether new players should spawn with this book in their inventory. Players may
     * override this in the config if they wish.
     * <p>
     * By default, books will not spawn in the player's inventory.
     *
     * @return the builder instance for chaining.
     */
    public BookBinder setSpawnWithBook() {
        this.spawnWithBook = true;
        return this;
    }
}
