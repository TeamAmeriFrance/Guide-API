package de.maxanier.guideapi.entry;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxanier.guideapi.api.IPage;
import de.maxanier.guideapi.api.impl.Book;
import de.maxanier.guideapi.api.impl.Entry;
import de.maxanier.guideapi.api.impl.abstraction.CategoryAbstract;
import de.maxanier.guideapi.api.util.GuiHelper;
import de.maxanier.guideapi.gui.BaseScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class EntryItemStack extends Entry {

    public ItemStack itemStack;

    public EntryItemStack(List<IPage> pageList, String name, ItemStack stack) {
        super(pageList, name);
        this.itemStack = stack;
    }


    public EntryItemStack(String name, ItemStack stack) {
        super(name);
        this.itemStack = stack;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawExtras(MatrixStack stack, Book book, CategoryAbstract category, int entryX, int entryY, int entryWidth, int entryHeight, int mouseX, int mouseY, BaseScreen guiBase, FontRenderer fontRendererObj) {
        if (itemStack != null)
            GuiHelper.drawScaledItemStack(itemStack, entryX + 2, entryY, 0.5F);

        super.drawExtras(stack, book, category, entryX, entryY, entryWidth, entryHeight, mouseX, mouseY, guiBase, fontRendererObj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryItemStack)) return false;
        if (!super.equals(o)) return false;

        EntryItemStack that = (EntryItemStack) o;

        return itemStack != null ? itemStack.equals(that.itemStack) : that.itemStack == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (itemStack != null ? itemStack.hashCode() : 0);
        return result;
    }
}
