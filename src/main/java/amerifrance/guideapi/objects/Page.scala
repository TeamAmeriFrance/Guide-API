package amerifrance.guideapi.objects

import amerifrance.guideapi.gui.GuiBase
import net.minecraft.entity.player.EntityPlayer

class Page() {


  def drawPage(guiBase: GuiBase, guiLeft: Int, guiTop: Int) = {
  }

  def drawExtras(mouseX: Int, mouseY: Int, guiBase: GuiBase) = {
  }

  def canSee(player: EntityPlayer): Boolean = {
    return true
  }

  def onLeftClicked(mouseX: Int, mouseY: Int) = {
    System.out.println("Left Clicked")
  }

  def onRightClicked(mouseX: Int, mouseY: Int) = {
    System.out.println("Right Clicked")
  }
}
