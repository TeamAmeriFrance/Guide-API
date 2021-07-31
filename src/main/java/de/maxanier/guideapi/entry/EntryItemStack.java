package de.maxanier.guideapi.entry;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.Entry;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Objects;

public class EntryItemStack extends Entry {

    public ItemStack itemStack;

    public EntryItemStack(List<IPage> pageList, Component name, ItemStack stack) {
        super(pageList, name);
        this.itemStack = stack;
    }


    public EntryItemStack(Component name, ItemStack stack) {
        super(name);
        this.itemStack = stack;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(PoseStack stack, Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, Font fontRendererObj) {
        if (itemStack != null)
            GuiHelper.drawScaledItemStack(stack, itemStack, entryX + 2, entryY, 0.5f);

        super.drawExtras(stack, book, category, entryX, entryY, entryWidth, entryHeight, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryItemStack that)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(itemStack, that.itemStack);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (itemStack != null ? itemStack.hashCode() : 0);
        return result;
    }
}
