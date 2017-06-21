package amerifrance.guideapi.test;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageBrewingRecipe;
import amerifrance.guideapi.page.PageFurnaceRecipe;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageText;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nullable;
import java.awt.*;

@GuideBook
public class TestBook2 implements IGuideBook {

    public static Book book;

    @Nullable
    @Override
    public Book buildBook() {
        book = new Book();
        book.setAuthor("TehNut");
        book.setColor(Color.GREEN);
        book.setDisplayName("Display Name");
        book.setTitle("Title message");
        book.setWelcomeMessage("Is this still a thing?");

        CategoryAbstract testCategory = new CategoryItemStack("test.category.name", new ItemStack(Items.BANNER)).withKeyBase("guideapi");
        testCategory.addEntry("entry", new EntryItemStack("test.entry.name", new ItemStack(Items.POTATO)));
        testCategory.getEntry("entry").addPage(new PageText("Hello, this is\nsome text"));
        testCategory.getEntry("entry").addPage(new PageFurnaceRecipe(Blocks.COBBLESTONE));
        testCategory.getEntry("entry").addPage(new PageIRecipe(new ShapedOreRecipe(new ResourceLocation("gi","test2"),Items.ACACIA_BOAT, "X X", "XXX", 'X', "plankWood")));
        testCategory.getEntry("entry").addPage(new PageBrewingRecipe(new BrewingRecipe(
            PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD),
            new ItemStack(Items.SPECKLED_MELON),
            PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.HEALING))));
        book.addCategory(testCategory);

        book.setRegistryName(new ResourceLocation("guideapi", "test_book2"));
        return book;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleModel(ItemStack bookStack) {
        GuideAPI.setModel(book);
    }

    @Override
    public void handlePost(ItemStack bookStack) {
      //  GameRegistry.addRecipe(new ShapedOreRecipe(bookStack, "X X", " X ", "X X", 'X', "ingotIron"));
    }
}
