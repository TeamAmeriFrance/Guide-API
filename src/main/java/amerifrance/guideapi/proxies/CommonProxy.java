package amerifrance.guideapi.proxies;

import amerifrance.guideapi.GuideRegistry;
import amerifrance.guideapi.gui.GuiHome;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GuiHome(GuideRegistry.getBook(ID), player, player.getHeldItem());
    }

    public void registerBooks() {
    }

    public void registerJsonBooks(GsonBuilder gsonBuilder) {
    }

    public void playSound(ResourceLocation sound) {
    }
}
