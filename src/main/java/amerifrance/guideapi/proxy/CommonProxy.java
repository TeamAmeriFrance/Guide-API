package amerifrance.guideapi.proxy;

import api.GuideAPI;
import api.IGuideItem;
import api.IRecipeRenderer;
import api.impl.Book;
import api.impl.abstraction.CategoryAbstract;
import api.impl.abstraction.EntryAbstract;
import api.util.NBTBookTags;
import amerifrance.guideapi.gui.CategoryScreen;
import amerifrance.guideapi.gui.EntryScreen;
import amerifrance.guideapi.gui.HomeScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy {


    public void openGuidebook(PlayerEntity player, World world, Book book, ItemStack bookStack){
    }

    public void playSound(SoundEvent sound) {
    }

    public void openEntry(Book book, CategoryAbstract categoryAbstract, EntryAbstract entryAbstract, PlayerEntity player, ItemStack stack) {
    }

    public void initColors() {
    }
}
