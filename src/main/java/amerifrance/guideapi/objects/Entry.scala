package amerifrance.guideapi.objects

import java.util

import amerifrance.guideapi.gui.GuiBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.StatCollector

class Entry(pageList: util.List[Page] = new util.ArrayList[Page], unlocEntryName: String) {

  var pages: util.List[Page] = pageList
  var unlocalizedEntryName: String = unlocEntryName

  def addPage(page: Page) = {
    this.pages.add(page)
  }

  def addPageList(list: util.List[Page]) = {
    this.pages.addAll(list)
  }

  def removePage(page: Page) = {
    this.pages.remove(page)
  }

  def removePageList(list: util.List[Page]) = {
    this.pages.remove(list)
  }

  def getLocalizedName(): String = {
    return StatCollector.translateToLocal(unlocalizedEntryName)
  }

  def drawExtras(mouseX: Int, mouseY: Int, guiBase: GuiBase) = {
  }

  def canSee(player: EntityPlayer): Boolean = {
    return true
  }

  def onLeftClicked(mouseX: Int, mouseY: Int) = {
    System.out.println(getLocalizedName() + "Left Clicked")
  }

  def onRightClicked(mouseX: Int, mouseY: Int) = {
    System.out.println(getLocalizedName() + "Right Clicked")
  }
}
