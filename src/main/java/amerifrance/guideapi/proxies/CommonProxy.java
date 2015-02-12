package amerifrance.guideapi.proxies;

import amerifrance.guideapi.gui.GuiHome;
import amerifrance.guideapi.test.TestBooks;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) {
            return null;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) {
            return new GuiHome(TestBooks.testBook1, player, player.getHeldItem());
        }
        return null;
    }
}
