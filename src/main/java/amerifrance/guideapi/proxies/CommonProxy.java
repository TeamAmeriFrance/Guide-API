package amerifrance.guideapi.proxies;

import amerifrance.guideapi.GuideRegistry;
import amerifrance.guideapi.gui.GuiHome;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
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
}
