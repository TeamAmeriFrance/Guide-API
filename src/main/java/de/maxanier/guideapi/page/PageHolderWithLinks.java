package de.maxanier.guideapi.page;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.impl.abstraction.EntryAbstract;
import de.maxanier.guideapi.api.util.BookHelper;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import de.maxanier.guideapi.gui.EntryScreen;
import de.maxanier.guideapi.gui.LinkedEntryScreen;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.util.List;


public class PageHolderWithLinks implements IPage {
    private static final Logger LOGGER = LogManager.getLogger();

    private final IPage page;
    private final BookHelper bookHelper;
    private final List<ResourceLocation> lateLinks = Lists.newArrayList();
    private final List<Link> links = Lists.newArrayList();
    private long lastLinkClick = 0;

    public PageHolderWithLinks(BookHelper bookHelper, IPage page) {
        this.page = page;
        this.bookHelper = bookHelper;
    }

    /**
     * Add a link
     *
     * @return This
     */
    public PageHolderWithLinks addLink(EntryAbstract entry) {
        links.add(new EntryLink(entry));
        return this;
    }

    /**
     * Add a resource location of an entry to be linked
     *
     * @return This
     */
    public PageHolderWithLinks addLink(ResourceLocation entry) {
        lateLinks.add(entry);
        return this;
    }

    /**
     * Add a URL link
     *
     * @return This
     */
    public PageHolderWithLinks addLink(URLLink link) {
        links.add(link);
        return this;
    }

    /**
     * Adds a resource location of an entry to be linked
     *
     * @return This
     */
    public PageHolderWithLinks addLink(String resourceLocation) {
        addLink(new ResourceLocation(resourceLocation));
        return this;
    }


    @Override
    public boolean canSee(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack, EntryScreen guiEntry) {
        return page.canSee(book, category, entry, player, bookStack, guiEntry);
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void draw(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {
        page.draw(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void drawExtras(PoseStack stack, Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {
        int ll = guiLeft + guiBase.xSize - 5;
        int y = guiTop + 10;
        for (Link l : links) {
            Component t = l.getDisplayName();
            fontRendererObj.drawShadow(stack, t, ll, y, 0xFFFFFF);
            if (l.width == 0) {
                l.width = fontRendererObj.width(t);
            }
            y += 20;
        }
        page.drawExtras(stack, book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onInit(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack, EntryScreen guiEntry) {
        while (lateLinks.size() > 0) {
            ResourceLocation s = lateLinks.remove(0);
            EntryAbstract e = bookHelper.getLinkedEntry(s);
            if (e == null) {
                LOGGER.warn("Failed to find linked entry {}", s);
            } else {
                addLink(e);
            }
        }
        page.onInit(book, category, entry, player, bookStack, guiEntry);
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void onLeftClicked(Book book, CategoryAbstract category, EntryAbstract entry, double mouseX, double mouseY, Player player, EntryScreen guiEntry) {
        if (mouseX > guiEntry.guiLeft + guiEntry.xSize) {
            //Avoid double/triple execution per click
            long lastClock = System.currentTimeMillis() / 4;
            if (lastClock != lastLinkClick) {
                lastLinkClick = lastClock;
                for (int i = 0; i < links.size(); i++) {
                    if (GuiHelper.isMouseBetween(mouseX, mouseY, guiEntry.guiLeft + guiEntry.xSize, guiEntry.guiTop + 10 + 20 * i, links.get(i).width, 20)) {
                        links.get(i).onClicked(book, category, entry, player, guiEntry.bookStack, guiEntry.pageNumber);
                        return;
                    }
                }
            }

        }
        page.onLeftClicked(book, category, entry, mouseX, mouseY, player, guiEntry);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onRightClicked(Book book, CategoryAbstract category, EntryAbstract entry, double mouseX, double mouseY, Player player, EntryScreen guiEntry) {
        page.onRightClicked(book, category, entry, mouseX, mouseY, player, guiEntry);
    }

    private static abstract class Link {
        public int width;

        public abstract Component getDisplayName();

        @OnlyIn(Dist.CLIENT)
        public abstract void onClicked(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack, int page);
    }

    public static class URLLink extends Link {
        private final Component name;
        private final URI link;

        public URLLink(String name, URI link) {
            this.name = Component.literal(name);
            this.link = link;
        }

        public URLLink(Component name, URI link) {
            this.name = name;
            this.link = link;
        }

        @Override
        public Component getDisplayName() {
            return name;
        }

        @Override
        public void onClicked(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack, int page) {
            Util.getPlatform().openUri(link);
        }
    }

    private static class EntryLink extends Link {
        private final EntryAbstract linkedEntry;

        private EntryLink(EntryAbstract entry) {
            this.linkedEntry = entry;
        }

        @Override
        public Component getDisplayName() {
            return linkedEntry.getName();
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public void onClicked(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack, int page) {
            openLinkedEntry(book, category, linkedEntry, player, bookStack, entry, page);
        }

        /**
         * Simply opens a gui screen with a GuiLinkedEntry. Not sure why the @SideOnly does not work, but this uses Class.forName to solve server side class not found issues
         */
        @OnlyIn(Dist.CLIENT)
        private void openLinkedEntry(Book book, CategoryAbstract category, EntryAbstract entry, Player player, ItemStack bookStack, EntryAbstract from, int fromPage) {
            BaseScreen screen = new LinkedEntryScreen(book, category, entry, player, bookStack, from, fromPage);
            Minecraft.getInstance().setScreen(screen);
        }
    }


}
