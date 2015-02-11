package amerifrance.guideapi.objects

import java.util

import amerifrance.guideapi.gui.{GuiBase, GuiCategory}
import net.minecraft.client.gui.FontRenderer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.StatCollector

abstract class AbstractEntry(pageList: util.List[Page] = new util.ArrayList[Page], unlocEntryName: String) {

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

  def draw(book: Book, category: AbstractCategory, entryX: Int, entryY: Int, entryWidth: Int, entryHeight: Int, mouseX: Int, mouseY: Int, guiBase: GuiBase, fontRenderer: FontRenderer)

  def drawExtras(book: Book, category: AbstractCategory, entryX: Int, entryY: Int, entryWidth: Int, entryHeight: Int, mouseX: Int, mouseY: Int, guiBase: GuiBase, fontRenderer: FontRenderer)

  def canSee(player: EntityPlayer): Boolean

  def onLeftClicked(book: Book, category: AbstractCategory, mouseX: Int, mouseY: Int, player: EntityPlayer, guiCategory: GuiCategory)

  def onRightClicked(book: Book, category: AbstractCategory, mouseX: Int, mouseY: Int, player: EntityPlayer, guiCategory: GuiCategory)
}
